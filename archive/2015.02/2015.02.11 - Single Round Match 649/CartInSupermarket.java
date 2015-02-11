package src;

import java.util.Arrays;

public class CartInSupermarket {
    public int calcmin(int[] a, int b) {
        long res = (1L<<32) - 1;
        for (long i = 1L << 31; i > 0; i /= 2) {
            if (possible(a, b, res - i)) {
                res -= i;
            }
        }
        return (int) res;
    }

    private boolean possible(int[] a, long b, long x) {
        long times = 0;
        for (int k : a) {
            times += solve(k, x);
            if (times > b) return false;
        }
        return true;
    }

    private long solve(long k, long x) {
        long y = k - 1;
        if (!possible2(k, x, y)) return Integer.MAX_VALUE;
        for (long i = 1L << 31; i > 0; i /= 2) {
            if (i <= y && possible2(k, x, y - i)) {
                y -= i;
            }
        }
        return y;
    }

    private boolean possible2(long k, long x, long y) {
        if (y == 0) {
            return x >= k;
        }
        int z = Long.numberOfTrailingZeros(Long.highestOneBit(y));
        long d1 = 2 * (y - (1L << z) + 1);
        long d0 = (1L << z + 1) - 1 - y;
        return k <= d1 * (x - z - 1) + d0 * (x - z);
    }

    void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
