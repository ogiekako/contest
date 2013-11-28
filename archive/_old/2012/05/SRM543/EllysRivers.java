package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class EllysRivers {
    double INF = Double.POSITIVE_INFINITY;
    public double getMin(int length, int walk, int[] width, int[] speed) {
        double[][] dp = new double[2][length + 2];
        for (int i = 0; i < 2; i++) Arrays.fill(dp[i], INF);
        dp[0][0] = 0;
        int cur = 0, nxt = 1;
        for (int i = 0; i < width.length; i++) {
            Arrays.fill(dp[nxt], INF);
            for (int j1 = 0, j2 = 0; j1 <= length; ) {
                double a = dp[cur][j2] + MathUtils.hypot(width[i], j2 - j1) / speed[i];
                double b = dp[cur][j2 + 1] + MathUtils.hypot(width[i], j2 + 1 - j1) / speed[i];
                if (a <= b) {
                    dp[nxt][j1] = a;
                    j1++;
                } else {
                    j2++;
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        double res = INF;
        for (int i = 0; i <= length; i++) {
            res = Math.min(res, dp[cur][i] + (double) (length - i) / walk);
        }
        return res;
    }
}

