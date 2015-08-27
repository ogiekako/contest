package on2015_08.on2015_08_26_TopCoder_SRM__662.ExactTree;



import java.util.Arrays;

public class ExactTree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int INF = Integer.MAX_VALUE / 2;
    public int getTree(int n, int m, int r) {
        int[][] dp = new int[n + 1][m];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[1][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 1; k + i <= n; k++) {
                    for (int l = 0; l < m; l++) {
                        if (dp[i][j] >= INF)continue;
                        if (dp[k][l] >= INF)continue;
                        int T = dp[i][j] + dp[k][l] + i * (n - i);
                        dp[i + k][T % m] = Math.min(dp[i + k][T % m], T);
                    }
                }
            }
        }
        return dp[n][r] == INF ? -1 : dp[n][r];
    }
}
