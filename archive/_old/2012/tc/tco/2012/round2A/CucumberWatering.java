package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Cast;

import java.util.Arrays;

public class CucumberWatering {
    public long theMin(int[] _x, int K) {
        long[] x = Cast.toLong(_x);
        Entry[] es = new Entry[x.length - 1];
        for (int i = 0; i < x.length - 1; i++) es[i] = new Entry(x[i], x[i + 1]);
        x = ArrayUtils.pushFront((long) -1e11, x);
        x = ArrayUtils.pushBack(x, (long) 1e11);
        int n = x.length;
        K += 2;
        if (K >= n) K = n;
        Arrays.sort(x);

        long[][][] dp = new long[n][n][K + 2];
        long INF = 1L << 60;
        ArrayUtils.fill(dp, INF);
        dp[0][0][1] = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int last = 0; last < i + 1; last++) {
                for (int count = 1; count < K + 1; count++)
                    if (dp[i][last][count] < INF) {
                        long tmp = dp[i][last][count];
                        for (Entry e : es) {
                            if (x[last] <= e.x1 && e.x1 <= x[i + 1] && x[last] <= e.x2 && e.x2 <= x[i + 1]) {
                                tmp += Math.min(e.length(), e.x1 - x[last] + x[i + 1] - e.x2);
                            } else if (x[last] <= e.x1 && e.x1 <= x[i + 1]) {
                                tmp += Math.min(e.x1 - x[last], x[i + 1] - e.x1);
                            } else if (x[last] <= e.x2 && e.x2 <= x[i + 1]) {
                                tmp += Math.min(e.x2 - x[last], x[i + 1] - e.x2);
                            }
                        }
                        dp[i + 1][i + 1][count + 1] = Math.min(dp[i + 1][i + 1][count + 1], tmp);
                        dp[i + 1][last][count] = Math.min(dp[i + 1][last][count], dp[i][last][count]);
                    }
            }
        }
        return dp[n - 1][n - 1][K];
    }
    class Entry {
        long x1, x2;

        Entry(long x1, long x2) {
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
        }

        public long length() {
            return x2 - x1;
        }
    }
}

