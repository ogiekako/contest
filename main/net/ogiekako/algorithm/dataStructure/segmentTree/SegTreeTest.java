package net.ogiekako.algorithm.dataStructure.segmentTree;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SegTreeTest {
    private static final boolean LOG = false;

    @Test
    public void testRMQ() throws Exception {
        test(new Gen<Long, Long>() {
            public Long genDelta(Random rnd) {
                return rnd.nextLong();
            }

            public SegTree<Long, Long> genTree(int n) {
                return new SegTree.Min(n);
            }

        });
    }

    @Test
    public void testSum() {
        test(new Gen<Long, Integer>() {
            public SegTree<Long, Integer> genTree(int n) {
                return new SegTree.Sum(n);
            }

            public Integer genDelta(Random rnd) {
                return rnd.nextInt(201) - 100;
            }

        });
    }

    @Test
    public void testCoin() {
        test(new Gen<Integer, Boolean>() {

            public SegTree<Integer, Boolean> genTree(int n) {
                return new SegTree.Coin(n);
            }

            public Boolean genDelta(Random rnd) {
                return rnd.nextBoolean();
            }
        });
    }

    private <V, D> void test(Gen<V, D> gen) {
        testValidity(gen);
        testSpeed(gen);
    }

    private <V, D> void testValidity(Gen<V, D> gen) {
        Random rnd = new Random(1209812940L);
        for (int n : new int[]{2, 3, 4, 5, 63, 64, 65, 1}) {
            @SuppressWarnings("unchecked") D[] data = (D[]) new Object[n];
            @SuppressWarnings("unchecked") V[] value = (V[]) new Object[n];
            SegTree<V, D> tree = gen.genTree(n);
            for (int i = 0; i < n; i++) value[i] = tree.identity();
            for (int iter = 0; iter < 1000; iter++) {
                int mode = rnd.nextInt(3);// set, add, answer
                if (mode == 0) {// set
                    int s = rnd.nextInt(n), t = rnd.nextInt(n);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    D v = gen.genDelta(rnd);
                    log("set", s, t, v);
                    tree.set(s, t, v);
                    for (int i = s; i < t; i++) {
                        data[i] = v;
                        value[i] = tree.operate(tree.identity(), 1, data[i]);
                    }
                } else if (mode == 1) {
                    int s = rnd.nextInt(n), t = rnd.nextInt(n);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    D v = gen.genDelta(rnd);
                    log("add", s, t, v);
                    tree.add(s, t, v);
                    for (int i = s; i < t; i++) {
                        data[i] = data[i] == null ? v : tree.join(data[i], v);
                        value[i] = tree.operate(tree.identity(), 1, data[i]);
                    }
                } else if (mode == 2) {
                    int s = rnd.nextInt(n), t = rnd.nextInt(n);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    log("sum", s, t);
                    V res = tree.convolution(s, t);
                    V exp = tree.identity();
                    for (int i = s; i < t; i++) exp = tree.operate(exp, value[i]);
                    Assert.assertEquals(Arrays.toString(data) + " " + s + " " + t, exp, res);
                } else throw new AssertionError();
            }
        }
    }

    private <V, D> void testSpeed(Gen<V, D> gen) {
        Random rnd = new Random(1209812940L);
        int size = 50000;
        int iteration = 50000;
        double limit = 1000;
        for (int o = 0; o < 3; o++) {
            long start = System.currentTimeMillis();
            SegTree<V, D> tree = gen.genTree(size);
            for (int iter = 0; iter < iteration; iter++) {
                int mode = rnd.nextInt(3);// set, add, answer
                if (mode == 0) {// set
                    int s = rnd.nextInt(size), t = rnd.nextInt(size);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    D v = gen.genDelta(rnd);
//                    log("set", s, t, v);
                    tree.set(s, t, v);
                } else if (mode == 1) {
                    int s = rnd.nextInt(size), t = rnd.nextInt(size);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    D v = gen.genDelta(rnd);
//                    log("add", s, t, v);
                    tree.add(s, t, v);
                } else if (mode == 2) {
                    int s = rnd.nextInt(size), t = rnd.nextInt(size);
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
//                    log("sum", s, t);
                    V res = tree.convolution(s, t);
                } else throw new AssertionError();
            }
            long time = System.currentTimeMillis() - start;
            Assert.assertTrue("" + time, time < limit);
        }
    }


    private void log(Object... os) {
        if (LOG) System.out.println(Arrays.deepToString(os));
    }

    interface Gen<V, D> {
        SegTree<V, D> genTree(int n);

        D genDelta(Random rnd);
    }
}
