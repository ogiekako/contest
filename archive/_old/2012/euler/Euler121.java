package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler121 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        double[][] dp = new double[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= i; j++) {
                double p = 1.0 / (i + 2);
                dp[i + 1][j + 1] += dp[i][j] * p;
                dp[i + 1][j] += dp[i][j] * (1 - p);
            }
        double res = 0;
        for (int j = n / 2 + 1; j <= n; j++) {
            res += dp[n][j];
        }
        res = 1 / res;
        out.println((long) res);
    }
}
