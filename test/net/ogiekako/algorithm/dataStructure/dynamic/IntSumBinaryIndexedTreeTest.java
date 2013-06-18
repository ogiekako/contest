package net.ogiekako.algorithm.dataStructure.dynamic;

import junit.framework.Assert;
import net.ogiekako.algorithm.dataStructure.tree.IntSumBinaryIndexedTree;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class IntSumBinaryIndexedTreeTest {
    @Test
    public void testAll() throws Exception {
        Random rnd = new Random(1204981240L);
        for (int n : new int[]{1, 2, 7, 8, 9, 100}) {
            int[] arr = new int[n];
            IntSumBinaryIndexedTree bit = new IntSumBinaryIndexedTree(n);
            for (int iteration = 0; iteration < 10000; iteration++) {
                int mode = rnd.nextInt(4);
                if (mode == 0) {// add
                    int i = rnd.nextInt(n);
                    int v = rnd.nextInt(5 + 1 + 5) - 5;
                    bit.add(i, v);
                    arr[i] += v;
                } else if (mode == 1) {// set
                    int i = rnd.nextInt(n);
                    int v = rnd.nextInt(5 + 1 + 5) - 5;
                    bit.set(i, v);
                    arr[i] = v;
                } else if (mode == 2) {// sum
                    int s = rnd.nextInt(n);
                    int t = rnd.nextInt(n);
                    if (s > t) {int tmp = t; t = s; s = tmp;}
                    long exp = 0;
                    for (int i = s; i < t; i++) exp += arr[i];
                    long res = bit.sum(s, t);
                    Assert.assertEquals(exp, res);
                } else if (mode == 3) {// kth
                    // set all values positive
                    for (int i = 0; i < n; i++) {
                        arr[i] = Math.abs(arr[i]);
                        long v = bit.sum(i, i + 1);
                        if (v < 0) bit.set(i, -v);
                    }
                    long size = 0;
                    for (long a : arr) size += a;
                    Assert.assertEquals(size, bit.sum(0, n));
                    if (size > 0) {
                        int k = rnd.nextInt((int) size);
                        int res = bit.kth(k);
                        int exp = -1;
                        for (int s = 0; s <= k; ) s += arr[++exp];
                        Assert.assertEquals(String.format("%s, k=%d, res=%d, exp=%d", Arrays.toString(arr), k, res, exp), exp, res);
                    }
                }
            }
        }
    }
}
