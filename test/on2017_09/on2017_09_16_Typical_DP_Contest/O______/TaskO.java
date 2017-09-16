package on2017_09.on2017_09_16_Typical_DP_Contest.O______;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;

import java.util.Arrays;

public class TaskO {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        Mint[][] dp = new Mint[2][10 * 26 + 1];
        int cur = 0, nxt = 1;
        Mint[][] C = MathUtils.combinationMint(dp[cur].length);
        Arrays.fill(dp[cur], Mint.ZERO);
        dp[cur][0] = Mint.ONE;
        int n = 0;
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dp[nxt], Mint.ZERO);
            int f = in.nextInt();
            for (int j = 0; j <= n; j++) {
                if (f == 0) {
                    dp[nxt][j] = dp[cur][j];
                    continue;
                }
                for (int k = 1; k <= f; k++) {
                    for (int l = 0; l <= k && l <= j; l++) {
                        Mint val = dp[cur][j];
                        val = val.mul(C[f - 1][f - k]);
                        val = val.mul(C[n + 1 - j][k - l]).mul(C[j][l]);
                        dp[nxt][j - l + f - k] = dp[nxt][j - l + f - k].add(val);
                    }
                }
            }
            n += f;
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        out.println(dp[cur][0]);
    }
}
