package on2013_08.on2013_08_27_TopCoder_SRM__588.KeyDungeonDiv1;


public class KeyDungeonDiv1 {
    class E {
        int r, g, w;
        public E(int r, int g, int w) {
            this.r = r; this.g = g; this.w = w;
        }
    }
    public int maxKeys(int[] doorR, int[] doorG, int[] roomR, int[] roomG, int[] roomW, int[] keys) {
        int n = doorR.length;
        E[][] dp = new E[1 << n][150];
        dp[0][keys[0]] = new E(keys[0], keys[1], keys[2]);
        for (int i = 0; i < 1 << n; i++)
            for (int r = 0; r < 150; r++)
                if (dp[i][r] != null) {
                    E e = dp[i][r];
                    int sum = e.r + e.g + e.w;
                    for (int j = 0; j < n; j++)
                        if (i << 31 - j >= 0) {
                            if (doorR[j] + doorG[j] > sum) continue;
                            if (doorR[j] > e.r + e.w) continue;
                            if (doorG[j] > e.g + e.w) continue;
                            E ne = new E(e.r, e.g, e.w);
                            if (ne.r >= doorR[j]) {
                                ne.r -= doorR[j];
                            } else {
                                ne.w -= doorR[j] - ne.r;
                                ne.r = 0;
                            }
                            if (ne.g >= doorG[j]) {
                                ne.g -= doorG[j];
                            } else {
                                ne.w -= doorG[j] - ne.g;
                                ne.g = 0;
                            }
                            ne.r += roomR[j];
                            ne.g += roomG[j];
                            ne.w += roomW[j];
                            int ni = i | 1 << j;
                            if (dp[ni][ne.r] == null || dp[ni][ne.r].w < ne.w) {
                                dp[ni][ne.r] = ne;
                            }
                        }
                }
        int res = 0;
        for (int i = 0; i < 1 << n; i++)
            for (int j = 0; j < 150; j++) {
                if (dp[i][j] != null) res = Math.max(res, dp[i][j].r + dp[i][j].g + dp[i][j].w);
            }
        return res;
    }
}
