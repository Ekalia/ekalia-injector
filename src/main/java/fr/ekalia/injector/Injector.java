package fr.ekalia.injector;

import fr.ekalia.injector.annotation.Inject;
import fr.ekalia.injector.annotation.InjectPriority;
import fr.ekalia.injector.annotation.Provides;
import fr.ekalia.injector.exception.UnprovidableClassException;
import fr.ekalia.injector.util.Multimap2;
import fr.ekalia.injector.util.Tuple2;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to inject classes.
 *
 * @author Azn9, Vortezz
 */
public class Injector {

    private static final Logger LOGGER = LogManager.getLogger(Injector.class);
    private static final String CANNOT_LOAD_PROVIDED_CLASS = "Cannot load provided class {}: {}";
    private static boolean initialized = false;
    private final Multimap2<Class<?>, InjectPriority, Object> classes = new Multimap2<>();
    private final Set<ClassLoader> classLoaderSet = new HashSet<>();
    private ClassGraph classGraph;

    /**
     * Creates a new injector.
     */
    @SuppressWarnings("java:S3010") // "Static fields should not be updated in constructors"
    public Injector() {
        if (Injector.initialized) {
            throw new IllegalStateException("Injector already initialized");
        }

        Injector.initialized = true;

        this.classGraph = new ClassGraph().enableAllInfo();

        this.classes.put(Injector.class, InjectPriority.HIGHEST, this);
        this.classes.put(ClassGraph.class, InjectPriority.HIGHEST, this.classGraph);
    }

    /**
     * Registers an object to be injected.
     *
     * @param instance The instance to register.
     */
    public void registerInjection(@NotNull Object instance) {
        Injector.LOGGER.info("Registering injection for {}", instance.getClass().getName());
        this.classes.put(instance.getClass(), InjectPriority.NORMAL, instance);
    }

    /**
     * Registers an object to be injected.
     *
     * @param instance The instance to register.
     * @param priority The priority of the instance.
     */
    public void registerInjection(@NotNull Object instance, @NotNull InjectPriority priority) {
        if (Injector.LOGGER.isInfoEnabled()) {
            Injector.LOGGER.info("Registering injection for {} with priority {}", instance.getClass().getName(), priority.name());
        }
        this.classes.put(instance.getClass(), priority, instance);
    }

    /**
     * Injects all the fields annotated with {@link Inject} with the classes annotated with {@link Provides}.
     *
     * @param classLoader The class loader to use.
     */
    public void addClassLoader(@NotNull ClassLoader classLoader) {
        this.classLoaderSet.add(classLoader);
    }

