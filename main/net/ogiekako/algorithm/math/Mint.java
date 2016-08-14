package net.ogiekako.algorithm.math;

import java.math.BigInteger;

public class Mint {
    public static Mint ZERO;
    public static Mint ONE;

    final long x;

    private static int MOD = 0;

    private Mint(long x) {
        this.x = normalize(x);
    }

    public static void set1e9_7() {
        setMod((int) (1e9 + 7));
    }

    public static void setMod(int mod) {
        if (mod <= 0) throw new IllegalArgumentException("mod should be positive: " + mod);
        if (!BigInteger.valueOf(mod).isProbablePrime(10)) throw new IllegalArgumentException("mod must be a prime." + mod);
        MOD = mod;

        ZERO = of(0);
        ONE = of(1);
    }

    public static int getMod() {
        if (MOD == 0) throw new IllegalArgumentException("Please set mod first.");
        return MOD;
    }

    public static long normalize(long x) {
        if (0 <= x) {
            if (x < MOD) return x;
            if (x < MOD * 2) return x - MOD;
            return x % MOD;
        }
        if (-MOD <= x) return x + MOD;
        x %= MOD;
        return x == 0 ? 0 : x + MOD;
    }

    private static long mulLong(long x, long y) {
        return normalize(normalize(x) * normalize(y));
    }

    // O(log y)
    public static long divLong(long x, long y) {
        return normalize(normalize(x) * MathUtils.inverse(normalize(y), MOD));
    }

    public int get() {
        return (int) x;
    }

    public static Mint of(long value) {
        return new Mint(value);
    }

    public Mint mulInv() {
        return of(MathUtils.inverse(x, getMod()));
    }

    public Mint mul(Mint other) {
        return mul(other.x);
    }

    public Mint mul(long y) {
        return of(mulLong(x, y));
    }

    public Mint div(Mint other) {
        return div(other.x);
    }

    public Mint div(long y) {
        return of(divLong(x, y));
    }

    public Mint add(Mint other) {
        return of(x + other.x);
    }

    public Mint add(long y) {
        return of(x + normalize(y));
    }

    public Mint minus(Mint other) {
        return minus(other.x);
    }

    public Mint minus(long y) {
        return of(x - normalize(y));
    }

    public Mint addInv() {
        return of(-x);
    }

    public boolean isZero() {
        return x == 0;
    }

    public boolean isOne() {
        return x == normalize(1);
    }

    @Override
    public String toString() {
        return "" + x;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mint)) return false;
        Mint other = (Mint)o;
        return x == other.x;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(x);
    }
}
