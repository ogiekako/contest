package net.ogiekako.algorithm.convolution;

import net.ogiekako.algorithm.iteration.Iteration;
import net.ogiekako.algorithm.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;

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
}
