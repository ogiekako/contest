package on2013_05.on2013_05_03_Single_Round_Match_578.WolfInZooDivOne;


import java.util.Arrays;
public class WolfInZooDivOne {
    int MOD = (int) (1e9 + 7);
    public int count(int N, String[] _L, String[] _R) {
        int[] L = gen(_L), R = gen(_R);
        for (int i = 0; i < L.length; i++) {
            L[i]++; R[i]++;
        }
        int[] limit = new int[N + 1];
        Arrays.fill(limit, -1);
        for (int i = 0; i < L.length; i++) {
            for (int j = L[i]; j <= R[i]; j++) limit[j] = Math.max(limit[j], R[i]);
        }
        int[][] dp = new int[N + 1][N + 1];
        dp[0][0] = 1;
        for (int i = 0; i <= N; i++)
            for (int j = i; j <= N; j++) {
                for (int k = j + 1; k <= N; k++) {
                    if (limit[i] < k) {
                        dp[j][k] += dp[i][j];
                        if (dp[j][k] >= MOD) dp[j][k] -= MOD;
                    }
                }
            }
        int res = 0;
        for (int i = 0; i <= N; i++)
            for (int j = 0; j <= N; j++) {
                res += dp[i][j];
                if (res >= MOD) res -= MOD;
            }
        return res;
    }
    private int[] gen(String[] L) {
        StringBuilder b = new StringBuilder();
        for (String s : L) b.append(s);
        String[] ss = b.toString().split(" ");
        int[] is = new int[ss.length];
        for (int i = 0; i < is.length; i++) is[i] = Integer.parseInt(ss[i]);
        return is;
    }
}
