package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class TheFansAndMeetingsDivOne {
    public double find(int[] minJ, int[] maxJ, int[] minB, int[] maxB, int k) {
        double[] J = calc(minJ, maxJ, k);
        double[] B = calc(minB, maxB, k);
        double res = 0;
        for (int i = 0; i < J.length; i++) res += J[i] * B[i];
        return res;
    }

    private double[] calc(int[] minJ, int[] maxJ, int K) {
        int n = minJ.length;
        double[][][] dp = new double[2][K + 1][1610];
        dp[0][0][0] = 1;
        long[][] C = new long[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++)
            for (int j = 0; j < i + 1; j++) C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
        int cur = 0, nxt = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < K + 1; j++) Arrays.fill(dp[nxt][j], 0);
            for (int j = 0; j < K + 1; j++)
                for (int k = 0; k < 1610; k++)
                    if (dp[cur][j][k] > 0) {
                        double p = K == j ? 0 : (double) C[n - i - 1][K - j - 1] / C[n - i][K - j];
                        dp[nxt][j][k] += dp[cur][j][k] * (1 - p);
                        for (int l = minJ[i]; l <= maxJ[i]; l++)
                            if (p > 0) {
                                dp[nxt][j + 1][k + l] += dp[cur][j][k] * p / (maxJ[i] - minJ[i] + 1);
                            }
                    }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        return dp[cur][K];
    }
}

