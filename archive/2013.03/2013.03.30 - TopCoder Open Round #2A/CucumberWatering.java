package tmp;

import java.util.Arrays;
import java.util.TreeSet;

public class CucumberWatering {
    public long theMin(int[] x, int K) {
        K = Math.min(K, x.length);
        TreeSet<Long> xSet = new TreeSet<Long>();
        xSet.add((long) -1e16);
        xSet.add((long) 1e16);
        for (int a : x) xSet.add((long) a);
        long[] cs = tols(xSet.toArray(new Long[xSet.size()]));
        int n = cs.length;
        long[][] dp = new long[n][K + 2];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Long.MAX_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++)
                for (int k = 0; k < K + 1; k++)
                    if (dp[j][k] < Long.MAX_VALUE) {
                        long cost = dp[j][k];
                        for (int l = 0; l < x.length - 1; l++) {
                            long s = x[l], t = x[l + 1];
                            if (s > t) {long tmp = s; s = t; t = tmp;}
                            long a = cs[j], b = cs[i];
                            if (a <= s && s <= b && b <= t) cost += Math.min(s - a, b - s);
                            else if (s <= a && a <= t && t <= b) cost += Math.min(t - a, b - t);
                            else if (a <= s && t <= b) cost += Math.min(t - s, s - a + b - t);
                        }
                        dp[i][k + 1] = Math.min(dp[i][k + 1], cost);
                    }
        }
        return dp[n - 1][K + 1];
    }

    private long[] tols(Long[] Ls) {
        long[] ls = new long[Ls.length];
        for (int i = 0; i < ls.length; i++) ls[i] = Ls[i];
        return ls;
    }
}
