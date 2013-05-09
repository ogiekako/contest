package net.ogiekako.algorithm.misc.coin;

public class CoinProblem {
    /**
     * calculate the minimum price of the coin problem that greedy algorithm fails on it.
     * O(n^3).
     * <p/>
     * algorithm from paper
     * "A Polynomial-time Algorithm for the Change-Making Problem."
     *
     * @param c c[0] > c[1] ... > c[n-1] = 1
     * @return -1 if greedy is always optimal. minimum counter example otherwise.
     */
    public static long minimumCounterExample(long[] c) {
        int n = c.length;
        if (c[n - 1] != 1) throw new IllegalArgumentException("last element must be 1.");
        for (int i = 0; i < n - 1; i++)
            if (c[i] <= c[i + 1]) throw new IllegalArgumentException("elements must be strictly decreasing.");
        long res = Long.MAX_VALUE;
        for (int i = 1; i < n; i++)
            for (int j = i; j < n; j++) {// first and last index of nonzero element of minimum.
                long[] g = greedy(c, c[i - 1] - 1);
                g[j]++;
                long m = 0;
                long w = 0;

                // g[i..j] is lexicographically greatest minimum for w.
                for (int k = i; k <= j; k++) {
                    m += g[k];
                    w += g[k] * c[k];
                }
                long[] g2 = greedy(c, w);
                for (int k = 0; k < n; k++) m -= g2[k];
                if (m < 0) res = Math.min(res, w);
            }
        return res == Long.MAX_VALUE ? -1 : res;
    }

    static long[] greedy(long[] c, long w) {
        int n = c.length;
        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            res[i] = w / c[i];
            w -= res[i] * c[i];
        }
        return res;
    }
}
