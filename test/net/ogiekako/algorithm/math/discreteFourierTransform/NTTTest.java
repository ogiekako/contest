package net.ogiekako.algorithm.math.discreteFourierTransform;

import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.math.ExEuclid;
import net.ogiekako.algorithm.math.MathUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class NTTTest {
    private void test(long[] a, long[] b, int mod, long[] exp) {
        long[] res = NTT.convolutionAnyMod(a, b, mod);
        Assert.assertArrayEquals(exp, res);
    }

    @Test
    public void ntt() throws Exception {
        test(new long[]{1, 1, 2}, new long[]{1, 2, 2}, 5, new long[]{1, 3, 1, 1, 4});
    }

    @Test
    public void ntt2() throws Exception {
        test(new long[]{1000000000, 1000000001}, new long[]{1000000000, 1000000002}, 100000, new long[]{0, 0, 2});
    }

    @Test
    public void convolution() throws Exception {
        // http://blog.brucemerry.org.za/2016/11/tco-2016-finals.html
        int mod = 998244353;
        int root = 3;
        int n = 1 << 19;
        long[] a = new long[n];
        long[] b = new long[n];
        Random rnd = new Random(12412);
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(mod);
            b[i] = rnd.nextInt(mod);
        }

        int[] ks = new int[2];
        long[] exp = new long[ks.length];
        for (int i = 0; i < ks.length; i++) {
            ks[i] = rnd.nextInt(n);
            for (int j = 0; j <= ks[i]; j++) {
                exp[i] = (exp[i] + a[j] * b[ks[i] - j]) % mod;
            }
        }

        long[] c = NTT.convolution(a,b,mod,root);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(exp[i], c[ks[i]]);
        }
    }

    @Test
    public void nttRand() throws Exception {
        int n = 1 << 18;
        long[] a = new long[n];
        long[] b = new long[n];
        Random rnd = new Random(14019824);
        int mod = (int) 1e7;
        for (int i = 0; i < n / 2; i++) {
            a[i] = rnd.nextInt(Integer.MAX_VALUE / 2) % mod;
            b[i] = rnd.nextInt(Integer.MAX_VALUE / 2) % mod;
        }
        long[] res = NTT.convolutionAnyMod(a, b, mod);
        for (int i = 0; i < 100; i++) {
            int k = rnd.nextInt(a.length);
            long exp = 0;
            for (int j = 0; j <= k; j++) {
                exp = (exp + a[j] * b[k - j]) % mod;
            }
            Assert.assertEquals(exp, res[k]);
        }
    }
}