package net.ogiekako.algorithm.dataStructure.tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class BinaryIndexedTreeTest {
    @Test
    public void test() throws Exception {
        int N = 10;
        BinaryIndexedTree.INT bit = new BinaryIndexedTree.INT(N);
        Random rnd = new Random(14908102784L);
        int[] is = new int[N];
        for (int i = 0; i < 10000; i++) {
            if (rnd.nextBoolean()) {
                int p = rnd.nextInt(N);
                int v = rnd.nextInt(100);
                is[p] += v;
                bit.add(p, v);
            } else {
                int to = rnd.nextInt(N + 1);
                int res = bit.sum(to);
                int exp = 0;
                for (int j = 0; j < to; j++) exp += is[j];
                Assert.assertEquals(exp, res);
            }
        }
    }
}
