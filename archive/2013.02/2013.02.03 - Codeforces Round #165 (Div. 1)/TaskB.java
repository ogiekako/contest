package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt() - 1; in.nextDouble();
        }
        int[][] dp = new int[n + 1][m];
        ArrayUtils.fill(dp, Integer.MAX_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (dp[i][j] < Integer.MAX_VALUE) {
                    if (a[i] >= j) dp[i + 1][a[i]] = Math.min(dp[i + 1][a[i]], dp[i][j]);
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) res = Math.min(res, dp[n][i]);
        out.println(res);
    }
}
