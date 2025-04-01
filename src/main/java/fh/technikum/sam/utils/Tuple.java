package fh.technikum.sam.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A simple generic class that holds a pair of values (key and value).
 * This class can be used to represent pairs of related data.
 *
 * @param <K> The type of the key (first value).
 * @param <V> The type of the value (second value).
 */
@Data
@AllArgsConstructor
public class Tuple<K, V> {

    /**
     * The key of the tuple.
     */
    private K key;

    /**
     * The value associated with the key.
     */
    private V value;

}
