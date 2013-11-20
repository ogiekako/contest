package net.ogiekako.algorithm.utils;
import junit.framework.Assert;

import java.util.Collection;
public class AssertionUtils {
    public static void assertAllBetween(Collection<Integer> xs, int from, int to) {
        for(int x:xs) Assert.assertTrue(from <= x && x < to);
    }
    public static void assertContains(int[] array, int target) {
        boolean valid = false;
        for(int value : array) if(value == target) valid = true;
        Assert.assertTrue(valid);
    }
}
