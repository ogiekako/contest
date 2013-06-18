package net.ogiekako.algorithm.utils;

public class BitUtils {
    public static boolean contains(int bitMask, int index) {
        return (bitMask >> index & 1) == 1;
    }
}
