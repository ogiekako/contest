package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

public class FoxAndGCDLCM {
    public long get(long G, long L) {
        if (L % G != 0) return -1;
        long C = L / G;
        long res = Long.MAX_VALUE;
        for (long a = 1; a * a <= C; a++) {
            if (C % a == 0) {
                long b = C / a;
                if (MathUtils.gcd(a, b) == 1)
                    res = Math.min(res, a + b);
            }
        }
        return res * G;
    }
}

