package net.ogiekako.algorithm.convolution;

import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.misc.iteration.Iteration;
import net.ogiekako.algorithm.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SubsetConvolutionTest {
    @Test
    public void testZetaConvolution() throws Exception {
        int n = 10;
        final long[] f = TestUtils.generateRandomLongArray(1 << n, 100);
        long[] resG = SubsetConvolution.zetaConvolution(f, n);
        final long[] expG = new long[f.length];
        for (int i = 0; i < 1 << n; i++) {
            final int finalI = i; Iteration.iterateSubset(i, new Iteration.IntFunc() {
                public void doIt(int comb) {
                    expG[finalI] += f[comb];
                }
            });
        }
        Assert.assertArrayEquals(expG, resG);
    }
    @Test
    public void testZetaConvolutionCorner() {
        long[] f = new long[1];
        f[0] = 3;
        long[] resG = SubsetConvolution.zetaConvolution(f, 0);
        long[] expG = new long[]{3};
        Assert.assertArrayEquals(expG, resG);
    }

    @Test
    public void testMobiusConvolution() throws Exception {
        int n = 10;
        final long[] g = TestUtils.generateRandomLongArray(1 << n, 10);
        final long[] expF = new long[1 << n];
        for (int T = 0; T < 1 << n; T++) {
            final int finalT = T; Iteration.iterateSubset(T, new Iteration.IntFunc() {
                public void doIt(int S) {
                    int bit = Integer.bitCount(finalT ^ S);
                    if ((bit & 1) == 0) {
                        expF[finalT] += g[S];
                    } else {
                        expF[finalT] -= g[S];
                    }
                }
            });
        }
        long[] resF = SubsetConvolution.mobiusConvolution(g, n);
        Assert.assertArrayEquals(expF, resF);
    }

    @Test
    public void testMobiusConvolutionCorner() {
        long[] g = new long[1];
        g[0] = 3;
        long[] resF = SubsetConvolution.mobiusConvolution(g, 0);
        long[] expF = new long[]{3};
        Assert.assertArrayEquals(expF, resF);
    }

    @Test
    public void testZetaMobius() {
        int n = 20;
        int upTo = 10000;
        long[] f = TestUtils.generateRandomLongArray(1 << n, upTo);
        long[] g = SubsetConvolution.zetaConvolution(f, n);
        long[] resF = SubsetConvolution.mobiusConvolution(g, n);
        Assert.assertArrayEquals(f, resF);

        g = TestUtils.generateRandomLongArray(1 << n, upTo);
        f = SubsetConvolution.mobiusConvolution(g, n);
        long[] resG = SubsetConvolution.zetaConvolution(f, n);
        Assert.assertArrayEquals(g, resG);
    }

    @Test
    public void testZetaInv() {
        int n = 10;
        final long[] f = TestUtils.generateRandomLongArray(1 << n, 100);
        long[] resG = SubsetConvolution.zetaConvolutionInv(f, n);
        final long[] expG = new long[f.length];
        for (int i = 0; i < 1 << n; i++) {
            int comp = (1 << n) - 1 ^ i;
            final int finalI = i; Iteration.iterateSubset(comp, new Iteration.IntFunc() {
                public void doIt(int comb) {
                    expG[finalI] += f[finalI | comb];
                }
            });
        }
        Assert.assertArrayEquals(expG, resG);
    }

    @Test
    public void testMobiusConvolutionInv() throws Exception {
        int n = 10;
        final long[] g = TestUtils.generateRandomLongArray(1 << n, 100);
        final long[] expF = new long[1 << n];
        for (int T = 0; T < 1 << n; T++) {
            int C = (1 << n) - 1 ^ T;
            final int finalT = T; Iteration.iterateSubset(C, new Iteration.IntFunc() {
                public void doIt(int D) {
                    int S = finalT | D;
                    int bit = Integer.bitCount(finalT) - Integer.bitCount(S);
                    if ((bit & 1) == 0) {
                        expF[finalT] += g[S];
                    } else {
                        expF[finalT] -= g[S];
                    }
                }
            });
        }
        long[] resF = SubsetConvolution.mobiusConvolutionInv(g, n);
        Assert.assertArrayEquals(expF, resF);
    }

    @Test
    public void testZetaMobiusInv() {
        int n = 20;
        int upTo = 10000;
        long[] f = TestUtils.generateRandomLongArray(1 << n, upTo);
        long[] g = SubsetConvolution.zetaConvolutionInv(f, n);
        long[] resF = SubsetConvolution.mobiusConvolutionInv(g, n);
        Assert.assertArrayEquals(f, resF);

        g = TestUtils.generateRandomLongArray(1 << n, upTo);
        f = SubsetConvolution.mobiusConvolutionInv(g, n);
        long[] resG = SubsetConvolution.zetaConvolutionInv(f, n);
        Assert.assertArrayEquals(g, resG);
    }

    private void checkSubsetConvolution(long[] f, long[] g, long[] h) {
        long[] res = SubsetConvolution.fastSubsetConvolution(f, g);
        Assert.assertArrayEquals(h, res);
    }

    @Test
    public void testSubsetConvolution() {
        checkSubsetConvolution(new long[]{1}, new long[]{1}, new long[]{1});
        checkSubsetConvolution(new long[]{1,1,1,1}, new long[]{1,1,1,1}, new long[]{1,2,2,4});
        checkSubsetConvolution(new long[]{1,1,1,1,1,1,1,1}, new long[]{1,1,1,1,1,1,1,1}, new long[]{1,2,2,4,2,4,4,8});
        checkSubsetConvolution(new long[]{1,2,3,4}, new long[]{1,2,3,4}, new long[]{1,4,6,20});

        int n = 1<<10;
        Random rnd = new Random(109410284L);
        long[] f = new long[n];
        long[] g = new long[n];
        long[] h = new long[n];
        for (int i = 0; i < n; i++) f[i] = rnd.nextInt(100);
        for (int i = 0; i < n; i++) g[i] = rnd.nextInt(100);
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if((i|j) == i) {
            h[i] += f[j] * g[i^j];
        }
        checkSubsetConvolution(f,g,h);
    }

    @Test(timeout=1000)
    public void testSubsetConvolutionLarge() {
        int n = 1<<16;
        Random rnd = new Random(112847L);
        long[] f = new long[n];
        long[] g = new long[n];
        for (int i = 0; i < n; i++) f[i] = rnd.nextInt(100);
        for (int i = 0; i < n; i++) g[i] = rnd.nextInt(100);
        SubsetConvolution.fastSubsetConvolution(f, g);
    }

    private void checkXorConvolution(long[] _f, long[] _g, long[] _h) {
        Mint[] f = new Mint[_f.length];
        Mint[] g = new Mint[_g.length];
        Mint[] h = new Mint[_h.length];
        for (int i = 0; i < _f.length; i++) {
            f[i] = Mint.of(_f[i]);
            g[i] = Mint.of(_g[i]);
            h[i] = Mint.of(_h[i]);
        }
        Mint[] res = SubsetConvolution.xorConvolution(f, g);
        Assert.assertArrayEquals(h, res);
    }

    @Test
    public void testXorConvolution() {
        Mint.set1e9_7();
        checkXorConvolution(new long[]{1}, new long[]{1}, new long[]{1});
        checkXorConvolution(new long[]{1,1}, new long[]{1,1}, new long[]{2,2});
        checkXorConvolution(new long[]{1,1,1,1}, new long[]{1,1,1,1}, new long[]{4,4,4,4});
        checkXorConvolution(new long[]{1,2,3,4}, new long[]{1,2,3,4}, new long[]{30,2+2+12+12,3+8+3+8,4+6+6+4});

        int n = 1<<10;
        Random rnd = new Random(109410284L);
        long[] f = new long[n];
        long[] g = new long[n];
        long[] h = new long[n];
        for (int i = 0; i < n; i++) f[i] = rnd.nextInt(100);
        for (int i = 0; i < n; i++) g[i] = rnd.nextInt(100);
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            h[i] += f[j] * g[i^j];
        }
        checkXorConvolution(f,g,h);
    }

    @Test(timeout=1000)
    public void testXorConvolutionLarge() {
        Mint.set1e9_7();
        int n = 1<<18;
        Random rnd = new Random(112847L);
        Mint[] f = new Mint[n];
        Mint[] g = new Mint[n];
        for (int i = 0; i < n; i++) f[i] = Mint.of(rnd.nextInt(1000000));
        for (int i = 0; i < n; i++) g[i] = Mint.of(rnd.nextInt(1000000));
        SubsetConvolution.xorConvolution(f, g);
    }
}
