package net.ogiekako.algorithm.utils;

import java.util.Collection;

public class Cast {
    public static int[] toInt(Collection<Integer> integerCollection) {
        return toInt(integerCollection.toArray(new Integer[integerCollection.size()]));
    }

    public static int[] toInt(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }

    public static int[] toInt(String[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Integer.parseInt(Is[i]);
        return is;
    }

    public static int[] toInt(long[] longArray) {
        int[] res = new int[longArray.length];
        for (int i = 0; i < res.length; i++) res[i] = (int) longArray[i];
        return res;
    }

    public static int[][] toInt(long[][] longArray) {
        int[][] res = new int[longArray.length][];
        for (int i = 0; i < res.length; i++) res[i] = toInt(longArray[i]);
        return res;
    }

    public static long[] toLong(Collection<Long> longCollection) {
        return toLong(longCollection.toArray(new Long[longCollection.size()]));
    }

    public static double[] toDoubleArray(Collection<Double> collection) {
        return toDouble(collection.toArray(new Double[collection.size()]));
    }

    public static double[] toDouble(Double[] Is) {
        double[] is = new double[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }

    public static long[] toLong(Long[] Ls) {
        long[] ls = new long[Ls.length];
        for (int i = 0; i < ls.length; i++) ls[i] = Ls[i];
        return ls;
    }

    public static long[] toLong(int[] intArray) {
        long[] res = new long[intArray.length];
        for (int i = 0; i < res.length; i++) res[i] = intArray[i];
        return res;
    }

    public static long[][] toLong(int[][] intArray) {
        long[][] res = new long[intArray.length][];
        for (int i = 0; i < res.length; i++) res[i] = toLong(intArray[i]);
        return res;
    }

    public static String[] toString(long[] ls) {
        String[] res = new String[ls.length];
        for (int i = 0; i < res.length; i++) res[i] = String.valueOf(ls[i]);
        return res;
    }
}
