package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    int MOD = 1000000007;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        String t = in.next();
        long[][] dp = new long[2][t.length()];
        int cur = 0, nxt = 1;
        long res = 0;
        for (int i = 0; i < s.length(); i++) {
            long sum = 0;
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < t.length(); j++) {
                if (t.charAt(j) == s.charAt(i)) {
                    dp[nxt][j] = 1 + sum;
                    if (dp[nxt][j] >= MOD) dp[nxt][j] -= MOD;
                    res += dp[nxt][j];
                    if (res >= MOD) res -= MOD;
                }
                if (i > 0 && t.charAt(j) == s.charAt(i - 1)) {
                    sum += dp[cur][j];
                    if (sum >= MOD) sum -= MOD;
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        out.println(res);
    }
}
