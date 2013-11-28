package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler115 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int m = in.nextInt();
        int th = in.nextInt();
        int[] dp = new int[1000000];
        dp[0] = 1;
        for (int i = 1; ; i++) {
            dp[i] += dp[i - 1];
            for (int j = m + 1; j <= i; j++) {
                dp[i] += dp[i - j];
            }
            if (i >= m) dp[i]++;

            if (dp[i] > th) {
                out.println(i);
                return;
            }
        }
    }
}
