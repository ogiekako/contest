package net.ogiekako.algorithm.dataStructure.tree;

import java.util.Arrays;


public class IntRangeMinimumQuery {
    // root : 0
    // parent : (i-1)/2
    // left,right : 2*i+1, 2*i+2
    // leaf : i >= n-1
    private int[] dat;
    private int n;
    public IntRangeMinimumQuery(int _n) {
        n = 1;
        while (n < _n) n <<= 1;
        dat = new int[n + n];
        Arrays.fill(dat, Integer.MAX_VALUE);
    }

    /**
     * O(log n)
     */
    public void update(int index, int value) {
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
    public int query(int left, int right) {
        return query(left, right, 0, 0, n);
    }

    private int query(int a, int b, int i, int l, int r) {
        if (r <= a || b <= l) return Integer.MAX_VALUE;
        if (a <= l && r <= b) return dat[i];
        int vl = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
        int vr = query(a, b, (i << 1) + 2, (l + r) >>> 1, r);
        return Math.min(vl, vr);
    }

    /**
     * O(log n).
     *
     * @return minimum index i such that i-th value in the array
     *         is less than the given value and i is contained in [left,right).
     *         If there is no such index, this method returns -1.
     */
    public int minimumIndexValueIsLessThan(int left, int right, int value) {
        int res = minimumIndexValueIsLessThan(left, right, 0, 0, n, value);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private int minimumIndexValueIsLessThan(int a, int b, int i, int l, int r, int v) {
        if (r <= a || b <= l) return Integer.MAX_VALUE;
        if (a <= l && r <= b) {
            int mn = query(a, b, i, l, r);// O(1)
            if (mn >= v) return Integer.MAX_VALUE;
            if (r - l == 1) return i - n + 1;
            mn = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
            if (mn < v) return minimumIndexValueIsLessThan(a, b, (i << 1) + 1, l, (l + r) >>> 1, v);
            return minimumIndexValueIsLessThan(a, b, (i << 1) + 2, (l + r) >>> 1, r, v);
        }
        int vl = minimumIndexValueIsLessThan(a, b, (i << 1) + 1, l, (l + r) >>> 1, v);
        if (vl < Integer.MAX_VALUE) return vl;
        return minimumIndexValueIsLessThan(a, b, (i << 1) + 2, (l + r) >>> 1, r, v);
    }
}
