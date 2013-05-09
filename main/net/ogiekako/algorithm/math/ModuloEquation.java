package net.ogiekako.algorithm.math;

public class ModuloEquation {
    // res % ms[i] = rs[i]
    // gcd(ms[i],ms[j]) must be 1.  mul ms[i] must < Integer.MAX_VALUE
    public static long moduloEquation(int[] ms, int[] rs) {
        long m = ms[0];
        long d = rs[0];
        for (int i = 1; i < ms.length; i++) {
            long[] xy = ExEuclid.exGcd(m, (long) ms[i]);
            long mm = m * ms[i];
            // may overflow if m > Integer.MAX_VALUE
            d = (m * rs[i] % mm * xy[0] + d * ms[i] % mm * xy[1]) % mm;
            m = mm;
        }
        if (d < 0) d += m;
        return d;
    }

    public static int plus(int a, int b, int modulus) {
        int res = a + b;
        if (res >= modulus) res -= modulus;
        return res;
    }
}
