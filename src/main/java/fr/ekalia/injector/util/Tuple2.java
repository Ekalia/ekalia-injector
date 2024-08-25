/*
 * Copyright 2024 Ekalia <contact@ekalia.fr>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
