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

import java.util.HashMap;
import java.util.Map;

/**
 * A multimap implementation using {@link Tuple2}.
 *
 * @param <K>  The key type.
 * @param <V1> The first value type.
 * @param <V2> The second value type.
 * 
 * @author Azn9
 */
public class Multimap2<K, V1, V2> extends HashMap<K, Tuple2<V1, V2>> {

    /**
     * Adds a value to the multimap.
     *
     * @param key    The key.
     * @param value1 The first value.
     * @param value2 The second value.
     */
    public void put(K key, V1 value1, V2 value2) {
        super.put(key, new Tuple2<>(value1, value2));
    }

    /**
     * Gets the first value associated with the key.
     *
     * @param key The key.
     * @return The first value or null if the multimap does not contain the key.
     */
    public V1 getValue1(K key) {
        if (!super.containsKey(key)) {
            return null;
        }

        return super.get(key).getT1();
    }

    /**
     * Gets the second value associated with the key.
     *
     * @param key The key.
     * @return The second value or null if the multimap does not contain the key.
     */
    public V2 getValue2(K key) {
        if (!super.containsKey(key)) {
            return null;
        }

        return super.get(key).getT2();
    }
}
