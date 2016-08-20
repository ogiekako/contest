package on2016_08.on2016_08_21_TopCoder_SRM__518.Nim;



import net.ogiekako.algorithm.convolution.SubsetConvolution;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;

import java.util.Arrays;

public class Nim {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int count(int K, int L) {
        Mint.set1e9_7();
        int n = 64 - Long.numberOfLeadingZeros(L);
        Mint[] f = new Mint[1 << n];
        for (int i = 0; i < 1 << n; i++) f[i] = i <= L && MathUtils.isPrime(i) ? Mint.ONE : Mint.ZERO;
        f = power(f, K);
        return f[0].get();
    }

    private Mint[] power(Mint[] f, int K) {
        if (K == 1) return f;
        if (K % 2 == 1) return mul(power(f, K - 1), f);
        f = power(f, K / 2);
        return mul(f, f);
    }

    private Mint[] mul(Mint[] f, Mint[] g) {
        return SubsetConvolution.xorConvolution(f, g);
    }
}
