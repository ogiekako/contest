package net.ogiekako.algorithm.utils.counter;

import java.util.TreeMap;

public class TreeCounter<T> extends TreeMap<T, Long> implements Counter<T> {
    public void add(T key, long value) {
        Long cur = get(key);
        if (cur == null) put(key, value);
        else put(key, cur + value);
    }
}
