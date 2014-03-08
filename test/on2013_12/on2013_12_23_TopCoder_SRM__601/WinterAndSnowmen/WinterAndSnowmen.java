package on2013_12.on2013_12_23_TopCoder_SRM__601.WinterAndSnowmen;



import java.util.Arrays;
public class WinterAndSnowmen {
    int MOD = (int) (1e9 + 7);
    public int getNumber(int N, int M) {
        int res = 0;
        for (int h = 0; h <= 10; h++) {
            int[] dp = new int[1 << 12 - h];
            int[] ndp = new int[dp.length];
            dp[0] = 1;
            for (int i = 1; i <= Math.max(N, M); i++) {
                Arrays.fill(ndp, 0);
                int ni = i >> h;
                for (int j = 0; j < dp.length; j++)
                    if (dp[j] > 0) {
                        ndp[j] += dp[j];
                        if (ndp[j] >= MOD) ndp[j] -= MOD;
                        if (i <= N) {
                            int x = (ni << 1) | (ni & 1);
                            ndp[j ^ x] += dp[j];
                            if (ndp[j ^ x] >= MOD) ndp[j ^ x] -= MOD;
                        }
                        if (i <= M) {
                            int x = ni << 1;
                            ndp[j ^ x] += dp[j];
                            if (ndp[j ^ x] >= MOD) ndp[j ^ x] -= MOD;
                        }
                    }
                System.arraycopy(ndp, 0, dp, 0, dp.length);
            }
            res += dp[2];
            if (res >= MOD) res -= MOD;
        }
        return res;
    }
}
