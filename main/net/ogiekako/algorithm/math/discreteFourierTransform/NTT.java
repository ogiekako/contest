package net.ogiekako.algorithm.math.discreteFourierTransform;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NTT {
    // Primes where p-1 = 2^23 * x, p < 2^30.
    // p[0] * p[1] * p[2] > 2^23 * 2^31 * 2^31, which is large enough to reconstruct the solution.
    // 3 is a primitive root of every p.
    private static final int[] p = {645922817, 897581057, 998244353};

    // invP[i][j] = inv p[i] mod p[j].
    private static final long[][] invP;

    static {
        invP = new long[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) invP[i][j] = MathUtils.inverse(p[i], p[j]);
            }
        }
    }

    /**
     * n, the size of a and b devices p-1. n is a power of two.
     * g is a primitive root of p-1.
     * <p>
     * f(x) = a[0] + a[1]x + ... + a[n-1]x^(n-1).
     * ret[i] = f(g^((p-1) / n * i)).
     */
    private static void ntt(int sign, long[] a, int p, int g) {
        int n = a.length, d = Integer.numberOfLeadingZeros(n) + 1;
        if (Integer.bitCount(n) != 1) throw new IllegalArgumentException("n should be a power of 2 but was " + n);

        for (int i = 0; i < n; i++) {
            int j = Integer.reverse(i) >>> d;
            if (j < i) {
                ArrayUtils.swap(a, i, j);
            }
        }
        long w = MathUtils.powMod(g, (p - 1) / n, p);
        if (sign == -1) w = MathUtils.inverse(w, p);
        recur(a, n, w, p);
    }

    private static void recur(long[] a, int m, long w, int p) {
        if (m == 1) {
            if (w != 1) throw new RuntimeException();
            return;
        }
        if (w == 1) throw new RuntimeException();
        recur(a, m >> 1, w * w % p, p);

        int mh = m >> 1;
        long[] ws = new long[mh];
        for (int i = 0; i < mh; i++) {
            ws[i] = i == 0 ? 1 : ws[i - 1] * w % p;
        }
        for (int i = 0; i < a.length; i += m) {
            for (int j = i; j < i + mh; j++) {
                int k = j + mh;
                long ak = (a[k] * ws[j - i] % p);
                a[k] = a[j] - ak;
                if (a[k] < 0) a[k] += p;
                a[j] += ak;
                if (a[j] >= p) a[j] -= p;
            }
        }
    }

    /**
     * n: minimum power of 2 such that n >= |a| + |b| - 1.
     * n should divide p - 1. (for arbitrary mod, use convolutionMod).
     * g is a primitive root of p-1.
     * <p>
     * ret[i] = sum_j a[j] * b[i - j] % p.
     * |ret| = n.
     *
     * < 2s with |a|=|b| = 2^19.
     */
    public static long[] convolution(long[] a, long[] b, int p, int g) {
        int n = 1;
        while (n < a.length + b.length - 1) n *= 2;
        a = Arrays.copyOf(a, n);
        b = Arrays.copyOf(b, n);
        ntt(1, a, p, g);
        ntt(1, b, p, g);
        for (int i = 0; i < n; i++) {
            a[i] = a[i] * b[i] % p;
        }
        ntt(-1, a, p, g);
        long inv = MathUtils.inverse(n, p);
        for (int i = 0; i < n; i++) {
            a[i] = a[i] * inv % p;
        }
        return a;
    }

    /**
     * res[i] = Σ a[j] * b[i-j]
     * <p>
     * Precondition:
     * 0 <= a[i] < mod.
     * 0 <= b[i] < mod.
     * mod < 1 << 30.
     * <p>
     * < 2s with 2^18 elements.
     */
    public static long[] convolutionAnyMod(long[] a, long[] b, int mod) {
        if (mod > 1 << 30) throw new IllegalArgumentException("mod is too large.");

        long[][] c = new long[p.length][];

        for (int k = 0; k < p.length; k++) {
            c[k] = convolution(a, b, p[k], 3);
        }
        int len = a.length + b.length - 1;
        long[] res = new long[len];
        for (int i = 0; i < len; i++) {
            res[i] = garner(c[0][i], c[1][i], c[2][i], mod);
        }
        return res;
    }

    private static long garner(long a, long b, long c, int mod) {
        // ans = x + yp + zpq.
        // x = a  mod p.
        // y = (b - x) / p  mod q.
        // z = (c - x - yp) / (p * q)  mod r
        long x = a % p[0];
        long y = (b - a) * invP[0][1] % p[1];
        if (y < 0) y += p[1];
        long z = (c - x - y * p[0]) % p[2] * invP[0][2] % p[2] * invP[1][2] % p[2];
        if (z < 0) z += p[2];
        return (x + y * p[0] % mod + z * p[0] % mod * p[1]) % mod;
    }

    public static void main(String[] args) {
        int base = 1 << 23;

        List<Integer> cands = new ArrayList<>();
        for (int i = 1; i < 1 << 7; i++) {
            int n = base * i;
            if (!MathUtils.isPrime(n + 1)) continue;

            for (int g = 2; ; g++) {
                if (MathUtils.powMod(g, n, n + 1) != 1) throw new RuntimeException();
                boolean ok = true;
                for (long d : MathUtils.divisors(n)) {
                    if (d < n && MathUtils.powMod(g, d, n + 1) == 1) ok = false;
                }
                if (ok) {
                    if (g == 3) cands.add(n + 1);
                    break;
                }
            }
        }
        int len = cands.size();
        BigInteger mul = BigInteger.ONE;
        for (int i = len - 1; i >= len - 3; i--) {
            mul = mul.multiply(BigInteger.valueOf(cands.get(i)));
            System.out.println(cands.get(i));
        }
        BigInteger obj = BigInteger.valueOf(base).multiply(BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(Integer.MAX_VALUE)));
        if (mul.compareTo(obj) > 0) {
            System.out.println("OK");
        }
    }
}
