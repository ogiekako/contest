package net.ogiekako.algorithm.dataStructure.heap;
import junit.framework.Assert;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
public class Tester {
    public static class TestCase {
        Type[] type;
        int[] x;
        int[] answer;
        TreeSet<Double> set1, set2;
    }

    public static enum Type {
        POLL1, POLL2, MELD, ADD1, ADD2, SIZE
    }

    private Tester() {}

    static boolean meldable;
    public static void test(Gen gen) {
        List<TestCase> tests = new ArrayList<TestCase>();
        Random rnd = new Random(124912048L);
        meldable = gen.meldable();
        for (int i = 0; i < 10; i++) {
            tests.add(gen(1000, true, rnd));
            tests.add(gen(100000, false, rnd));
        }
        int max1 = 0, max2 = 0;
        long maxTime = 0;
        for (TestCase test : tests) {
            int n = test.x.length;
            Heap<Integer> heap1 = gen.create();
            Heap<Integer> heap2 = gen.create();
            long start = System.currentTimeMillis();
            int curMax1 = 0, curMax2 = 0;
            for (int i = 0; i < n; i++) {
                int res = 0;
                switch (test.type[i]) {
                    case POLL1:
                        if (heap1.size() == 0) res = -1;
                        else res = heap1.poll();
                        break;
                    case POLL2:
                        if (heap2.size() == 0) res = -1;
                        else res = heap2.poll();
                        break;
                    case MELD:
                        heap1.meld(heap2);
                        heap2 = new BinomialHeap<Integer>();
                        break;
                    case ADD1:
                        heap1.add(test.x[i]);
                        break;
                    case ADD2:
                        heap2.add(test.x[i]);
                        break;
                    case SIZE:
                        res = heap1.size() + heap2.size();
                        break;
                }
                curMax1 = Math.max(curMax1, heap1.size());
                curMax2 = Math.max(curMax2, heap2.size());
                if (test.answer != null)
                    Assert.assertEquals(test.answer[i], res);
            }
            long time = System.currentTimeMillis() - start;
            if (test.set1 != null) {
                assertEquals(test.set1, heap1);
                assertEquals(test.set2, heap2);
            }
            System.out.println("time: " + time);
            System.out.println("max1: " + curMax1);
            System.out.println("max2: " + curMax2);
            max1 = Math.max(max1, curMax1);
            max2 = Math.max(max2, curMax2);
            maxTime = Math.max(maxTime, time);
        }
        System.out.println("overall max1: " + max1);
        System.out.println("overall max2: " + max2);
        System.out.println("maxTime: " + maxTime);
    }
    private static void assertEquals(TreeSet<Double> set, Heap<Integer> heap) {
        while (heap.size() > 0) {
            int res = heap.poll();
            int exp = (int) Math.round(set.pollFirst());
            if (res != exp) throw new AssertionError();
        }
        if (!set.isEmpty()) throw new AssertionError();
    }

    private static TestCase gen(int n, boolean createAnswer, Random rnd) {
        int valueRange = (int) Math.pow(10, rnd.nextInt(10));
        Type[] type = new Type[n];
        int[] x = new int[n];
        int[] answer = new int[n];
        TreeSet<Double> set1 = new TreeSet<Double>();
        TreeSet<Double> set2 = new TreeSet<Double>();
        Type[] allTypes = ArrayUtils.removeAll(Type.values(), Type.MELD, new Type[0]);

        int m = allTypes.length;
        double[] ratio = new double[m];

        for (int i = 0; i < m; i++) ratio[i] = (i == 0 ? 0 : ratio[i - 1]) + rnd.nextDouble();
        for (int i = 0; i < n; i++) {
            double rndVal = rnd.nextDouble() * ratio[m - 1];
            int p;
            for (p = 0; ; p++) if (rndVal < ratio[p]) break;
            type[i] = allTypes[p];
            x[i] = rnd.nextInt(valueRange * 2 + 1) - valueRange;

            if (createAnswer) {
                switch (type[i]) {
                    case POLL1:
                        if (set1.isEmpty()) answer[i] = -1;
                        else answer[i] = (int) Math.round(set1.pollFirst());
                        break;
                    case POLL2:
                        if (set2.isEmpty()) answer[i] = -1;
                        else answer[i] = (int) Math.round(set2.pollFirst());
                        break;
                    case MELD:
                        set1.addAll(set2);
                        set2.clear();
                        break;
                    case ADD1:
                        set1.add(x[i] + 1e-3 * rnd.nextDouble());
                        break;
                    case ADD2:
                        set2.add(x[i] + 1e-3 * rnd.nextDouble());
                        break;
                    case SIZE:
                        answer[i] = set1.size() + set2.size();
                        break;
                }
            }
        }
        TestCase testCase = new TestCase();
        testCase.type = type;
        testCase.x = x;
        if (createAnswer) {
            testCase.answer = answer;
            testCase.set1 = set1;
            testCase.set2 = set2;
        }
        return testCase;
    }

    interface Gen {
        Heap<Integer> create();
        boolean meldable();
    }
}
