package tmp;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskE {
    int INF = 1 << 28;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        int[] from = new int[n], len = new int[n];
        for (int i = 0; i < n; i++) {
            from[i] = in.nextInt();
            len[i] = in.nextInt();
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int[] i : dp) Arrays.fill(i, INF);
        dp[0][0] = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= k; j++)
                if (dp[i][j] < INF) {
                    // doIt
                    if (dp[i][j] <= from[i]) {
                        dp[i + 1][j] = Math.min(dp[i + 1][j], from[i] + len[i]);
                    } else {
                        dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + len[i]);
                    }
                    // ignore
                    if (j + 1 <= k) {
                        dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);
                    }
                }
        int res = 0;
        for (int i = 0; i <= n; i++) {
            int min = INF;
            for (int j = 0; j < k + 1; j++) min = Math.min(min, dp[i][j]);
            int val = (i == n ? 86401 : from[i]) - min;
            res = Math.max(res, val);
        }
        out.println(res);
    }
}
