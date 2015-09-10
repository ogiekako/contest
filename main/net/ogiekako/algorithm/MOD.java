package net.ogiekako.algorithm;

import net.ogiekako.algorithm.math.MathUtils;

public class MOD {
    private static int MOD = (int) (1e9 + 7);

    public static void set(int mod) {
        if (mod <= 0) throw new IllegalArgumentException("mod should be positive: " + mod);
        MOD = mod;
    }

    public static int get() {
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

    public static long mul(long x, long y) {
        return normalize(normalize(x) * normalize(y));
    }

    // O(log y)
    public static long div(long x, long y) {
        return normalize(normalize(x) * MathUtils.inverse(normalize(y), MOD));
    }

    public static long plus(long x,long y){
        return normalize(normalize(x) + normalize(y));
    }

    public static long minus(long x, long y) {
        return normalize(normalize(x) - normalize(y));
    }
}
