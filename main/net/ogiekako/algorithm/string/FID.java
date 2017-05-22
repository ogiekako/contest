package net.ogiekako.algorithm.string;

import java.util.BitSet;

/**
 * Fully Indexable Dictionary
 */
public class FID {
    int n;
    int D = 128;
    int[] ranks;
    long[] bits;

    public FID(int len) {
        n = len;
        bits = new long[(n >> 6) + 1];
    }

    public void set(int i) {
        if (ranks != null) throw new IllegalStateException("Don't call set after build.");
        bits[i >> 6] |= 1L << (i & 63);
    }

    public boolean get(int i) {
        return (bits[i >> 6] >> (i & 63) & 1) == 1;
    }

    public void build() {
        if (ranks != null) throw new IllegalStateException("Call build only once.");
        ranks = new int[n / D + 2];
        for (int i = 0; i < n; i++) {
            if (get(i)) ranks[i / D + 1]++;
        }
        for (int i = 0; i < ranks.length - 1; i++) {
            ranks[i + 1] += ranks[i];
        }
    }

    /**
     * The number of b-s in [0, pos).
     * <p>
     * < 100ms for 10^6 queries to 10^6 size bits.
     */
    public int rank(boolean b, int pos) {
        if (ranks == null) throw new IllegalStateException("Call build().");
        if (pos >= n) pos = n;
        int j = pos / D;
        int res = ranks[j];
        res += cardinality(j * D, pos);
        return b ? res : pos - res;
    }

    /**
     * The index of the ind-th b. (0-origin)
     * <p>
     * < 200ms for 10^6 queries to 10^6 size bits.
     */
    public int select(boolean b, int ind) {
        if (ranks == null) throw new IllegalStateException("Call build().");
        int left = 0, right = ranks.length;
        while (right - left > 1) {
            int mid = (left + right) >>> 1;
            int val = b ? ranks[mid] : mid * D - ranks[mid];
            if (val <= ind) {
                left = mid;
            } else {
                right = mid;
            }
        }
        ind -= b ? ranks[left] : left * D - ranks[left];
        left *= D;
        int from = left;
        right = Math.min(n + 1, right * D);
        while (right - left > 1) {
            int mid = (left + right) >>> 1;
            int val = b ? cardinality(from, mid) : (mid - from) - cardinality(from, mid);
            if (val <= ind) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left >= n ? -1 : left;
    }

    private int cardinality(int from, int to) {
        if ((from & 63) != 0) throw new RuntimeException();
        if (to - from >= D) throw new RuntimeException();
        int res = 0;
        for (; from + 64 <= to; from += 64) {
            res += Long.bitCount(bits[from >> 6]);
        }
        return res + Long.bitCount(bits[from >> 6] & ((1L << (to - from)) - 1));
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) {
            b.append(get(i) ? '1' : '0');
        }
        return b.toString();
    }
}
