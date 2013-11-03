package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.algebra.Ring;

public class Polynomial extends Ring<Polynomial> {
    long[] a_;
    Polynomial(long[] a) {
        a_ = a.clone();
    }
    public static Polynomial of(int n) {
        return new Polynomial(new long[n]);
    }
    public static Polynomial of(long[] a) {
        return new Polynomial(a);
    }

    /**
     * P % Q.
     *
     * @verified
     */
    public static long[] mul(long[] P, long[] Q, int MOD) {
        long sub = Long.MAX_VALUE / MOD * MOD;
        int n = P.length, m = Q.length;
        long[] res = new long[n + m - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i + j] += P[i] * Q[j];
                if (res[i + j] < 0) res[i + j] -= sub;
            }
        }
        for (int i = 0; i < res.length; i++) res[i] %= MOD;
        return res;
    }

    // x^k % P(x).
    // O(n^2 log k)
    public static long[] mod(long k, long[] P, int MOD) {
        long[] res = new long[P.length - 1];
        if (res.length <= 0) return res;// zero
        res[0] = 1;
        long[] pow = mod(new long[]{0, 1}, P, MOD);// x
        while (k > 0) {
            if ((k & 1) == 1) {
                res = mod(mul(pow, res, MOD), P, MOD);
            }
            pow = mod(mul(pow, pow, MOD), P, MOD);
            k >>>= 1;
        }
        return res;
    }

    // P(x) % Q(x)
    public static long[] mod(long[] P, long[] Q, int MOD) {
        int n = Q.length;
        long sub = Long.MAX_VALUE / MOD * MOD;
        Q = Q.clone();
        long inv = MathUtils.inverse(Q[n - 1], MOD);
        for (int i = 0; i < n; i++) Q[i] = Q[i] * inv % MOD;
        P = P.clone();
        for (int i = P.length - 1; i >= n - 1; i--)
            if (P[i] > 0) {
                long mul = MOD - P[i] % MOD;
                for (int j = 0; j < n; j++) {
                    P[i - j] += mul * Q[n - 1 - j];
                    if (P[i - j] < 0) P[i - j] -= sub;
                }
            }
        long[] res = new long[n - 1];
        for (int i = 0; i < n - 1 && i < P.length; i++) res[i] = P[i] % MOD;
        return res;
    }

    public Polynomial mul(Polynomial other) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Polynomial add(Polynomial other) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Polynomial addInv() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isZero() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Polynomial zero() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
