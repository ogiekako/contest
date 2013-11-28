package utpc;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class 磁力 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int a = in.nextInt(), b = in.nextInt();
        int k = in.nextInt();
        double res = solve(a, b, k);
        out.printf("%.12f\n", res);
    }

    private double solve(int a, int b, int k) {
        double[][] dp = new double[2][k + 1];
        int sum = a + b;
        dp[0][0] = 1;// a+j
        int cur = 0, nxt = 1;
        for (int i = 0; i < k; i++) {
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < k + 1; j++)
                if (dp[cur][j] > 1e-15) {
                    int curA = a + j;
                    int curB = sum - curA;
                    double fA = f(curA);
                    double fB = f(curB);
                    dp[nxt][j + 1] += dp[cur][j] * fA / (fA + fB);
                    dp[nxt][j] += dp[cur][j] * fB / (fA + fB);
                }
            sum++;
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        double res = 0;
        for (int j = 0; j < k + 1; j++) {
            int curA = a + j;
            int curB = sum - curA;
            if (curA > curB) {
                res += dp[nxt][j];
            }
        }
        return res;
    }

    private double f(long a) {
        return Math.pow(a, a);
    }

    public static void main(String[] args) {
        int N = 1000;
        for (int i = 1; i < N; i++)
            for (int j = 1; j < i; j++) {
                double res = new 磁力().solve(j, i, 100);
                if (res >= 1e-9) {
                    System.out.printf("%d %d -> %.12f\n", i, j, res);
                    break;
                }
            }
    }
}
