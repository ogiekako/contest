package net.ogiekako.algorithm.math;

import java.math.BigInteger;

public class Mint {
    private static int MOD = 0;

    public static Mint ZERO = new UninitializedMint();
    public static Mint ONE = new UninitializedMint();

    final long x;

    private Mint() {
        x = 0;
    }

    private Mint(long x) {
        if (x < 0 || x >= getMod()) throw new IllegalArgumentException("x should be in the valid range, but was " + x);
        this.x = x;
    }

    public static void set1e9_7() {
        setMod((int) (1e9 + 7));
    }

    public static void setMod(int mod) {
        if (mod <= 0) throw new IllegalArgumentException("mod should be positive: " + mod);
        if (!BigInteger.valueOf(mod).isProbablePrime(10))
            throw new IllegalArgumentException("mod must be a prime." + mod);
        MOD = mod;

        ZERO = of(0);
        ONE = of(1);
    }

    public static int getMod() {
        if (MOD == 0) throw new IllegalArgumentException("Please set mod first.");
        return MOD;
    }

    public static long normalize(long x) {
        int m = getMod();
        if (0 <= x) {
            if (x < m) return x;
            if (x < m * 2) return x - m;
            return x % m;
        }
        if (-m <= x) return x + m;
        x %= m;
        return x == 0 ? 0 : x + m;
    }

    // O(log y)
    static long divLong(long x, long y) {
        return normalize(normalize(x) * MathUtils.inverse(normalize(y), MOD));
    }

    public int get() {
        return (int) x;
    }

    public static Mint of(long value) {
        return new Mint(normalize(value));
    }

    public Mint mulInv() {
        if (x == 0) throw new IllegalArgumentException("Zero has no inverse.");
        return of(MathUtils.inverse(x, getMod()));
    }

    public Mint mul(Mint other) {
        return of(x * other.x);
    }

    public Mint mul(long y) {
        if (y >= MOD || y < 0) throw new IllegalArgumentException("y should be within the valid range, but was " + y);
        return of(x * y);
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
        if (y >= MOD || y < 0) throw new IllegalArgumentException("y should be within the valid range, but was " + y);
        return of(x + y);
    }

    public Mint minus(Mint other) {
        return of(x - other.x);
    }

    public Mint minus(long y) {
        if (y >= MOD || y < 0) throw new IllegalArgumentException("y should be within the valid range, but was " + y);
        return of(x - y);
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
        Mint other = (Mint) o;
        return x == other.x;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(x);
    }

    private static class UninitializedMint extends Mint {

        private UninitializedMint() {}

        @Override
        public int get() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint mulInv() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint mul(Mint other) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint mul(long y) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint div(Mint other) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint div(long y) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint add(Mint other) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint add(long y) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint minus(Mint other) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint minus(long y) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public Mint addInv() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public boolean isZero() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public boolean isOne() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public String toString() {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public boolean equals(Object o) {
            throw new IllegalArgumentException("Uninitialized mod");
        }

        @Override
        public int hashCode() {
            throw new IllegalArgumentException("Uninitialized mod");
        }
    }
}
