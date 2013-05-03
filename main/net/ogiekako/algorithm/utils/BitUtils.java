package net.ogiekako.algorithm.utils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/19
 * Time: 9:54
 * To change this template use File | Settings | File Templates.
 */
public class BitUtils {
    public static boolean contains(int bitMask, int index) {
        return (bitMask >> index & 1) == 1;
    }
}
