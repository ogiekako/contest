package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class FoxAndGreed {
    int[][] dp;
    int MOD = (int) (1e4 + 7);
    long[][] C = MathUtils.genCombTableMod(2620, 110, MOD);

    public int count(int H, int W, int S) {
        dp = new int[2510][S + 1];
        for (int[] is : dp) Arrays.fill(is, -1);
        if (H == 1 || W == 1) {
            return rec2(Math.max(H, W) - 1, S);
        }
        long res = solve(H, W, S, 0) + solve(W, H, S, 1);
        return (int) (res % MOD);
    }

    private int solve(int H, int W, int S, int d) {
        int[][] down = gen(S, d);
        int[][] right = gen(S, d ^ 1);
        long res = 0;
        for (int y = 0; y < W - 1; y++) {
            int x = H - 2;
            if (Math.min(x, y) >= 105) continue;
            int all = H * W - (1 + 2 * (x + y + 1)) - (W - 1 - y);
            long mul = MathUtils.powMod(S + 1, all, MOD);
            mul = mul * C[x + y][Math.min(x, y)] % MOD;
            long sum = 0;
            for (int s = 0; s <= S; s++) {
                long sumS = 0;
                for (int t = 0; t <= s; t++) {
                    sumS += down[x][t] * right[y][s - t];
                }
                sumS %= MOD;
                for (int t = 0; s + t <= S; t++) {
                    long way = sumS * (t + d);
                    way *= rec2(W - 1 - y, S - s - t);
                    sum += way;
                }
            }
            sum %= MOD;
            res += sum * mul;
        }

        return (int) (res % MOD);
    }

    private int[][] gen(int S, int d) {
        int[][] down = new int[2510][110];
        down[0][0] = 1;
        for (int i = 0; i < 2505; i++)
            for (int s = 0; s <= S; s++)
                if (down[i][s] > 0) {
                    for (int k = 0; s + k <= S; k++) {
                        down[i + 1][s + k] += down[i][s] * (k + d);
                        down[i + 1][s + k] %= MOD;
                    }
                } return down;
    }

    private int rec2(int n, int s) {
        if (n == 0) return s == 0 ? 1 : 0;
        if (s == 0) return 1;
        if (dp[n][s] >= 0) return dp[n][s];
        int res = rec2(n - 1, s) + rec2(n, s - 1);
        if (res >= MOD) res -= MOD;
        return dp[n][s] = res;
    }
}

