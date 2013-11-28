package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler164 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long[][][] dp = new long[n + 1][10][10];
        dp[0][0][0] = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < 10; j++)
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        if (i == 0 && l == 0) continue;
                        if (i >= 2 && j + k + l > 9) continue;
                        dp[i + 1][k][l] += dp[i][j][k];
                    }
                }
        long res = 0;
        for (int j = 0; j < 10; j++) for (int k = 0; k < 10; k++) res += dp[n][j][k];
        out.println(res);
    }
}
