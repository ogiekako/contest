package net.ogiekako.algorithm.utils;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/10
 * Time: 5:14
 * To change this template use File | Settings | File Templates.
 */
public class CollectionUtils {
    public static long sum(Collection<Integer> collection) {
        long res = 0;
        for (int i : collection) res += i;
        return res;
    }

    public static int[] toIntArray(List<Integer> values) {
        int[] res = new int[values.size()];
        for (int i = 0; i < res.length; i++)res[i] = values.get(i);
        return res;
    }

    public static long[] toLongArray(List<Long> values) {
        return Cast.toLong(values);
    }

}
