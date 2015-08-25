package net.ogiekako.algorithm.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class MathUtilsTest {
    @Test
    public void testSumOfGeometricSequence() throws Exception {
        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            int N = rnd.nextInt(10000) + 1;
            int base = rnd.nextInt(10000) + 1;
            int modulus = rnd.nextInt(10000) + 1;
            long exp = 0;
            long exponent = 1;
            for (int j = 0; j < N; j++) {
                exp += exponent;
                exp %= modulus;
                exponent = exponent * base % modulus;
            }
            long res = MathUtils.sumOfGeometricSequence(base, N, modulus);
            Assert.assertEquals(exp, res);
        }
    }
    @Test
    public void testCombination() throws Exception {
        int MOD = 10007;
        long[][] C = MathUtils.combination(50);
        long[][] CMod = MathUtils.generateCombinationMod(50, 20, MOD);
        for (int i = 0; i < CMod.length; i++)
            for (int j = 0; j < CMod[0].length; j++) {
                Assert.assertEquals(C[i][j] % MOD, CMod[i][j]);
            }
    }

    @Test
    public void testFactorize() {
        for (int i = 1; i <= 100; i++) {
            PrimeDecomposition pd = MathUtils.factorize(i);
            checkPrimeDecompositionValidity(i, pd);
        }

        for (int from = 1; from < 10; from++)
            for (int to = from; to < 10; to++) {
                PrimeDecomposition[] fs = MathUtils.factorize(from, to);
                if (fs.length != to - from) throw new AssertionError();
                for (int i = 0; i < fs.length; i++) {
                    int v = from + i;
                    PrimeDecomposition pd = fs[i];
                    checkPrimeDecompositionValidity(v, pd);
                }
            }

        for (long from = (long) 1e12; from <= 1e12 + 10; from++)
            for (long to = from; to < 1e12 + 10; to++) {
                PrimeDecomposition[] fs = MathUtils.factorize(from, to);
                if (fs.length != to - from) throw new AssertionError();
                for (int i = 0; i < fs.length; i++) {
                    long v = from + i;
                    PrimeDecomposition pd = fs[i];
                    checkPrimeDecompositionValidity(v, pd);
                }
            }
    }

    @Test(timeout = 2000)
    public void testFactorizeSpeed() {
        long from = (long) 1e12, to = (long) (1e12 + 1e5);
        PrimeDecomposition[] pd = MathUtils.factorize(from, to);
    }
    public void testFactorize2() {
        long from = (long) 1e12, to = (long) (1e12 + 1e6);
        PrimeDecomposition[] pd = MathUtils.factorize(from, to);
        for (int i = 0; i < pd.length; i++) checkPrimeDecompositionValidity(from + i, pd[i]);
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private void checkPrimeDecompositionValidity(long number, PrimeDecomposition pd) {
        long v = 1;
        for (PrimePower f : pd) {
            Assert.assertTrue(MathUtils.isPrime(f.prime));
            v *= MathUtils.power(f.prime, f.power);
        }
        Assert.assertEquals(number, v);
    }

    @Test
    public void testCatalanNumber() {
        long[] exp = {1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786};
        long[] res = MathUtils.generateCatalanNumber(exp.length);
        Assert.assertArrayEquals(exp, res);
    }

    @Test
    public void testDivisors() throws Exception {
        long[] res = MathUtils.divisors(60);
        long[] exp = {1,2,3,4,5,6,10,12,15,20,30,60};
        Assert.assertArrayEquals(exp, res);

        res = MathUtils.divisors(97821761637600L);
        Assert.assertEquals(17280, res.length);
    }
}
