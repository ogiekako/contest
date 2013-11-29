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
}
