package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskB {
    int MOD = 1000000007;

    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long m = in.nextLong();
        int K = in.nextInt();
        long[][] C = MathUtils.generateCombinationMod(n + 1, n + 1, MOD);
        long[][] dp = new long[2][K + 1];
        dp[0][0] = 1;
        int cur = 0, nxt = 1;
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[nxt], 0);
            long rep = (m - i + n - 1) / n;
            for (int j = 0; j <= n; j++) {
                long way = MathUtils.powMod(C[n][j], rep, MOD);
                for (int k = 0; j + k <= K; k++) {
                    dp[nxt][k + j] += dp[cur][k] * way % MOD;
                    if (dp[nxt][k + j] >= MOD) dp[nxt][k + j] -= MOD;
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        out.println(dp[cur][K]);
    }
}
