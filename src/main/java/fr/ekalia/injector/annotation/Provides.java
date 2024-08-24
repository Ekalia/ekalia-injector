package fr.ekalia.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to provide a class with the injector.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provides {

    /**
     * The priority of the class.
     * Default is normal.
     *
     * @return The priority
     */
    InjectPriority priority() default InjectPriority.NORMAL;

}
