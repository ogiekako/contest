package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import org.junit.Assert;

import java.io.PrintWriter;
import java.util.Random;

/*
P(x) := a_0 x^0 + a_1 x^1 + ... + a_{n-1} x^{x-1}.
P(t) を mod 2^30 で求めよ.

n, Q <= 10^5
0 <= a_i, t_i < 2^30

coding: 7:00 - 7:23
*/
public class PolynomialEvaluation {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), Q = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        int[] t = new int[Q];
        for (int i = 0; i < Q; i++) t[i] = in.nextInt();
        int[] r = solve(a, t, 30);
        for (int i = 0; i < Q; i++) out.println(r[i]);
    }

    private int[] solve(int[] a, int[] t, int L) {
        int n = a.length;
        int MOD = 1 << L;
        int[] res = new int[t.length];
        long[][] C = MathUtils.generateCombinationMod(n, L, MOD);
        long[] coefficient = new long[L];
        for (int j = 0; j < L; j++) {
            for (int k = 0; k < n; k++) {
                coefficient[j] += C[k][j] * a[k];
            }
            coefficient[j] <<= j;
        }
        for (int i = 0; i < t.length; i++) {
            if (t[i] % 2 == 0) {
                long xk = 1;
                for (int k = 0; k < L && k < a.length; k++) {
                    res[i] = (int) (res[i] + xk * a[k]);
                    xk = xk * t[i];
                }
            } else {
                int m = t[i] / 2;
                long mk = 1;
                for (int k = 0; k < L && k < a.length; k++) {
                    res[i] = (int) (res[i] + coefficient[k] * mk);
                    mk = mk * m;
                }
            }
            res[i] = (res[i] % MOD + MOD) % MOD;
        }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(419284791L);
        for (int iteration = 0; iteration < 50; iteration++) {
            int n = 1000;
            int Q = 1000;
            int L = iteration == 0 ? 30 : rnd.nextInt(30) + 1;
            int MOD = 1 << L;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(MOD);
            int[] t = new int[Q];
            for (int i = 0; i < 10; i++) t[i] = i;
            for (int i = 10; i < Q; i++) t[i] = rnd.nextInt(MOD);
            int[] r = new PolynomialEvaluation().solve(a, t, L);
            int[] exp = solveStupid(a, t, L);
            Assert.assertArrayEquals(exp, r);
        }

        for (int iteration = 0; iteration < 50; iteration++) {
            int n = 100000;
            int Q = 100000;
            int L = 30;
            int MOD = 1 << L;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(MOD);
            int[] t = new int[Q];
            for (int i = 0; i < 10; i++) t[i] = i;
            for (int i = 10; i < Q; i++) t[i] = rnd.nextInt(MOD);
            long start = System.currentTimeMillis();
            new PolynomialEvaluation().solve(a, t, L);
            long time = System.currentTimeMillis() - start;
            Assert.assertTrue(time < 2000);
        }
    }

    private static int[] solveStupid(int[] a, int[] t, int L) {
        int[] res = new int[t.length];
        int MOD = 1 << L;
        for (int i = 0; i < t.length; i++) {
            long tj = 1;
            for (int anA : a) {
                res[i] = (int) ((res[i] + anA * tj) % MOD);
                tj = tj * t[i] % MOD;
            }
        }
        return res;
    }
}
