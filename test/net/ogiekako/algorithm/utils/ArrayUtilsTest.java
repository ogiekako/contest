package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.utils.interfaces.Classifiable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static net.ogiekako.algorithm.utils.ArrayUtils.*;

public class ArrayUtilsTest {
    @Test
    public void testCreateArray() throws Exception {
        E e = new E();
        e.id = 1;
//        ArrayUtils.createArray(100, e);
    }

    @Test
    public void classify() {
        class E implements Classifiable {
            int id, color;

            E(int id, int color) {
                this.id = id;
                this.color = color;
            }

            @Override
            public int getKey() {
                return color;
            }
        }
        E[] es = {new E(1, 200), new E(2, 100), new E(3, 200), new E(4, (int) 1e9), new E(5, 100)};
//        E[][] result = ArrayUtils.classify(es);
//        Assert.assertEquals(3, result.length);
//        Assert.assertEquals(2, result[0].length);
//        Assert.assertEquals(2, result[1].length);
//        Assert.assertEquals(1, result[2].length);
    }

    @Test
    public void testConcatenate_SubArray_RotateLeft() {
        Random rnd = new Random(2349088234890L);
        for (int i = 0; i < 1000; i++) {
            int n = rnd.nextInt(100) + 1;
            int rot = rnd.nextInt(n);
            int[] is = new int[n];
            for (int j = 0; j < n; j++) is[j] = j;
            int[] exp = concatenate(subArray(is, rot), subArray(is, 0, rot));
            rotateLeft(is, rot);
            if (!Arrays.equals(is, exp)) {
                throw new RuntimeException();
            }
        }
    }

    @Test
    public void testCopyOf() {
        Integer[] Is = new Integer[5];
        for (int i = 0; i < 5; i++) Is[i] = i;
        for (int n = 0; n <= 10; n++) {
            System.err.println(n);
            Integer[] Js = ArrayUtils.copyOf(Is, n, new Integer[0]);
            Integer[] exp = Arrays.copyOf(Is, n);
            if (Is == Js)
                throw null;
            debug(Js);
            debug(exp);
            if (!Arrays.equals(Js, exp))
                throw null;
        }
    }

    @Test
    public void testAppend() {
        int n = 5;
        Integer[] Is = new Integer[n];
        for (int i = 0; i < n; i++) Is[i] = i;
        Integer[] res = append(Is, n);
        for (int i = 0; i < n + 1; i++)
            if (res[i] != i)
                throw null;
    }

    class E {
        int id;
    }

}
