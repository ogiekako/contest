package net.ogiekako.algorithm.utils.counter;

import java.util.Map;

public interface Counter<T> extends Map<T, Long> {
    /**
     * Returns updated value.
     */
    long add(T key, long value);

    /**
     * Returns zero by default.
     */
    Long get(Object o);
}
