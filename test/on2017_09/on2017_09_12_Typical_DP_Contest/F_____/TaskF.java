package on2017_09.on2017_09_12_Typical_DP_Contest.F_____;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;

import java.util.Arrays;

public class TaskF {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        Mint.set1e9_7();
        Mint[] dp = new Mint[N + 2];
        Arrays.fill(dp, Mint.ZERO);
        dp[0] = Mint.ONE;
        // dp[0] = 1, dp[1] = 0, dp[N] = 0
        // dp[i] = dp[i-K] + ... + dp[i-1]
        Mint sum = Mint.ZERO;
        sum = sum.add(dp[0]);
        for (int i = 2; i < N + 2; i++) {
            if (i != N) {
                dp[i] = sum;
            }
            sum = sum.add(dp[i]).minus(i-K >= 0 ? dp[i-K] : Mint.ZERO);
        }
        out.println(dp[N+1]);
    }
}
