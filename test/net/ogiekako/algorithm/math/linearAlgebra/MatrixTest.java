package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.MOD;
import net.ogiekako.algorithm.math.PowerOperation;
import net.ogiekako.algorithm.math.algebra.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Cast;
import net.ogiekako.algorithm.utils.Permutation;
import net.ogiekako.algorithm.utils.TestUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/04/04
 * Time: 7:05
 * To change this template use File | Settings | File Templates.
 */
public class MatrixTest {
    @Test
    public void testPow() throws Exception {
        for (int iteration = 0; iteration < 50; iteration++) {
            long[][] A = Cast.toLong(TestUtils.generateRandomIntArray(50, 50, 100));
            long[][] res = Matrix.powered(A, 100, 1000);
            long[][] exp = powStupid(A, 100, 1000);
            for (int i = 0; i < res.length; i++) Assert.assertArrayEquals(res[i], exp[i]);
        }
    }

    private long[][] powStupid(long[][] A, int exponent, int modulus) {
        long[][] res = Matrix.identity(A.length);
        for (int i = 0; i < exponent; i++) {
            res = Matrix.mul(res, A, modulus);
        }
        return res;
    }

    @Test
    public void testCharPoly() {
        Random rnd = new Random(104912908L);
        final int MOD = (int) (1e9 + 7);
        for (int iteration = 0; iteration < 50; iteration++) {
            int n = 20;
            final long[][] A = new long[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    A[i][j] = rnd.nextInt(5);
            long[] x = new long[n];
            for (int i = 0; i < n; i++) x[i] = rnd.nextInt(5);
            long[] r = Matrix.characteristicPolynomial(new LongLinearTransform() {
                public long[] map(long[] x) {
                    return Matrix.mul(A, x, MOD);
                }
            }, x, MOD);
            int[] sum = new int[n];
            for (long coefficient : r) {
                for (int j = 0; j < n; j++) sum[j] = (int) ((sum[j] + x[j] * coefficient) % MOD);
                x = Matrix.mul(A, x, MOD);
            }
            int[] zero = new int[n];
            if (!Arrays.equals(sum, zero)) {
                throw new AssertionError();
            }
        }
    }

    @Test
    public void testPowered() {
        Random rnd = new Random(12490124L);
        int iteration = 50;
        for (int iterationCount = 0; iterationCount < iteration; iterationCount++) {
//            System.err.println("iteration : " + iterationCount);
            int n = 20;
            int MOD = (int) (1e9 + 7);
            int k = (int) 1e9;
            long[][] A = new long[n][n];
            long[] x = new long[n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = rnd.nextInt(MOD);
            for (int i = 0; i < n; i++) x[i] = rnd.nextInt(MOD);
//            System.err.println("1");
            long[] res = Matrix.powered(A, k, x, MOD);
//            System.err.println("2");
            long[][] poweredA = Matrix.powered(A, k, MOD);
            long[] exp = Matrix.mul(poweredA, x, MOD);
            if (!Arrays.equals(res, exp)) {
                debug(A);
                debug(x);
                debug(res);
                debug(exp);
                throw new AssertionError();
            }
        }
    }

    @Test
    public void testSumPowered() {
        Random rnd = new Random(12490124L);
        int iteration = 50;
        for (int iterationCount = 0; iterationCount < iteration; iterationCount++) {
//            System.err.println("iteration : " + iterationCount);
            int n = 20;
            int MOD = (int) (1e9 + 7);
            int k = (int) 100;
            long[][] A = new long[n][n];
            long[] x = new long[n];
            int[][] Ai = new int[n][n];
            int[] xi = new int[n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = Ai[i][j] = rnd.nextInt(MOD);
            for (int i = 0; i < n; i++) x[i] = xi[i] = rnd.nextInt(MOD);
//            System.err.println("1");
            long[] res = Matrix.sumPowered(A, k, x, MOD);
            int[] resi = Matrix.sumPowered(Ai, k, xi, MOD);
//            System.err.println("2");
            long[] exp = new long[n];
            long[] Ax = x.clone();
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < n; j++) {
                    exp[j] += Ax[j];
                    if (exp[j] >= MOD) exp[j] -= MOD;
                }
                Ax = Matrix.mul(A, Ax, MOD);
            }
            int[] expi = new int[n];
            for (int i = 0; i < n; i++) expi[i] = (int) exp[i];
            if (!Arrays.equals(res, exp)) {
                debug("A", A);
                debug("x", x);
                debug("res", res);
                debug("exp", exp);
                throw new AssertionError();
            }
            if (!Arrays.equals(resi, expi)) {
                debug("Ai", Ai);
                debug("xi", xi);
                debug("resi", resi);
                debug("expi", expi);
                throw new AssertionError();
            }
        }
    }

    @Test
    public void testDeterminantLong() {
        int PRIME = 97;
        Random random = new Random(14901284L);
        for (int iteration = 0; iteration < 50; iteration++) {
            int n = 5;
            long[][] A = new long[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = random.nextInt(PRIME);
            long res = Matrix.determinant(ArrayUtils.deepClone(A), PRIME);
            long exp = determinantStupid(A, PRIME);
            if (res != exp) {
                debug(A);
                debug(res, exp);
                throw new AssertionError();
            }
        }
    }

    @Test
    public void testDeterminantMint() {
        int PRIME = 97;
        MOD.set(PRIME);
        Random random = new Random(14901284L);
        for (int iteration = 0; iteration < 50; iteration++) {
            int n = 5;
            Mint[][] A = new Mint[n][n];
            long[][] ALong = new long[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++){
                ALong[i][j] = random.nextInt(PRIME);
                A[i][j] = Mint.of(ALong[i][j]);
            }
            long res = Matrix.determinant(A);
            long exp = determinantStupid(ALong, PRIME);
            if (res != exp) {
                debug(A);
                debug(res, exp);
                throw new AssertionError();
            }
        }
    }

    private long determinantStupid(long[][] A, int MOD) {
        int n = A.length;
        int[] ps = ArrayUtils.createOrder(n);
        long res = 0;
        do {
            long cur = Permutation.sign(ps);
            for (int i = 0; i < n; i++) cur = cur * A[i][ps[i]] % MOD;
            res += cur;
            if (res >= MOD) res -= MOD;
            if (res < 0) res += MOD;
        } while (ArrayUtils.nextPermutation(ps));
        return res;
    }

    @Test
    @Ignore("powered is currently the same as naivePowered.")
    public void testMatrixPower() {
        int n = 80;
        int MOD = (int) (1e9 + 9);
        int iterationCount = 100;
        Random rnd = new Random(120812048L);
        double totalTime = 0, totalNaiveTime = 0;
        double worstTime = 0, worstNaiveTime = 0;
        for (int iteration = 0; iteration < iterationCount; iteration++) {
            System.err.println("iteration = " + iteration);
            long[][] A = new long[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = rnd.nextInt(MOD);
                }
            }
            long power = TestUtils.randomLong((long) 1e18, rnd);
            long start = System.currentTimeMillis();
            long[][] res = Matrix.powered(A, power, MOD);
            long time = System.currentTimeMillis() - start;
            totalTime += time;
            worstTime = Math.max(worstTime, time);
            start = System.currentTimeMillis();
            long[][] exp = NaivePowered(A, power, MOD);
            time = System.currentTimeMillis() - start;
            totalNaiveTime += time;
            worstNaiveTime = Math.max(worstNaiveTime, time);
            if (!ArrayUtils.equals(exp, res)) throw new AssertionError();
        }
        double averageTime = totalTime / iterationCount;
        double averageNativeTime = totalNaiveTime / iterationCount;
        System.err.println("average : " + averageTime);
        System.err.println("naive : " + averageNativeTime);
        System.err.println("worst : " + worstTime);
        System.err.println("worstNaive : " + worstNaiveTime);
    }

    private long[][] NaivePowered(long[][] A, long power, final int mod) {
        if (power == 0) return Matrix.identity(A.length);
        return new PowerOperation<long[][]>() {
            @Override
            protected long[][] associativeOperation(long[][] a, long[][] b) {
                return Matrix.mul(a, b, mod);
            }
        }.power(A, power);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
