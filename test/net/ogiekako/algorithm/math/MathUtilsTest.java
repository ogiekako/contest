package net.ogiekako.algorithm.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class MathUtilsTest {

    @Before
    public void beforeTest() {
        Mint.set1e9_7();
    }

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
        long[][] C = MathUtils.genCombTable(50);
        long[][] CMod = MathUtils.genCombTableMod(50, 20, MOD);
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
        MathUtils.factorize(from, to);
    }

    @Test
    public void testFactorize2() {
        long from = (long) 1e12, to = (long) (1e12 + 1e3);
        PrimeDecomposition[] pd = MathUtils.factorize(from, to);
        for (int i = 0; i < pd.length; i++) checkPrimeDecompositionValidity(from + i, pd[i]);
    }

    private void checkPrimeDecompositionValidity(long number, PrimeDecomposition pd) {
        long v = 1;
        for (PrimePower f : pd) {
            Assert.assertTrue(BigInteger.valueOf(f.prime).isProbablePrime(10));
            v *= MathUtils.power(f.prime, f.power);
        }
        Assert.assertEquals(number, v);
    }

    @Test
    public void testComb() {
        Mint.set1e9_7();
        int[][] exp = new int[][]{
                {1,0,0,0},
                {1,1,0,0},
                {1,2,1,0},
                {1,3,3,1},
        };
        for (int i = 0; i < exp.length; i++) for (int j = 0; j < exp[i].length; j++){
            Assert.assertEquals(exp[i][j], MathUtils.comb(i,j).get());
        }
    }

    @Test(timeout=2000)
    public void testCombLarge() {
        Mint.set1e9_7();
        int n = 5000;

        // long[][] を更新していくのに比べて2倍程度。728ms.
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) MathUtils.comb(i,j);
    }

    @Test
    public void testFact() {
        Mint.setMod(13);
        int exp = 1;
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(exp % 13, MathUtils.fact(i).get());
            exp *= i+1;
        }
    }

    @Test
    public void testIfact() {
        Mint.setMod(13);
        int fact = 1;
        for (int i = 0; i < 10; i++) {
            Mint res = MathUtils.ifact(i);
            Assert.assertEquals(1, res.mul(fact).get());
            fact = fact * (i+1) % Mint.getMod();
        }
    }

    @Test
    public void testCatalanNumber() {
        Mint.setMod(40009);
        long[] exp = {1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786 % 40009};
        for (int i = 0; i < exp.length; i++) Assert.assertEquals(exp[i], MathUtils.catalan(i).get());
    }

    @Test(timeout=1000)
    public void testCatalanNumberLarge() {
        for (int i = 0; i < 50000; i++) MathUtils.catalan(i);
    }

    @Test
    public void testCatalanTriangle() {
        Assert.assertEquals(2, MathUtils.catalan(2, 1).get());
        Assert.assertEquals(MathUtils.catalan(3), MathUtils.catalan(3, 3));
    }

    @Test
    public void testCatalanTransposed() {
        Assert.assertEquals(0, MathUtils.catalanTransposed(3,0).get());
        Assert.assertEquals(2, MathUtils.catalanTransposed(3,1).get());
        Assert.assertEquals(2, MathUtils.catalanTransposed(3,2).get());
        Assert.assertEquals(1, MathUtils.catalanTransposed(3,3).get());
        Assert.assertEquals(0, MathUtils.catalanTransposed(3,4).get());
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
