package net.ogiekako.algorithm.utils.counter;

import java.util.TreeMap;

public class TreeCounter<T> extends TreeMap<T, Long> implements Counter<T> {
    public long add(T key, long value) {
        long res = get(key) + value;
        put(key, res);
        return res;
    }

    public Long get(Object key) {
        Long res = super.get(key);
        return res == null ? 0 : res;
    }
}
