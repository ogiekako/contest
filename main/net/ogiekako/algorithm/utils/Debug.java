package net.ogiekako.algorithm.utils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/24
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class Debug {
    public static void show(boolean[][] field) {
        for(boolean[] bs : field)show(bs);
    }

    private static void show(boolean[] array) {
        for(boolean b:array){
            System.err.print(b ? 1 : 0);
        }
        System.err.println();
    }

    public static void tle() {
        for(;;);
    }
}
