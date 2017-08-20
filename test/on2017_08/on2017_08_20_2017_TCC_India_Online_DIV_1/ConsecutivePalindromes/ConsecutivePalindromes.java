package on2017_08.on2017_08_20_2017_TCC_India_Online_DIV_1.ConsecutivePalindromes;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class ConsecutivePalindromes {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    public int countStrings(String S) {
        int n = S.length();
        long[][] dp = new long[n + 1][3];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                if (dp[i][j] == 0) continue;
                dp[i + 1][0] += dp[i][j];
                if (dp[i + 1][0] >= MOD) dp[i + 1][0] -= MOD;
                if (j == 0) {
                    dp[i + 1][1] += dp[i][j];
                    if (dp[i + 1][1] >= MOD) dp[i + 1][1] -= MOD;
                } else if (j == 1) {
                    if (S.charAt(i - 1) != S.charAt(i)) {
                        dp[i + 1][2] += dp[i][j];
                        if (dp[i + 1][2] >= MOD) dp[i + 1][2] -= MOD;
                    }
                } else {
                    if (S.charAt(i - 2) != S.charAt(i) && S.charAt(i - 1) != S.charAt(i)) {
                        dp[i + 1][2] += dp[i][j];
                        if (dp[i + 1][2] >= MOD) dp[i + 1][2] -= MOD;
                    }
                }
            }
        }
        long res = 0;
        for (int i = 0; i < 3; i++) {
            res += dp[n][i];
        }
        long tot = MathUtils.powMod(2, n, MOD);
        return (int) (((tot - res) % MOD + MOD) % MOD);
    }
}
