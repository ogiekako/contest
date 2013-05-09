package net.ogiekako.algorithm.convolution;

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
        long[] g = new long[1 << N];
        _zetaConvolution(f, g, N - 1, 0);
        return g;
    }

    private static void _zetaConvolution(long[] f, long[] g, int n, int mask) {
        if (n < 0) {
            g[mask] = f[mask];
            return;
        }
        _zetaConvolution(f, g, n - 1, mask);
        _zetaConvolution(f, g, n - 1, mask | 1 << n);
        for (int i = 0; i < 1 << n; i++) {
            g[mask | 1 << n | i] += g[mask | i];
        }
    }

    /*
    g.length = 1<<N.
    res[T] = sum_{S \subset T} (-1)^|T-S| g[S]
     */
    public static long[] mobiusConvolution(long[] g, int N) {
        long[] f = new long[1 << N];
        _mobiusConvolution(f, g, N - 1, 0);
        return f;
    }

    private static void _mobiusConvolution(long[] f, long[] g, int n, int mask) {
        if (n < 0) {
            f[mask] = g[mask];
            return;
        }
        _mobiusConvolution(f, g, n - 1, mask);
        _mobiusConvolution(f, g, n - 1, mask | 1 << n);
        for (int i = 0; i < 1 << n; i++) {
            f[mask | 1 << n | i] -= f[mask | i];
        }
    }

    /*
    f.length = 1<<N.
    res[T] = sum_{T \subset S} f[S]
     */
    public static long[] zetaConvolutionInv(long[] f, int N) {
        long[] f2 = functionToComplement(f, N);
        long[] g2 = zetaConvolution(f2, N);
        long[] g = functionToComplement(g2, N);
        return g;
    }

    /*
   g.length = 1<<N.
   res[T] = sum_{T \subset S} (-1)^|S-T|g[S]
    */
    public static long[] mobiusConvolutionInv(long[] g, int N) {
        long[] g2 = functionToComplement(g, N);
        long[] f2 = mobiusConvolution(g2, N);
        long[] f = functionToComplement(f2, N);
        return f;
    }

    /*
    f.length = 1<<N.
    res[T] = f[ \bar{T} ].
     */
    public static long[] functionToComplement(long[] f, int N) {
        long[] res = new long[1 << N];
        for (int i = 0; i < 1 << N; i++) res[i] = f[complement(i, N)];
        return res;
    }

    private static int complement(int mask, int N) {
        return ((1 << N) - 1) ^ mask;
    }
}
