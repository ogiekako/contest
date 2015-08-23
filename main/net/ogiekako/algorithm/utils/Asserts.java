package net.ogiekako.algorithm.utils;

import java.util.Collection;

public class Asserts {
    public static void assertAllBetween(Collection<Integer> xs, int from, int to) {
        for (int x : xs) assertTrue(from <= x && x < to);
    }

    public static void assertContains(int[] array, int target) {
        boolean valid = false;
        for (int value : array) if (value == target) valid = true;
        assertTrue(valid);
    }

    public static void assertTrue(boolean b) {
        if (!b) throw new AssertionError("want: true  actual: false");
    }

    public static void assertNonNegative(double value) {
        if (value < 0) throw new AssertionError("want: positive  actual: " + value);
    }
}
