package tmp;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Euler172 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        long[][] dp = new long[2][1 << 20];
        for (int i = 1; i <= 9; i++) dp[0][1 << i * 2]++;
        int cur = 0, nxt = 1;
        for (int i = 1; i < N; i++) {
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < 1 << 20; j++) {
                for (int k = 0; k <= 9; k++) {
                    int tmp = (j >> 2 * k) & 3;
                    if (tmp < 3) {
                        int nj = j + (1 << 2 * k);
                        dp[nxt][nj] += dp[cur][j];
                    }
                }
            }

            int tmp = cur; cur = nxt; nxt = tmp;
        }
        long res = 0;
        for (long l : dp[cur]) res += l;
        out.println(res);
    }
}
