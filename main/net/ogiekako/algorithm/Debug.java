package net.ogiekako.algorithm;

import java.util.Arrays;

public class Debug {
    public static void print(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
