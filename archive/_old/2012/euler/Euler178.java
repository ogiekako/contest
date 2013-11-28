package tmp;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler178 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        long[][][] dp = new long[2][1 << 10][10];
        long res = 0;
        for (int i = 1; i <= 9; i++) {
            dp[0][1 << i][i] = 1;
        }
        int cur = 0, nxt = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < 10; j++) res += dp[cur][(1 << 10) - 1][j];
            ArrayUtils.fill(dp[nxt], 0);
            for (int j = 0; j < 1 << 10; j++)
                for (int k = 0; k < 10; k++)
                    if (dp[cur][j][k] > 0) {
                        int nk = k - 1;
                        if (nk >= 0) dp[nxt][j | 1 << nk][nk] += dp[cur][j][k];
                        nk = k + 1;
                        if (nk < 10) dp[nxt][j | 1 << nk][nk] += dp[cur][j][k];
                    }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        out.println(res);
    }
}
