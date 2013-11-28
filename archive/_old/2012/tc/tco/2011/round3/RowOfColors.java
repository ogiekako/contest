package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.ArrayUtils;

public class RowOfColors {
    int MOD = 1000000007;
    public int countWays(int W, int H, int K) {
        int[][][] dp = new int[2][K + 2][H + 2];
        dp[0][0][0] = 1;
        int cur = 0, nxt = 1;
        for (int i = 0; i < W; i++) {
            ArrayUtils.fill(dp[nxt], 0);
            for (int j = 0; j < K + 1; j++)
                for (int k = 0; k < H + 1; k++) {
                    int add = dp[cur][j][k];
                    if (k > 0) dp[nxt][j + 1][k - 1] = add(dp[nxt][j + 1][k - 1], add);
                    if (k > 0) dp[nxt][j][k] = add(dp[nxt][j][k], add);
                    dp[nxt][j][k + 1] = add(dp[nxt][j][k + 1], add);
                    if (k < H) dp[nxt][j + 1][k] = add(dp[nxt][j + 1][k], add);
                }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        long res = dp[cur][K][0];
        for (int i = 0; i < K; i++) {
            res = res * (i + 1) % MOD;
        }
        return (int) res;
    }

    private int add(int a, int b) {
        int res = a + b;
        if (res >= MOD) res -= MOD;
        return res;
    }


}

