package on2017_09.on2017_09_12_Typical_DP_Contest.J______;



import net.egork.chelper.checkers.TokenChecker;
import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskJ {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        double[] dp = new double[1 << 16];
        Arrays.fill(dp, Double.POSITIVE_INFINITY);
        dp[0] = 0;
        for (int i = 1; i < 1 << 16; i++) {
            for (int j = 1; j < 16; j++) {
                double y = 0;
                int sub = 0;
                for (int k = j - 1; k <= j + 1; k++) {
                    if (i << 31 - k < 0) {
                        y += dp[i ^ 1 << k];
                    }
                    sub |= 1 << k;
                }
                // x = y / 3 + 1        => 3x = y + 3
                // x = (x + y) / 3 + 1  => 2x = y + 3
                // x = (2x + y) / 3 + 1 =>  x = y + 3
                dp[i] = Math.min(dp[i], (y + 3) / Integer.bitCount(sub & i));
            }
        }
        int N = in.nextInt();
        int mask = 0;
        for (int i = 0; i < N; i++) {
            mask |= 1 << in.nextInt();
        }
        out.println(dp[mask]);
    }
}
