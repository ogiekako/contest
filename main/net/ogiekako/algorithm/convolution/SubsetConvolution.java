package net.ogiekako.algorithm.convolution;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;

/*
公式
[ T \subset S ] = [ \bar{S} \subset \bar{T} ]
 */
public class SubsetConvolution {
    /*
    f.length = 1<<N.
    res[T] = sum_{S \subset T} f[S].
    */
    public static long[] zetaConvolution(long[] f, int N) {
        return convolution(f, N, 1, 0);
    }

    /*
    g.length = 1<<N.
    res[T] = sum_{S \subset T} (-1)^|T-S| g[S]
     */
    public static long[] mobiusConvolution(long[] g, int N) {
        return convolution(g, N, -1, 0);
    }

    /*
    d = 1 or -1.
    subset = 0 or 1
    subset = 1 -> f(T) = sum_{S⊆T}d^|T＼S|g(S)
    subset = 0 -> f(T) = sum_{T⊆S}d^|T＼S|g(S)
     */
    private static long[] convolution(long[] g, int N, int d, int subset) {
        long[] f = new long[1 << N];
        System.arraycopy(g, 0, f, 0, f.length);
        for (int i = 0; i < N; i++) {
            for (int S = 0; S < 1 << N; S++) {
                if ((S >> i & 1) == subset) {
                    f[S ^ (1 << i)] += d * f[S];
                }
            }
        }
        return f;
    }

    /*
    f.length = 1<<N.
    res[T] = sum_{T \subset S} f[S]
     */
    public static long[] zetaConvolutionInv(long[] f, int N) {
        return convolution(f, N, 1, 1);
    }

    /*
   g.length = 1<<N.
   res[T] = sum_{T \subset S} (-1)^|S-T|g[S]
    */
    public static long[] mobiusConvolutionInv(long[] g, int N) {
        return convolution(g, N, -1, 1);
    }

    private static int log(int n) {
        return 63 - Long.numberOfLeadingZeros(n);
    }
    private static long[][] leveledConvolution(long[] f) {
        int n = log(f.length);
        long[][] f2 = new long[n+1][1<<n];
        for (int i = 0; i < 1<<n; i++) {
            f2[Integer.bitCount(i)][i] = f[i];
        }
        for (int i = 0; i < n+1; i++) {
            f2[i] = convolution(f2[i], n, 1, 0);
        }
        return f2;
    }

    /**
     * f*g(S) = \sum_{T\subseteq S} f(T)g(S\setminus T).
     *
     * 残念ながら、実用上は O(3^n) でやったほうが速い。
     */
    public static long[] fastSubsetConvolution(long[] f, long[] g) {
        System.err.println("fastSubsetConvolution is SLOW! Use O(3^n) algorithm.");
        if (Integer.bitCount(f.length) != 1) throw new IllegalArgumentException("Size must be a power of 2.");
        if(f.length != g.length) throw new IllegalArgumentException("Sizes must be the same.");
        int n = log(f.length);
        long[][] f2 = leveledConvolution(f);
        long[][] g2 = leveledConvolution(g);
        long[] h2 = new long[1<<n];
        long[] h = new long[1<<n];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < 1 << n; j++) {
                h2[j] = 0;
                for (int k = 0; k <= i; k++) {
                    h2[j] += f2[k][j] * g2[i-k][j];
                }
            }
            h2 = convolution(h2, n, -1, 0);
            for (int j = 0; j < 1 << n; j++) {
                if (Integer.bitCount(j) == i) h[j] = h2[j];
            }
        }
        return h;
    }

    /**
     * h[S] = \sum_T f[T]g[S^T]
     *
     * Verified: SRM 518 Hard Nim.
     */
    public static Mint[] xorConvolution(Mint[] f, Mint[] g) {
        if (Integer.bitCount(f.length) != 1) throw new IllegalArgumentException("Size must be a power of 2.");
        if(f.length != g.length) throw new IllegalArgumentException("Sizes must be the same.");

        int[] _f = new int[f.length];
        int[] _g = new int[g.length];
        for (int i = 0; i < f.length; i++) _f[i] = f[i].get();
        for (int i = 0; i < g.length; i++) _g[i] = g[i].get();

        int[] h = xorConvolutionSub(_f, _g, Mint.getMod());
        long invN = MathUtils.inverse(f.length, Mint.getMod());
        for (int i = 0; i < h.length; i++) h[i] = (int) ((long)h[i] * invN % Mint.getMod());

        Mint[] _h = new Mint[h.length];
        for (int i = 0; i < h.length; i++)
            _h[i] = Mint.of(h[i]);
        return _h;
    }

    private static int[] xorConvolutionSub(int[] f, int[] g, int mod) {
        int N = f.length;
        if (N == 1) {
            int[] h = new int[1];
            h[0] = (int) ((long)f[0] * g[0] % mod);
            return h;
        }
        int[] f0 = new int[N/2];
        int[] f1 = new int[N/2];
        int[] g0 = new int[N/2];
        int[] g1 = new int[N/2];
        for (int i = 0; i < N/2; i++) {
            f0[i] = f[i << 1] + f[i << 1 | 1];
            f1[i] = f[i << 1] - f[i << 1 | 1];
            g0[i] = g[i << 1] + g[i << 1 | 1];
            g1[i] = g[i << 1] - g[i << 1 | 1];
            if(f0[i]>=mod)f0[i] -= mod;
            if(f1[i]<0)f1[i] += mod;
            if(g0[i]>=mod)g0[i] -= mod;
            if(g1[i]<0)g1[i] += mod;
        }
        int[] h0 = xorConvolutionSub(f0, g0, mod);
        int[] h1 = xorConvolutionSub(f1, g1, mod);
        int[] h = new int[N];
        for (int i = 0; i < N / 2; i++) {
            h[i << 1] = h0[i] + h1[i];
            if (h[i << 1] >= mod) h[i << 1] -= mod;
            h[i << 1 | 1] = h0[i] - h1[i];
            if (h[i << 1 | 1] < 0) h[i << 1 | 1] += mod;
        }
        return h;
    }
}
