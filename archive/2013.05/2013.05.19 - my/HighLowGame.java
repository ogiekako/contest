package src;

import net.ogiekako.algorithm.EPS;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.rationalNumber.RationalNumber;

import java.util.Arrays;

public class HighLowGame {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        out.printFormat("%.9f\n", solve(n));
    }

    double solve(long n) {
        return (3.0 * n + (harmonyOdd(n) + harmonyOdd(n + 1))) / 4.0 - 1;
    }

    private double harmonyOdd(long n) {
        return harmony(n) - harmony((n + 1) / 2L) / 2.0;
    }

    private double harmony(long n) {
        int T = 10;
        double res = 0;
        for (int k = 1; k < Math.min(T, n); k++) res += 1.0 / k;
        if (n >= T) {
            double log = Math.log(n) - Math.log(T);
            return res + log;
        }
        return res;
    }

    int n;
    double[][] dp;
    double solveStupid(int _n) {
        this.n = _n;
        dp = new double[1 << n][n];
        for (int i = 0; i < 1 << n; i++) Arrays.fill(dp[i], -1);
        double res = 0;
        for (int i = 0; i < n; i++) res += recur(1 << i, i);
        res /= n;
        return res;
    }

    private double recur(int vis, int last) {
        if (dp[vis][last] >= 0) return dp[vis][last];
        double nextPoint = 0;
        int high = 0, low = 0;
        for (int next = 0; next < n; next++)
            if (vis << 31 - next >= 0) {
                if (last < next) high++;
                else low++;
                nextPoint += recur(vis | 1 << next, next);
            }
        int m = high + low;
        if (m == 0) return dp[vis][last] = 0;
        nextPoint /= m;
        double curPoint = (double) Math.max(high, low) / m;
        return dp[vis][last] = curPoint + nextPoint;
    }

    public static void main(String[] args) {
        for (int n = 2; n <= 10; n++) {
            double exp = new HighLowGame().solveStupid(n);
            double res = new HighLowGame().solve(n);
            if (!EPS.eq(exp, res)) throw new AssertionError();
            RationalNumber r = RationalNumber.of(res);

            System.out.printf("%02d -> %.9f = %s\n", n, res, r.toString());
        }
        test((int) 1e3, 751.044529573);
        test((int) 1e4, 7501.620175805);
        test((int) 1e5, 75002.195822078);
        test((int) 1e6, 750002.771468351);
        test((int) 1e7, 7500003.347114624);
        test((int) 1e8, 75000003.922760900);
        test((int) 1e9, 750000004.498407100);
        test((long) 1e10, 7500000005.074052000);
        test((long) 1e11, 75000000005.649700000);
        test((long) 1e18, 7.5E17);
    }
    private static void test(long n, double exp) {
        double res = new HighLowGame().solve(n);
        EPS.set(1e-9);
        if (!EPS.eq(exp, res)) throw new AssertionError(String.format("%d -> exp %.9f but %.9f", n, exp, res));
    }
}
