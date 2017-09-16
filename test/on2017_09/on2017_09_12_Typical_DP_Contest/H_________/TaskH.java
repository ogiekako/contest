package on2017_09.on2017_09_12_Typical_DP_Contest.H_________;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskH {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), W = in.nextInt(), C = in.nextInt();
        int[] w = new int[N], v = new int[N], c = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = in.nextInt();
            v[i] = in.nextInt();
            c[i] = in.nextInt();
        }
        int[][][] dp = new int[2][C + 1][W + 1];
        int cur = 0, nxt = 1;
        for (int i = 1; i <= 50; i++) {
            ArrayUtils.fill(dp[nxt], 0);
            for (int j = 0; j < N; j++) {
                if (c[j] != i) continue;
                for (int k = C; k >= 1; k--) {
                    for (int l = W; l >= w[j]; l--) {
                        dp[nxt][k][l] = Math.max(dp[nxt][k][l], Math.max(dp[nxt][k][l - w[j]] + v[j], dp[cur][k - 1][l - w[j]] + v[j]));
                    }
                }
            }
            for (int k = 0; k <= C; k++) {
                for (int l = 0; l <= W; l++) {
                    dp[nxt][k][l] = Math.max(dp[nxt][k][l], dp[cur][k][l]);
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        int res = 0;
        for (int i = 0; i < C + 1; i++) {
            for (int j = 0; j < W + 1; j++) {
                res = Math.max(res, dp[cur][i][j]);
            }
        }
        out.println(res);
    }

}
