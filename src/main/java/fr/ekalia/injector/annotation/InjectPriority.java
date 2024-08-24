package fr.ekalia.injector.annotation;

/**
 * The priority of a class.
 * The priority is used to determine the order of the class injection.
 * The order is from the lowest priority to the highest priority.
 * For example, if you have a class with the priority "LOWEST" and another class with the priority "HIGHEST",
 * the class with the priority "LOWEST" will be injected before the class with the priority "HIGHEST", so the
 * one with the priority "HIGHEST" will replace the one with the priority "LOWEST".
 *
 * @author Azn9
 */
public enum InjectPriority {

    /**
     * The lowest priority.
     */
    LOWEST,
    /**
     * A little bit lower priority.
     */
    LOW,
    /**
     * The default priority.
     */
    NORMAL,
    /**
     * A little bit higher priority.
     */
    HIGH,
    /**
     * The highest priority.
     */
    HIGHEST

}
