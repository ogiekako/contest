package tmp;

public class TheExperiment {
    int MOD = (int) (1e9 + 9);

    public int countPlacements(String[] intensity, int M, int L, int A, int B) {
        int N = 0;
        for (String s : intensity) N += s.length();
        int[] is = new int[N];
        N = 0;
        for (String s : intensity) {
            for (char c : s.toCharArray()) is[N++] = c - '0';
        }
        int[] S = new int[N + 1];
        for (int i = 0; i < N; i++) S[i + 1] = S[i] + is[i];
        int[][][] dp = new int[N + 1][M + 1][3];// 0 -> empty, 1 -> hasL, 2 -> not has L
        dp[0][0][0] = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j <= M; j++)
                for (int k = 0; k < 3; k++) {
                    if (k != 2) {
                        dp[i + 1][j][0] += dp[i][j][k];
                        if (dp[i + 1][j][0] >= MOD) dp[i + 1][j][0] -= MOD;
                    }
                    for (int len = 1; len <= L; len++) {
                        int ni = i + len;
                        if (ni > N) continue;
                        int v = S[i + len] - S[i];
                        if (v < A || B < v) continue;
                        int nj = j + 1;
                        int nk = len == L ? 1 : k == 0 ? 2 : k;
                        if (nj <= M) {
                            dp[ni][nj][nk] += dp[i][j][k];
                            if (dp[ni][nj][nk] >= MOD) dp[ni][nj][nk] -= MOD;
                        }
                    }
                }
        int res = dp[N][M][0] + dp[N][M][1];
        if (res >= MOD) res -= MOD;
        return res;
    }
}
