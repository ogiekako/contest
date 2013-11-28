package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskC {
    int MOD = 1000000007;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = 110;
        int S = 2510;
        long[][] dp = new long[N + 1][S];
        dp[0][0] = 1;
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < S; ++j) {
                for (int k = 0; k < 26 && j + k < S; ++k) {
                    dp[i + 1][j + k] += dp[i][j];
                    if (dp[i + 1][j + k] >= MOD) dp[i + 1][j + k] -= MOD;
                }
            }
        int t = in.nextInt();
        while (t-- > 0) {
            int sum = 0;// 0 - 2500
            String s = in.next();
            int n = s.length();
            for (char c : s.toCharArray()) {
                sum += c - 'a';
            }
            long res = dp[n][sum] - 1;
            if (res >= MOD) res -= MOD;
            out.println(res);
        }
    }
}
