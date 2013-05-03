package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.io.MyScanner;

public class IOUtils {
    public static void readIntArrays(MyScanner in, int[]... arrays) {
        for (int i = 0; i < arrays.length; i++){
            for (int j = 0; j < arrays[i].length; j++)arrays[i][j] = in.nextInt();
        }
    }
}
