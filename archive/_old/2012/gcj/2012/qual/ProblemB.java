package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class ProblemB {
    static int[] maxWhenSurprise;
    static int[] maxWhenNotSurprise;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        if (maxWhenNotSurprise == null) {
            maxWhenNotSurprise = new int[31];
            maxWhenSurprise = new int[31];
            Arrays.fill(maxWhenSurprise, -1);
            Arrays.fill(maxWhenNotSurprise, -1);
            for (int a = 0; a < 11; a++)
                for (int b = 0; b < 11; b++)
                    for (int c = 0; c < 11; c++) {
                        int m = Math.max(Math.max(Math.abs(a - b), Math.abs(b - c)), Math.abs(c - a));
                        int sum = a + b + c;
                        if (m <= 2) {
                            if (m == 2)
                                maxWhenSurprise[sum] = Math.max(Math.max(Math.max(a, b), c), maxWhenSurprise[sum]);
                            else
                                maxWhenNotSurprise[sum] = Math.max(Math.max(Math.max(a, b), c), maxWhenNotSurprise[sum]);
                        }
                    }
        }
        int N = in.nextInt();
        int S = in.nextInt();
        int p = in.nextInt();
        int[][] dp = new int[N + 1][S + 1];
        ArrayUtils.fill(dp, -1);
        dp[0][0] = 0;
        for (int i = 0; i < N; i++) {
            int score = in.nextInt();
            for (int j = 0; j < S + 1; j++)
                if (dp[i][j] >= 0) {
                    if (maxWhenNotSurprise[score] >= 0) {
                        dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + (maxWhenNotSurprise[score] >= p ? 1 : 0));
                    }
                    if (maxWhenSurprise[score] >= 0 && j + 1 < S + 1) {
                        dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + (maxWhenSurprise[score] >= p ? 1 : 0));
                    }
                }
        }
        out.println("Case #" + testNumber + ": " + dp[N][S]);
    }
}
