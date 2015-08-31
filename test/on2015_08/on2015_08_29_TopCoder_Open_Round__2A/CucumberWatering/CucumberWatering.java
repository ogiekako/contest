package on2015_08.on2015_08_29_TopCoder_Open_Round__2A.CucumberWatering;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class CucumberWatering {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long theMin(int[] _x, int K) {
        int[] from = new int[_x.length - 1], to = new int[_x.length - 1];
        for (int i = 0; i < _x.length - 1; i++) {
            from[i] = _x[i];
            to[i] = _x[i + 1];
            if (from[i] > to[i]) {
                int tmp = from[i];
                from[i] = to[i];
                to[i] = tmp;
            }
        }
        long INF = 1L << 50;
        long[] x = new long[_x.length + 2];
        for (int i = 0; i < _x.length; i++) {
            x[i] = _x[i];
        }
        x[_x.length] = INF;
        x[_x.length + 1] = -INF;
        Arrays.sort(x);
        int n = x.length;
        long[][] dp = new long[n][K + 2];
        ArrayUtils.fill(dp, INF);
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < n; i++) {
            for (int num = 0; num <= K; num++)
                for (int j = i + 1; j < n; j++) {
                    long val = dp[i][num];
                    for (int k = 0; k < from.length; k++) {
                        if (x[i] <= from[k] && to[k] <= x[j]) {
                            val += Math.min(to[k] - from[k], from[k] - x[i] + x[j] - to[k]);
                        } else if (x[i] <= from[k] && from[k] <= x[j]) {
                            val += Math.min(from[k] - x[i], x[j] - from[k]);
                        } else if (x[i] <= to[k] && to[k] <= x[j]) {
                            val += Math.min(to[k] - x[i], x[j] - to[k]);
                        }
                    }
                    dp[j][num + 1] = Math.min(dp[j][num + 1], val);
                }
        }
        return dp[n - 1][K + 1];
    }
}
