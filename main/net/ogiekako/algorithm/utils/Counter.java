package net.ogiekako.algorithm.utils;

import java.util.Map;

public interface Counter<T> extends Map<T, Long> {
    void add(T key, long value);
}
