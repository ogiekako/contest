package on2017_09.on2017_09_12_Typical_DP_Contest.I______;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskI {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][i] = true;
        }
        for (int len = 3; len <= n; len += 3) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len;
                for (int k = i; k < j; k++) {
                    if (dp[i][k] && dp[k][j]) dp[i][j] = true;
                    if (s.charAt(i) == 'i' && s.charAt(j - 1) == 'i' && s.charAt(k) == 'w' && dp[i + 1][k] && dp[k + 1][j - 1])
                        dp[i][j] = true;
                }
            }
        }
        int[] dp2 = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            if (i > 0) dp2[i] = dp2[i - 1];
            for (int j = 0; j < i; j++) {
                if (dp[j][i]) {
                    dp2[i] = Math.max(dp2[i], dp2[j] + (i - j) / 3);
                }
            }
        }
        out.println(dp2[n]);
    }
}
