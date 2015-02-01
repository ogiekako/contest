package src;

public class AlienAndPermutation {
    int MOD = (int) (1e9 + 7);

    void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
    }

    public int getNumber(int[] P, int K) {
        int n = P.length;
        boolean[][] canUse = new boolean[n][n];// P[i] can be used at position j.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                canUse[i][j] = true;
                for (int k = 0; k < n; k++) {
                    if ((i <= k && k <= j || j <= k && k <= i) && P[k] > P[i]) {
                        canUse[i][j] = false;
                    }
                }
            }
        }
        long[][][] dp = new long[n + 1][K + 1][2]; // used P[j], l:= k is already inced by j.
        dp[n][0][0] = 1;// dummy
        for (int i = 0; i < n; i++) {
            long[][][] nDp = new long[n + 1][K + 1][2];
            for (int bef = 0; bef < n + 1; bef++)
                for (int k = 0; k <= K; k++)
                    for (int l = 0; l < 2; l++)
                        if (dp[bef][k][l] > 0) {
                            debug(i,bef,k,l,"->",dp[bef][k][l]);
                            if (bef < n) {
                                int nK = k;
                                int nL = l;
                                if (i != bef && l == 0) {
                                    nK++;
                                    nL = 1;
                                }
                                if (nK <= K && canUse[bef][i]) {
                                    nDp[bef][nK][nL] += dp[bef][k][l];
                                    if (nDp[bef][nK][nL] >= MOD) nDp[bef][nK][nL] -= MOD;
                                }
                            }
                            for (int nxt = (bef == n ? 0 : bef + 1); nxt < n; nxt++) {
                                int nK = k;
                                int nL = 0;
                                if (i != nxt) {
                                    nK++;
                                    nL++;
                                }
                                if (nK <= K && canUse[nxt][i]) {
                                    nDp[nxt][nK][nL] += dp[bef][k][l];
                                    if (nDp[nxt][nK][nL] >= MOD) nDp[nxt][nK][nL] -= MOD;
                                }

                            }
                        }
            dp = nDp;
        }
        debug("Res:");
        long res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= K; j++) for(int l=0;l<2;l++) if(dp[i][j][l] > 0) {
                debug("Last:", i,j,l,"->",dp[i][j][l]);
                res = (res + dp[i][j][l]) % MOD;
            }
        return (int) res;
    }
}
