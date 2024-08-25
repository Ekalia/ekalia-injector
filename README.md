# Ekalia Injector

[![Release version](https://img.shields.io/nexus/maven-releases/fr.ekalia.injector/ekalia-injector?server=https%3A%2F%2Fnexus.ekalia.fr&label=Release&color=green&link=https%3A%2F%2Fnexus.ekalia.fr%2F%23browse%2Fbrowse%3Amaven-release%3Afr%252Fekalia%252Finjector%252Fekalia-injector)](https://nexus.ekalia.fr/#browse/browse:maven-releases:fr%2Fekalia%2Finjector%2Fekalia-injector) [![Snapshot Version](https://img.shields.io/nexus/maven-public/fr.ekalia.injector/ekalia-injector?server=https%3A%2F%2Fnexus.ekalia.fr&label=Snapshot&color=blue&link=https%3A%2F%2Fnexus.ekalia.fr%2F%23browse%2Fbrowse%3Amaven-public%3Afr%252Fekalia%252Finjector%252Fekalia-injector)](https://nexus.ekalia.fr/#browse/browse:maven-snapshots:fr%2Fekalia%2Finjector%2Fekalia-injector)

This is an object injector for Java programs that is used to inject objects into static fields of classes, instead of
passing them as arguments to constructors. This is useful when you have a lot of classes that need to access the same
object, but you don't want to pass it as an argument to every constructor.

## Installation

### Maven

If you use Maven you should use the following code :

```xml
<repositories>
    ...
    <repository>
        <id>ekalia</id>
        <url>https://nexus.ekalia.fr/repository/maven-public/</url>
    </repository>
    ...
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>fr.ekalia.injector</groupId>
        <artifactId>ekalia-injector</artifactId>
        <version>latest</version>
        <scope>provided</scope>
    </dependency>
    ...
</dependencies>
```

### Gradle

If you use Gradle you should use the following code :

```groovy
repositories {
    maven {
        url = uri("https://nexus.ekalia.fr/repository/maven-public/")
    }
}

dependencies {
    compileOnly("fr.ekalia.injector:ekalia-injector:latest")
}
```

## Use injector

In order to use the injector, you need to create an instance of the `Injector` class.

```java
Injector injector = new Injector();
```

You can add class loaders to the injector to load classes from different sources:

```java
injector.addClassLoader(myClassLoader);
```

Then you can start the injector:

```java
injector.startInjection(currentClassLoader, "my.really.cool.package");
injector.

startInjection(myClassLoader, "my.really.cool.package","my.other.cool.package");
```

When you start the injector, it will scan all the classes in the class loaders and inject the provided classes into the
fields of the classes that have the `@Inject` annotation.

### Provide a class

You have 2 options to provide a class to the injector:

#### Use the @Provides annotation

If you want the class to be automatically provided by the injector, you can use the `@Provides` annotation like this:

```java
@Provides
public class MyProvidedClass {
    // ...
}
```

When you start the injector, it will automatically create an instance of this class using the default constructor.

#### Provide the class manually

If you want to provide the class manually, you can use the `registerInjection` method like this:

```java
injector.registerInjection(myProvidedClassInstance);
```

If you want to set a priority (see below for explanations about priority) for the provided class, you can use the
`registerInjection` method like this:

```java
injector.registerInjection(myProvidedClassInstance, InjectPriority.HIGH);
```

### Inject into a field

To mark a field for injection, you need to use the `@Inject` annotation like this:

```java
public class MyClass {

    @Inject
    private MyProvidedClass myProvidedClass;

    // ...
}
```

When you start the injector, it will automatically inject the provided class into the field.

### Priority

The injector uses priorities to determine which provided class to inject into a field. The default priority is
`InjectPriority.NORMAL`.

The priorities are:

- `InjectPriority.LOWEST`
- `InjectPriority.LOW`
- `InjectPriority.NORMAL`
- `InjectPriority.HIGH`
- `InjectPriority.HIGHEST`

The order of priorities is from **lowest** to **highest**: if a field has multiple provided classes with different
priorities, the one with the lowest priority will be injected.

You can set the priority of a provided class when you register it with the injector or in the `@Provides` annotation:

```java
@Provides(priority = InjectPriority.HIGH)
public class MyProvidedClass {
    // ...
}
```

## Contributing

Issues and pull requests are welcome. For major changes, please open an issue first to discuss what you would like to
change.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details.