package on2017_08.on2017_08_24_Single_Round_Match_720.SumProduct;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class SumProduct {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    public int findSum(int[] amount, int blank1, int blank2) {
        long[][] val = new long[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                amount[i]--;
                amount[j]--;
                val[i][j] = f(amount, blank1 + blank2 - 2);
                amount[i]++;
                amount[j]++;
            }
        }
        long[] ten = new long[101];
        for (int i = 0; i < ten.length; i++) {
            ten[i] = i == 0 ? 1 : (ten[i - 1] * 10) % MOD;
        }
        long res = 0;
        for (int i = 0; i < blank1; i++) {
            for (int j = 0; j < blank2; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        res = (res + val[k][l] * ten[i] % MOD * ten[j] % MOD * k * l) % MOD;
                    }
                }
            }
        }
        return (int) res;
    }

    private long f(int[] amount, int n) {
        for (int i = 0; i < 10; i++) {
            if (amount[i] < 0) return 0;
        }
        long[][] C = MathUtils.genCombTableMod(n + 1, n + 1, MOD);
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 0; i < 10; i++) {
            long[] nDp = new long[n + 1];
            for (int j = 0; j < n + 1; j++) {
                for (int k = 0; j + k < n + 1 && k <= amount[i]; k++) {
                    nDp[j + k] = (nDp[j + k] + dp[j] * C[n - j][k]) % MOD;
                }
            }
            dp = nDp;
        }
        return dp[n];
    }
}
