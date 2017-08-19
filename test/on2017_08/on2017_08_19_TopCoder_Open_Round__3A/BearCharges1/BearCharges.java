package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.BearCharges1;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class BearCharges {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double minTime(int[] x, int[] y) {
        int n = x.length;
        double[][] D = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                D[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
            }
        }
        double[][] dp = new double[1 << n][n];
        ArrayUtils.fill(dp, Double.POSITIVE_INFINITY);
        for (int mask = 0; mask < 1 << n; mask++) {
            for (int i = 0; i < n; i++) {
                if (mask << 31 - i < 0) {
                    if (Integer.bitCount(mask) == 1) {
                        dp[mask][i] = 0;
                        continue;
                    }
                    int sub = mask ^ (1 << i);
                    for (int j = 0; j < n; j++) {
                        if (sub << 31 - j < 0) {
                            dp[mask][i] = Math.min(dp[mask][i], dp[sub][j] + D[i][j]);
                        }
                    }
                    for (int X = sub; X > 0; X = (X - 1) & sub) {
                        for (int j = 0; j < n; j++) {
                            if (X << 31 - j < 0) {
                                dp[mask][i] = Math.min(dp[mask][i], D[i][j] + Math.max(dp[mask^X][i], dp[X][j]));
                            }
                        }
                    }
                }
            }
        }
        return dp[(1<<n) - 1][0];
    }
}
