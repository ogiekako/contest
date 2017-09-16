package on2017_09.on2017_09_16_Typical_DP_Contest.L____;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskL {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        int[][] f = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                f[i][j] = in.nextInt();
            }
        }
        int[][] S = new int[N][N + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                S[i][j + 1] = S[i][j] + f[i][j];
            }
        }
        int[][] dp = new int[N][N];
        ArrayUtils.fill(dp, Integer.MIN_VALUE / 2);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                int val = S[i][i] - S[i][j];
                if (i > 0) {
                    val += dp[i - 1][Math.min(i - 1, j)];
                }
                dp[i][j] = Math.max(dp[i][j], val);
                if (j > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                }
            }
        }
        out.println(dp[N - 1][N - 1] * 2);
    }
}
