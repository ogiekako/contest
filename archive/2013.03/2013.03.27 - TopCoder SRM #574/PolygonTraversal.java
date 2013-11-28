package tmp;

import java.util.Arrays;

public class PolygonTraversal {
    public long count(int N, int[] points) {
        long[][] dp = new long[1 << N][N];
        int init = 0;
        for (int i = 0; i < points.length; i++) points[i]--;
        for (int p : points) init |= 1 << p;
        int M = points.length;
        dp[init][points[M - 1]] = 1;
        for (int i = M; i <= N; i++)
            for (int mask = 0; mask < 1 << N; mask++)
                if (Integer.bitCount(mask) == i) {
                    for (int j = 0; j < N; j++)
                        if (mask << 31 - j < 0) {
                            for (int k = 0; k < N; k++) if (mask << 31 - k >= 0) dp[mask | 1 << k][k] += dp[mask][j];
                            int j2 = j + 1;
                            if (j2 >= N) j2 -= N;
                            while (mask << 31 - j2 >= 0) {
                                dp[mask | 1 << j2][j2] -= dp[mask][j];
                                j2++;
                                if (j2 >= N) j2 = 0;
                            }
                            j2 = j - 1;
                            if (j2 < 0) j2 += N;
                            while (mask << 31 - j2 >= 0) {
                                dp[mask | 1 << j2][j2] -= dp[mask][j];
                                j2--;
                                if (j2 < 0) j2 += N;
                            }
                        }
                }
        long res = 0;
        for (int i = 0; i < N; i++) {
            res += dp[(1 << N) - 1][i];
        }
        res -= dp[(1 << N) - 1][(points[0] - 1 + N) % N];
        res -= dp[(1 << N) - 1][(points[0] + N) % N];
        res -= dp[(1 << N) - 1][(points[0] + 1 + N) % N];

        return res;
    }
    class E {
        int[] nei;
        int[] other;

        E(int[] nei, int[] other) {
            this.nei = nei.clone();
            this.other = other.clone();
            Arrays.sort(nei);
            Arrays.sort(other);
        }
    }
}
