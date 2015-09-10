package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.algebra.Mint;
import net.ogiekako.algorithm.math.algebra.Ring;
import net.ogiekako.algorithm.utils.Cast;

import java.util.ArrayList;
import java.util.Arrays;

public class Polynomial extends Ring<Polynomial> {
    // degree
    final int n;
    final Mint[] a;

    private static Mint[] asMints(long[] a) {
        Mint[] res = new Mint[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = Mint.of(a[i]);
        }
        return res;
    }

    public static final Polynomial ZERO = of(0);

    Polynomial(Mint[] a) {
        this.a = a.clone();

        int n = a.length - 1;
        while (n > 0 && (a[n] == null || a[n].isZero())) n--;
        this.n = n;
    }

    public static Polynomial of(long... a) {
        return of(asMints(a));
    }

    public static Polynomial of(Mint... a) {
        return new Polynomial(a);
    }

    public int degree() {
        return n;
    }

    // "2x^2 + x - 5"
    public static Polynomial fromString(String formula) {
        formula = formula.replaceAll(" ", "").replaceAll("(\\+|-)", " $1");
        String[] items = formula.split(" ");
        ArrayList<Long> as = new ArrayList<Long>();
        for (String item : items) {
            if (!item.contains("x")) {
                as.set(0, Long.valueOf(item));
                continue;
            }
            item = item.replaceAll("x", "");
            String a = item;
            int deg = 1;
            if (item.contains("^")) {
                String[] ss = item.split("\\^");
                a = ss[0];
                deg = Integer.valueOf(ss[1]);
            }
            while (as.size() <= deg) {
                as.add(0L);
            }
            if (a.isEmpty() || a.equals("+") || a.equals("-")) a += "1";
            as.set(deg, Long.valueOf(a));
        }
        return Polynomial.of(Cast.toLong(as));
    }

    @Override
    public String toString() {
        if (degree() == 0) {
            return "0";
        }
        StringBuilder b = new StringBuilder();
        for (int i = n; i >= 0; i--) {
            if (a[i].isZero()) continue;
            if (i < n) {
                b.append(" + ");
            }
            if (i == 0 || !a[i].isOne()) {
                b.append(a[i].get());
            }
            if (i == 0) continue;
            b.append("x");
            if (i == 1) continue;
            b.append("^").append(i);
        }
        return b.toString();
    }

    /**
     * P * Q.
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

    /**
     * <p>Tested</p>
     */
    public Polynomial mul(Polynomial other) {
        Mint[] P = a;
        Mint[] Q = other.a;
        int m = other.degree();
        Mint[] res = new Mint[n + m + 1];
        Arrays.fill(res, Mint.ZERO);
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                res[i + j] = res[i + j].add(P[i].mul(Q[j]));
            }
        }
        return new Polynomial(res);
    }

    public Polynomial mul(long... Q) {
        return mul(of(Q));
    }

    public Polynomial mul(Mint... Q) {
        return mul(of(Q));
    }

    /**
     * <p>Tested</p>
     */
    public Polynomial add(Polynomial other) {
        int degree = Math.max(degree(), other.degree());
        Mint[] P = new Mint[degree + 1];
        for (int i = 0; i <= degree; i++) {
            P[i] = getCoefficient(i).add(other.getCoefficient(i));
        }
        return of(P);
    }

    public Polynomial add(Mint... Q) {
        return add(of(Q));
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

    public Mint getCoefficient(int i) {
        return i >= a.length || a[i] == null ? Mint.ZERO : a[i];
    }

    /**
     * <p>Tested</p>
     */
    public Mint evaluate(long x) {
        return evaluate(Mint.of(x));
    }

    public Mint evaluate(Mint x) {
        Mint res = Mint.ZERO;
        Mint xx = Mint.ONE;
        for (int i = 0; i <= n; i++) {
            res = res.add(a[i].mul(xx));
            xx = xx.mul(x);
        }
        return res;
    }
}
