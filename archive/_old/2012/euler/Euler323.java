package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class Euler323 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int ITER = 1000;
        double[][] dp = new double[2][33];
        dp[0][0] = 1;
        double res = 0;
        int cur = 0, nxt = 1;
        long[][] C = MathUtils.genCombTable(32);
        for (int i = 0; i < ITER; i++) {
            res += i * dp[cur][32];
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < 32; j++) {
                int rest = 32 - j;
                for (int k = 0; k <= rest; k++) {
                    double prob = (double) C[rest][k] / (1L << rest);
                    dp[nxt][j + k] += prob * dp[cur][j];
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        out.printf("%.10f\n", res);
    }
}
