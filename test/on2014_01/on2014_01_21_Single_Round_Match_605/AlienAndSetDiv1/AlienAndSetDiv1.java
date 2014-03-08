package on2014_01.on2014_01_21_Single_Round_Match_605.AlienAndSetDiv1;



public class AlienAndSetDiv1 {
    int MOD = (int) (1e9 + 7);
    public int getNumber(int N, int K) {
        int[][] dp = new int[N + 2][1 << K - 1];
        dp[0][0] = 1;
        int filter = (1 << K - 1) - 1;
        for (int i = 0; i < N * 2; i++) {
            int[][] nDp = new int[N + 2][1 << K - 1];
            for (int j = 0; j < N + 1; j++)
                for (int k = 0; k < 1 << K - 1; k++)
                    if (dp[j][k] > 0) {
                        nDp[j + 1][(k << 1 | 1) & filter] += dp[j][k];
                        if (nDp[j + 1][(k << 1 | 1) & filter] >= MOD) nDp[j + 1][(k << 1 | 1) & filter] -= MOD;
                        if (j == 0 || j - 1 >= Integer.bitCount(k)) {
                            nDp[Math.abs(j - 1)][j == 0 ? 1 & filter : (k << 1) & filter] += dp[j][k];
                            if (nDp[Math.abs(j - 1)][j == 0 ? 1 & filter : (k << 1) & filter] >= MOD)
                                nDp[Math.abs(j - 1)][j == 0 ? 1 & filter : (k << 1) & filter] -= MOD;
                        }
                    }
            dp = nDp;
        }
        return dp[0][0];
    }
}
