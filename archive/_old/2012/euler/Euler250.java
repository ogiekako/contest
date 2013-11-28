package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Euler250 {
    long MOD = 10000000000000000L;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        int mod = in.nextInt();
        long[][] dp = new long[2][mod];
        int cur = 0, nxt = 1;
        dp[0][0] = 1;
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[nxt], 0);

            int t = (int) MathUtils.powMod(i, i, mod);
            for (int j = 0; j < mod; j++) {
                dp[nxt][j] += dp[cur][j];
                if (dp[nxt][j] >= MOD) dp[nxt][j] -= MOD;
                dp[nxt][(j + t) % mod] += dp[cur][j];
                if (dp[nxt][(j + t) % mod] >= MOD) dp[nxt][(j + t) % mod] -= MOD;
            }

            int tmp = cur; cur = nxt; nxt = tmp;
        }
        out.println(dp[cur][0] - 1);
    }
}
