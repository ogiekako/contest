package on2015_09.on2015_09_01_TopCoder_SRM__615.AlternativePiles;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class AlternativePiles {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    char[] rgb = "RGB".toCharArray();

    public int count(String C, int M) {
        int n = C.length();
        int[][][] dp = new int[2][M][M + 1];
        dp[0][0][0] = 1;
        int cur = 0, nxt = 1;
        for (char c : C.toCharArray()) {
            ArrayUtils.fill(dp[nxt], 0);
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M + 1; j++) {
                    if (dp[cur][i][j] == 0) continue;
                    for (char d : rgb) {
                        if (c != 'W' && c != d) continue;
                        if (d == 'B') {
                            dp[nxt][i][j] += dp[cur][i][j];
                            if (dp[nxt][i][j] >= MOD) dp[nxt][i][j] -= MOD;
                        } else if (d == 'R') {
                            if (j + 1 > M) continue;
                            dp[nxt][i][j + 1] += dp[cur][i][j];
                            if (dp[nxt][i][j + 1] >= MOD) dp[nxt][i][j + 1] -= MOD;
                        } else if (d == 'G') {
                            if (j - 1 < 0) continue;
                            dp[nxt][(i + 1) % M][j - 1] += dp[cur][i][j];
                            if (dp[nxt][(i + 1) % M][j - 1] >= MOD) dp[nxt][(i + 1) % M][j - 1] -= MOD;
                        }
                    }
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        return dp[cur][0][0];
    }
}
