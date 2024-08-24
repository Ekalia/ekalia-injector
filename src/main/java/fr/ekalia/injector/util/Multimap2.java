package fr.ekalia.injector.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A multimap implementation using {@link Tuple2}.
 *
 * @param <K>  The key type.
 * @param <V1> The first value type.
 * @param <V2> The second value type.
 * @author Azn9
 */
@SuppressWarnings("java:S107") // Method has too many parameters (logically, it's a builder)
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

    /**
     * Build a {@link Multimap2} from two {@link Map}.
     *
     * @param map1 The first map.
     * @param map2 The second map.
     * @param <K>  The key type.
     * @param <V1> The first value type.
     * @param <V2> The second value type.
     * @return The multimap.
     * @throws IllegalArgumentException If the two maps have different keys.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            Map<K, V1> map1,
            Map<K, V2> map2
    ) {
        Multimap2<K, V1, V2> multimap2 = new Multimap2<>();

        if (map1.keySet().size() != map2.keySet().size()) {
            throw new IllegalArgumentException("The two maps have different keys.");
        }

        for (Entry<K, V1> entry : map1.entrySet()) {
            multimap2.put(entry.getKey(), entry.getValue(), map2.get(entry.getKey()));
        }

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with one key and two values.
     *
     * @param key1    The key.
     * @param value11 The first value.
     * @param value12 The second value.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12
    ) {
        Multimap2<K, V1, V2> multimap2 = new Multimap2<>();
        multimap2.put(key1, value11, value12);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with two keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12);
        multimap2.put(key2, value21, value22);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with three keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22);
        multimap2.put(key3, value31, value32);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with four keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32);
        multimap2.put(key4, value41, value42);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with five keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param key5    The fifth key.
     * @param value51 The first value for the fifth key.
     * @param value52 The second value for the fifth key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42);
        multimap2.put(key5, value51, value52);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with six keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param key5    The fifth key.
     * @param value51 The first value for the fifth key.
     * @param value52 The second value for the fifth key.
     * @param key6    The sixth key.
     * @param value61 The first value for the sixth key.
     * @param value62 The second value for the sixth key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52,
            K key6, V1 value61, V2 value62
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42, key5, value51, value52);
        multimap2.put(key6, value61, value62);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with seven keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param key5    The fifth key.
     * @param value51 The first value for the fifth key.
     * @param value52 The second value for the fifth key.
     * @param key6    The sixth key.
     * @param value61 The first value for the sixth key.
     * @param value62 The second value for the sixth key.
     * @param key7    The seventh key.
     * @param value71 The first value for the seventh key.
     * @param value72 The second value for the seventh key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52,
            K key6, V1 value61, V2 value62,
            K key7, V1 value71, V2 value72
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42, key5, value51, value52, key6, value61, value62);
        multimap2.put(key7, value71, value72);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with eight keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param key5    The fifth key.
     * @param value51 The first value for the fifth key.
     * @param value52 The second value for the fifth key.
     * @param key6    The sixth key.
     * @param value61 The first value for the sixth key.
     * @param value62 The second value for the sixth key.
     * @param key7    The seventh key.
     * @param value71 The first value for the seventh key.
     * @param value72 The second value for the seventh key.
     * @param key8    The eighth key.
     * @param value81 The first value for the eighth key.
     * @param value82 The second value for the eighth key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52,
            K key6, V1 value61, V2 value62,
            K key7, V1 value71, V2 value72,
            K key8, V1 value81, V2 value82
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42, key5, value51, value52, key6, value61, value62, key7, value71, value72);
        multimap2.put(key8, value81, value82);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with nine keys and two values.
     *
     * @param key1    The first key.
     * @param value11 The first value for the first key.
     * @param value12 The second value for the first key.
     * @param key2    The second key.
     * @param value21 The first value for the second key.
     * @param value22 The second value for the second key.
     * @param key3    The third key.
     * @param value31 The first value for the third key.
     * @param value32 The second value for the third key.
     * @param key4    The fourth key.
     * @param value41 The first value for the fourth key.
     * @param value42 The second value for the fourth key.
     * @param key5    The fifth key.
     * @param value51 The first value for the fifth key.
     * @param value52 The second value for the fifth key.
     * @param key6    The sixth key.
     * @param value61 The first value for the sixth key.
     * @param value62 The second value for the sixth key.
     * @param key7    The seventh key.
     * @param value71 The first value for the seventh key.
     * @param value72 The second value for the seventh key.
     * @param key8    The eighth key.
     * @param value81 The first value for the eighth key.
     * @param value82 The second value for the eighth key.
     * @param key9    The ninth key.
     * @param value91 The first value for the ninth key.
     * @param value92 The second value for the ninth key.
     * @param <K>     The key type.
     * @param <V1>    The first value type.
     * @param <V2>    The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52,
            K key6, V1 value61, V2 value62,
            K key7, V1 value71, V2 value72,
            K key8, V1 value81, V2 value82,
            K key9, V1 value91, V2 value92
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42, key5, value51, value52, key6, value61, value62, key7, value71, value72, key8, value81, value82);
        multimap2.put(key9, value91, value92);

        return multimap2;
    }

    /**
     * Build a {@link Multimap2} with nine keys and two values.
     *
     * @param key1     The first key.
     * @param value11  The first value for the first key.
     * @param value12  The second value for the first key.
     * @param key2     The second key.
     * @param value21  The first value for the second key.
     * @param value22  The second value for the second key.
     * @param key3     The third key.
     * @param value31  The first value for the third key.
     * @param value32  The second value for the third key.
     * @param key4     The fourth key.
     * @param value41  The first value for the fourth key.
     * @param value42  The second value for the fourth key.
     * @param key5     The fifth key.
     * @param value51  The first value for the fifth key.
     * @param value52  The second value for the fifth key.
     * @param key6     The sixth key.
     * @param value61  The first value for the sixth key.
     * @param value62  The second value for the sixth key.
     * @param key7     The seventh key.
     * @param value71  The first value for the seventh key.
     * @param value72  The second value for the seventh key.
     * @param key8     The eighth key.
     * @param value81  The first value for the eighth key.
     * @param value82  The second value for the eighth key.
     * @param key9     The ninth key.
     * @param value91  The first value for the ninth key.
     * @param value92  The second value for the ninth key.
     * @param key10    The tenth key.
     * @param value101 The first value for the tenth key.
     * @param value102 The second value for the tenth key.
     * @param <K>      The key type.
     * @param <V1>     The first value type.
     * @param <V2>     The second value type.
     * @return The multimap.
     */
    public static <K, V1, V2> Multimap2<K, V1, V2> of(
            K key1, V1 value11, V2 value12,
            K key2, V1 value21, V2 value22,
            K key3, V1 value31, V2 value32,
            K key4, V1 value41, V2 value42,
            K key5, V1 value51, V2 value52,
            K key6, V1 value61, V2 value62,
            K key7, V1 value71, V2 value72,
            K key8, V1 value81, V2 value82,
            K key9, V1 value91, V2 value92,
            K key10, V1 value101, V2 value102
    ) {
        Multimap2<K, V1, V2> multimap2 = Multimap2.of(key1, value11, value12, key2, value21, value22, key3, value31, value32, key4, value41, value42, key5, value51, value52, key6, value61, value62, key7, value71, value72, key8, value81, value82, key9, value91, value92);
        multimap2.put(key10, value101, value102);

        return multimap2;
    }
}
