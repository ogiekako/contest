package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class Facebook13QualB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        String s = in.nextLine();
        out.println(solve(s) ? "YES" : "NO");
    }

    private boolean solve(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++)
                if (dp[i][j]) {
                    char c = s.charAt(i);
                    if (c == '(') dp[i + 1][j + 1] = true;
                    else if (c == ')') {
                        if (j - 1 >= 0) dp[i + 1][j - 1] = true;
                    } else dp[i + 1][j] = true;
                    if (i < n - 1) {
                        char d = s.charAt(i + 1);
                        if (c == ':' && d == ')') dp[i + 2][j] = true;
                        if (c == ':' && d == '(') dp[i + 2][j] = true;
                    }
                }
        }
        return dp[n][0];
    }
}
