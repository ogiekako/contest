package net.ogiekako.algorithm.utils;

import java.util.HashMap;

public class HashCounter<T> extends HashMap<T, Long> implements Counter<T> {
    public void add(T key, long value) {
        Long current = get(key);
        if (current == null) put(key, value);
        else put(key, current + value);
    }
}
