package net.ogiekako.algorithm.iteration;

public class Iteration {
    public static void iterateBitCombination(int n, int k, IntFunc function) {
        if(n<0 || n>32)throw new IllegalArgumentException("n must be non-negative and <= 32");
        if(k<0 || k>n)throw new IllegalArgumentException("k must be non-negative and <= n");
        for (int comb = k==32 ? -1 : (1 << k) - 1; ; ) {

            function.doIt(comb);

            int x = Integer.lowestOneBit(comb);
            int y = comb + x;// lowest consecutive ones turn into zeros.
            if(y==0 || y<<31-n<0)break;
            int lowestConsecutiveOnes = comb & ~y;
            int smallerConsecutiveOnes = lowestConsecutiveOnes / x >> 1;
            comb = y | smallerConsecutiveOnes;
        }
    }

    public static interface IntFunc {
        void doIt(int comb);
    }

    public static void iterateLongBitCombination(int n, int k, LongFunc function) {
        for (long comb = (1L << k) - 1; comb < 1L << n; ) {

            function.doIt(comb);

            long x = comb & -comb, y = comb + x;
            comb = ((comb & ~y) / x >>> 1) | y;
        }
    }

    public static interface LongFunc {
        void doIt(long comb);
    }

    public static void iterateSubset(int mask, IntFunc function) {
        for (int sub = mask; ; sub = sub - 1 & mask) {
            function.doIt(sub);
            if (sub == 0) break;
        }
    }
}
