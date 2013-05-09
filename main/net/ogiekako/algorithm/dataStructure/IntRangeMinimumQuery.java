package net.ogiekako.algorithm.dataStructure;

import java.util.Arrays;


class IntRangeMinimumQuery {
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
     *
     * @param i
     * @param a
     */
    public void update(int i, int a) {
        i += n - 1;
        dat[i] = a;
        while (i > 0) {
            i = (i - 1) >> 1;
            dat[i] = Math.min(dat[(i << 1) + 1], dat[(i << 1) + 2]);
        }
    }

    /**
     * [l,r) の最小値を返す.一度もupdateされていないものが含まれている場合は,
     * Integer.MAX_VALUE を返す.
     * O(log n)
     *
     * @param l
     * @param r
     * @return
     */
    public int query(int l, int r) {
        return query(l, r, 0, 0, n);
    }

    private int query(int a, int b, int i, int l, int r) {
        if (r <= a || b <= l) return Integer.MAX_VALUE;
        if (a <= l && r <= b) return dat[i];
        int vl = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
        int vr = query(a, b, (i << 1) + 2, (l + r) >>> 1, r);
        return Math.min(vl, vr);
    }

    /**
     * [l,r) で,値がv を下回るような最小のid を返す.
     * 存在しない場合は, -1 を返す.
     * O(log n).
     *
     * @param l
     * @param r
     * @param v
     * @return
     */
    public int minimumIndexValueIsLessThan(int l, int r, int v) {
        int res = minimumIndexValueIsLessThan(l, r, 0, 0, n, v);
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
        int vr = minimumIndexValueIsLessThan(a, b, (i << 1) + 2, (l + r) >>> 1, r, v);
        return vr;
    }
}
