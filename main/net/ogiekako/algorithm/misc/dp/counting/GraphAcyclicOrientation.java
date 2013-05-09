package net.ogiekako.algorithm.misc.dp.counting;

import net.ogiekako.algorithm.iteration.Iteration;

public class GraphAcyclicOrientation {
    public static long solve(boolean[][] graph) {
        final int n = graph.length;
        final int[] nei = new int[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (graph[i][j]) nei[i] |= 1 << j;
        final int[] three = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            three[i] = i == 0 ? 1 : three[i - 1] * 3;
        }
        final long[] dp = new long[three[n]];
        dp[0] = 1;
        for (int _S = 0; _S < 1 << n; _S++) {
            final int S = _S;
            Iteration.iterateSubset((1 << n) - 1 ^ S, new Iteration.IntFunc() {
                public void doIt(int T) {
                    for (int i = 0; i < n; i++)
                        if ((S >> i & 1) == 0 && (T >> i & 1) == 0) {
                            int nS = S | 1 << i;
                            int nT = ((T | (1 << i) - 1) & ~nei[i]) & ~nS;
                            dp[encode(nS, nT, three)] += dp[encode(S, T, three)];
                        }
                }
            });
        }
        return dp[encode((1 << n) - 1, 0, three)];
    }

    private static int encode(int S, int T, int[] three) {
        int res = 0;
        for (int i = 0; i < three.length - 1; i++) {
            res *= 3;
            if ((S >> i & 1) == 1) res += 1;
            else if ((T >> i & 1) == 1) res += 2;
        }
        return res;
    }
}
