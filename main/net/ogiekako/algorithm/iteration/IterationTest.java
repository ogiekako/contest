package net.ogiekako.algorithm.iteration;

import net.ogiekako.algorithm.math.MathUtils;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class IterationTest {
    int num14 = 14;
    int get14(){
        return num14;
    }
    @Test
    public void testIterateBitCombination() throws Exception {
        long[][] C = MathUtils.combination(50);
        long start = System.currentTimeMillis();
        int n = get14() * 2, k = get14();
        for (int comb = k == 32 ? -1 : (1 << k) - 1, __comb = ~(1 << n); __comb != 0 && __comb << 31 - n >= 0; comb = (__comb = comb + (comb & -comb)) | (comb & ~__comb) / (comb == 0 ? 1 : comb & -comb) >> 1) {
            // do nothing
        }
        long time = System.currentTimeMillis() - start;
        System.err.println("time = " + time);
//        System.err.println("iteration = " + C[n][k]);
        start = System.currentTimeMillis();
        int iteration = 0;
        for(int comb=(1<<k)-1;comb<1<<n;){
            iteration++;
            int x = comb & -comb;
            int y = comb + x;
            comb = y | (~y&comb)/x>>1;
        }
        if(iteration!=C[n][k])throw null;
        time = System.currentTimeMillis() - start;// times were almost same.
        System.err.println("time = " + time);
//        System.err.println("iteration = " + C[n][k]);
        start = System.currentTimeMillis();
        for (int i = 0; i < C[n][k]; i++){

        }
        time = System.currentTimeMillis() - start;
        System.err.println("time = " + time);
        start = System.currentTimeMillis();
        Iteration.iterateBitCombination(n,k,new Iteration.IntFunc() {
            @Override
            public void doIt(int comb) {
                // do nothing
            }
        });
        time = System.currentTimeMillis() - start;
        System.err.println("time = " + time);
    }

    @Test
    public void testIterateBitCombination2() throws Exception {
        long[][] C = MathUtils.combination(50);
        int[] ns = {0, 15, 30, 31, 32};
        for (int n : ns) {
            int[] ks;
            if (n <= 15) {
                ks = new int[n + 1];
                for (int i = 0; i < n + 1; i++) ks[i] = i;
            } else {
                ks = new int[]{0, 5, n - 1, n};
            }
            for (int k : ks) {
                System.err.println(n + " " + k);
                final Set<Integer> set = new TreeSet<Integer>();
                final AtomicInteger count = new AtomicInteger();
                Iteration.iterateBitCombination(n, k, new Iteration.IntFunc() {
                    @Override
                    public void doIt(int comb) {
                        set.add(comb);
                        count.incrementAndGet();
                    }
                });
                if (set.size() != C[n][k]) throw null;
                if (set.size() != count.get()) throw null;
                for (int mask : set)
                    if (Integer.bitCount(mask) != k) {
                        System.err.println(Integer.toBinaryString(mask));
                        throw null;
                    }

                Set<Integer> set2 = new TreeSet<Integer>();
                for (int comb = k == 32 ? -1 : (1 << k) - 1, __comb = ~(1 << n); __comb != 0 && __comb << 31 - n >= 0; comb = (__comb = comb + (comb & -comb)) | (comb & ~__comb) / (comb == 0 ? 1 : comb & -comb) >> 1) {
                    set2.add(comb);
                }
                if (!set.equals(set2)) throw null;
            }
        }
    }


}
