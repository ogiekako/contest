package net.ogiekako.algorithm.dataStructure.tree;

import java.util.Arrays;


public class LongRangeMinimumQuery {
    // root : 0
    // parent : (i-1)/2
    // left,right : 2*i+1, 2*i+2
    // leaf : i >= n-1
    private long[] dat;
    private int n;
    public LongRangeMinimumQuery(int _n) {
        n = 1;
        while (n < _n) n <<= 1;
        dat = new long[n + n];
        Arrays.fill(dat, Long.MAX_VALUE);
    }

    /**
     * O(log n)
     */
    public void update(int index, long value) {
        index += n - 1;
        dat[index] = value;
        while (index > 0) {
            index = (index - 1) >> 1;
            dat[index] = Math.min(dat[(index << 1) + 1], dat[(index << 1) + 2]);
        }
    }

    /**
     * O(log n)
     *
     * @return the minimum value in [left,right). If there is a non-updated value in the interval,
     *         return value will be Integer.MAX_VALUE.
     */
    public long query(int left, int right) {
        return query(left, right, 0, 0, n);
    }

    private long query(int a, int b, int i, int l, int r) {
        if (r <= a || b <= l) return Long.MAX_VALUE;
        if (a <= l && r <= b) return dat[i];
        long vl = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
        long vr = query(a, b, (i << 1) + 2, (l + r) >>> 1, r);
        return Math.min(vl, vr);
    }

    /**
     * O(log n).
     *
     * @return minimum index i such that i-th value in the array
     *         is less than the given value and i is contained in [left,right).
     *         If there is no such index, this method returns -1.
     */
    public long minimumIndexValueIsLessThan(int left, int right, long value) {
        long res = minimumIndexValueIsLessThan(left, right, 0, 0, n, value);
        return res == Long.MAX_VALUE ? -1 : res;
    }

    private long minimumIndexValueIsLessThan(int a, int b, int i, int l, int r, long v) {
        if (r <= a || b <= l) return Integer.MAX_VALUE;
        if (a <= l && r <= b) {
            long mn = query(a, b, i, l, r);// O(1)
            if (mn >= v) return Integer.MAX_VALUE;
            if (r - l == 1) return i - n + 1;
            mn = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
            if (mn < v) return minimumIndexValueIsLessThan(a, b, (i << 1) + 1, l, (l + r) >>> 1, v);
            return minimumIndexValueIsLessThan(a, b, (i << 1) + 2, (l + r) >>> 1, r, v);
        }
        long vl = minimumIndexValueIsLessThan(a, b, (i << 1) + 1, l, (l + r) >>> 1, v);
        if (vl < Long.MAX_VALUE) return vl;
        return minimumIndexValueIsLessThan(a, b, (i << 1) + 2, (l + r) >>> 1, r, v);
    }
}
