package net.ogiekako.algorithm.utils;

import java.util.Collection;

public class CollectionUtils {
    public static long sum(Collection<Integer> collection) {
        long res = 0;
        for (int i : collection) res += i;
        return res;
    }
}
