package net.ogiekako.topcoder;

import java.util.Arrays;

public class InputGenerator<P, R> {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        String[] strings = args.getClass().newInstance();
        System.out.println(Arrays.toString(strings));
        Class<Integer> integerClass = int.class;

    }
}