    /**
     * Injects all the fields annotated with {@link Inject} with the classes annotated with {@link Provides}.
     *
     * @param currentClassLoader The class loader to use.
     * @param packageName        The packages name.
     */
    @SuppressWarnings({
            "java:S3011", // "Make sure that this accessibility update is safe here."
            "java:S1181", // "Throwable exceptions should never be caught"
            "java:S3776" // "Cognitive Complexity of methods should not be too high"
    })
    public void startInjection(ClassLoader currentClassLoader, String... packageName) {
        Injector.LOGGER.info("Starting injection for {}", () -> String.join(", ", packageName));

        this.loadProvides(currentClassLoader, packageName);

        this.classLoaderSet.add(currentClassLoader);

        // Prevent multiple registrations of the same classloader
        this.classGraph.overrideClassLoaders(this.classLoaderSet.toArray(new ClassLoader[0]));

        try (ScanResult scanResult = this.classGraph.scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithFieldAnnotation(Inject.class.getName())) {
                try {
                    Class<?> clazz = classInfo.loadClass(true);

                    if (clazz == null) {
                        Injector.LOGGER.warn("Cannot load class {}", classInfo.getName());
                        continue;
                    }

                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            if (!field.trySetAccessible()) {
                                continue;
                            }

                            if (this.classes.containsKey(field.getType())) {
                                field.set(null, field.getType().cast(this.classes.get(field.getType()).getT2()));
                                continue;
                            }

                            this.injectIntoField(field);
                        }
                    }
                } catch (NoClassDefFoundError e) {
                    Injector.LOGGER.debug("Cannot load class {}", classInfo.getName());
                } catch (Throwable e) {
                    Injector.LOGGER.error("Cannot inject into {} : {}", classInfo.getName(), e.getMessage());
                }
            }
        }

        this.classGraph = new ClassGraph().enableAllInfo();

        for (ClassLoader classLoader : this.classLoaderSet) {
            this.classGraph.addClassLoader(classLoader);
        }
    }

    private void loadProvides(ClassLoader currentClassLoader, String... packageName) {
        Map<Class<?>, InjectPriority> priorityMap = new HashMap<>();

        this.classGraph.overrideClassLoaders(currentClassLoader);
        this.classGraph.acceptPackages(packageName);

        try (ScanResult scanResult = this.classGraph.scan()) {
            for (Class<?> aClass : scanResult.getClassesWithAnnotation(Provides.class.getName()).loadClasses(true)) {
                this.loadProvidesClass(aClass, priorityMap);
            }
        }
    }

    private void loadProvidesClass(Class<?> aClass, Map<Class<?>, InjectPriority> priorityMap) {
        try {
            Provides provides = aClass.getAnnotation(Provides.class);

            InjectPriority priority = provides.priority();

            if (priorityMap.containsKey(aClass) && priorityMap.get(aClass).ordinal() > priority.ordinal()) {
                return;
            }

            priorityMap.put(aClass, priority);

            Object instance = aClass.getDeclaredConstructor().newInstance();
            this.classes.put(aClass, priority, instance);

            if (Injector.LOGGER.isInfoEnabled()) {
                Injector.LOGGER.info("Added provider for {} with priority {}", aClass.getName(), priority.name());
            }
        } catch (UnprovidableClassException uce) {
            if (uce.isShouldLog()) {
                Injector.LOGGER.error(Injector.CANNOT_LOAD_PROVIDED_CLASS, aClass.getName(), uce.getReason());
            }
        } catch (InvocationTargetException ite) {
            if (ite.getCause() instanceof UnprovidableClassException uce) {
                if (uce.isShouldLog()) {
                    Injector.LOGGER.error(Injector.CANNOT_LOAD_PROVIDED_CLASS, aClass.getName(), uce.getReason());
                }
            } else {
                Injector.LOGGER.error(Injector.CANNOT_LOAD_PROVIDED_CLASS, aClass.getName(), ite.getMessage());
                Injector.LOGGER.throwing(ite);
            }
        } catch (NoClassDefFoundError e) {
            Injector.LOGGER.error(Injector.CANNOT_LOAD_PROVIDED_CLASS, aClass.getName(), e.getMessage());
        } catch (
                Throwable t) { //NOSONAR We want to catch all exceptions and throwables as ClassGraph throws Throwables and not Exceptions
            Injector.LOGGER.error(Injector.CANNOT_LOAD_PROVIDED_CLASS, aClass.getName(), t.getMessage());
            Injector.LOGGER.throwing(t);
        }
    }

    /**
     * Inject an instance at runtime.
     *
     * @param instance    The instance to inject.
     * @param packageName The package name to scan.
     * @param classLoader The class loader to use.
     * @throws IllegalAccessException If the field is not accessible.
     */
    @SuppressWarnings({
            "java:S3011", // "Make sure that this accessibility update is safe here."
            "java:S1133" // "Remove the declaration of thrown exception"
    })
    public void injectAtRuntime(Object instance, String packageName, ClassLoader classLoader) throws IllegalAccessException {
        try (ScanResult scanResult = this.classGraph.scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithFieldAnnotation(Inject.class.getName())) {
                for (FieldInfo fieldInfo : classInfo.getDeclaredFieldInfo()) {
                    if (fieldInfo.hasAnnotation(Inject.class.getName()) && fieldInfo.isPublic()) {
                        Field field = fieldInfo.loadClassAndGetField();

                        if (field.getType().isAssignableFrom(instance.getClass())) {
                            field.set(null, field.getType().cast(instance));
                            continue;
                        }

                        this.injectIntoField(field);
                    }
                }
            }
        }
    }

    @SuppressWarnings("java:S3011") // "Make sure that this accessibility update is safe here."
    private void injectIntoField(Field field) throws IllegalAccessException {
        Object optimalValue = null;
        InjectPriority optimalPriority = null;

        for (Map.Entry<Class<?>, Tuple2<InjectPriority, Object>> entry : this.classes.entrySet()) {
            Class<?> aClass = entry.getKey();

            if (!field.getType().isAssignableFrom(aClass)) {
                continue;
            }

            Tuple2<InjectPriority, Object> tuple2 = entry.getValue();
            InjectPriority priority = tuple2.getT1();
            Object value = tuple2.getT2();

            if (optimalValue == null || priority.ordinal() > optimalPriority.ordinal()) {
                optimalValue = value;
                optimalPriority = priority;
            }
        }

        if (optimalValue != null) {
            field.set(null, field.getType().cast(optimalValue));
        } else {
            Injector.LOGGER.error("Cannot inject into {}#{} : No provider found", field.getDeclaringClass().getName(), field.getName());
        }
    }

    /**
     * Get the class loaders.
     *
     * @return The class loaders.
     */
    public Set<ClassLoader> getClassLoaders() {
        return this.classLoaderSet;
    }

    /**
     * Get the {@link ClassGraph} instance.
     *
     * @return The {@link ClassGraph} instance.
     */
    public ClassGraph getClassGraph() {
        return this.classGraph;
    }
}
