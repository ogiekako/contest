package tmp;

// Paste me into the FileEdit configuration dialog

public class FavouriteDigits {
    public long findNext(long N, int digit1, int count1, int digit2, int count2) {
        long[] ten = new long[20];
        for (int i = 0; i < 20; i++) ten[i] = i == 0 ? 1 : ten[i - 1] * 10;
        long[] ns = new long[20];
        for (int i = 0; i < 20; i++) ns[i] = N % ten[i];
        long[][][][][] dp = new long[20][20][20][2][2];
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++)
                for (int k = 0; k < 20; k++)
                    for (int l = 0; l < 2; l++) for (int m = 0; m < 2; m++) dp[i][j][k][l][m] = Long.MAX_VALUE;
        dp[0][0][0][0][0] = 0;
        for (int i = 0; ; i++) {
            long res = Long.MAX_VALUE;
            for (int j = 0; j < 20; j++)
                for (int k = 0; k < 20; k++)
                    for (int l = 0; l < 2; l++)
                        for (int m = 0; m < 2; m++)
                            if (dp[i][j][k][l][m] < Long.MAX_VALUE) {
                                if (m == 0 && l == 1 && j >= count1 && k >= count2 && dp[i][j][k][l][m] >= N)
                                    res = Math.min(res, dp[i][j][k][l][m]);
                                for (int d = 0; d < 10; d++) {
                                    long nxt = dp[i][j][k][l][m] + d * ten[i];
                                    int nj = d == digit1 ? j + 1 : j;
                                    int nk = d == digit2 ? k + 1 : k;
                                    int nl = d == 0 ? 0 : 1;
                                    int nm = nxt >= ns[i + 1] ? 0 : 1;
                                    dp[i + 1][nj][nk][nl][nm] = Math.min(dp[i + 1][nj][nk][nl][nm], nxt);
                                }
                            }
            if (res < Long.MAX_VALUE) return res;
        }
    }
}
