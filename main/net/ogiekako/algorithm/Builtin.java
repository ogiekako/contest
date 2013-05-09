package net.ogiekako.algorithm;

import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Cast;
import net.ogiekako.algorithm.utils.StringUtils;

public class Builtin {
    ////////// min //////////
    public static int min(int a, int b) { return Math.min(a, b); }
    public static long min(long a, long b) { return Math.min(a, b); }
    public static double min(double a, double b) { return Math.min(a, b); }
    public static <T extends Comparable<T>> T min(T a, T b) {return a.compareTo(b) <= 0 ? a : b;}

    ////////// max //////////
    public static int max(int a, int b) { return Math.max(a, b); }
    public static int max(int... a) {return ArrayUtils.max(a);}
    public static long max(long a, long b) { return Math.max(a, b); }
    public static double max(double a, double b) { return Math.max(a, b); }
    public static <T extends Comparable<T>> T max(T a, T b) {return a.compareTo(b) >= 0 ? a : b;}

    ////////// sort //////////
    public static void sort(int[] a) {ArrayUtils.sort(a);}

    ////////// reverse //////////
    public static void reverse(int[] a) {ArrayUtils.reverse(a);}

    ////////// cast //////////
    public static int[] toInt(long[] a) {return Cast.toInt(a);}
    public static int[] toInt(String[] a) {return Cast.toInt(a);}
    public static int[][] toInt(long[][] a) {return Cast.toInt(a);}

    ////////// concat //////////
    public static String concat(String[] a) {return StringUtils.concat(a);}
}
