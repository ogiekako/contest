package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskJ {

    long[][][] dp;
    int MOD = (int) (1e9 + 7);
    String s;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            System.err.println(T);
            s = in.next();
            int n = s.length();
             dp = new long[n][n + 1][2];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n + 1; j++) {
                    Arrays.fill(dp[i][j], -1);
                }
            }
            long A = f(0, n, 1);
            long B = f(0, n, 0);
            out.printFormat("%d true, %d false\n", A, B);
        }
    }

    private long f(int from, int to, int b) {
        if (to - from == 1) {
            if (s.charAt(from) - '0' == b) return 1;
            else return 0;
        }
        if (dp[from][to][b] != -1) return dp[from][to][b];


        long res = 0;
        for (int i = from + 1; i < to; i += 2) {
            for (int a = 0; a < 2; a++)
                for (int c = 0; c < 2; c++) {
                    long A = f(from, i, a);
                    long B = f(i + 1, to, c);
                    if(func(a, s.charAt(i), c) == b){
                        res = (res + A * B) % MOD;
                    }
                }
        }
        return dp[from][to][b] = res;
    }
    private int func(int x, char c, int y) {
        if(c=='|')return x|y;
        if(c=='^')return x^y;
        if(c=='&')return x&y;
        throw new AssertionError();
    }
}
