package fr.ekalia.injector.util;

/**
 * A tuple of two elements
 *
 * @param <T1> The type of the first element
 * @param <T2> The type of the second element
 *
 * @author Vortezz
 */
public class Tuple2<T1, T2> {

    private final T1 t1;
    private final T2 t2;

    /**
     * Create a new tuple
     *
     * @param t1 The first element
     * @param t2 The second element
     */
    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    /**
     * Get the first element
     *
     * @return The first element
     */
    public T1 getT1() {
        return t1;
    }

    /**
     * Get the second element
     *
     * @return The second element
     */
    public T2 getT2() {
        return t2;
    }
}
