package net.ogiekako.algorithm.dataStructure;

import net.ogiekako.algorithm.string.FID;

/**
 * https://research.preferred.jp/2013/01/wavelettree_world/
 * http://d.hatena.ne.jp/anta1/20130114/1358113488
 */
public class WaveletMatrix {
    int D;
    int n;
    FID[] B;
    int[] k;
    // Maximum value.
    int M;

    /**
     * s will be modified.
     * Precondition: s[i] >= 0.
     */
    public WaveletMatrix(int[] s) {
        for (int ss : s) if (ss < 0) throw new IllegalArgumentException("ss should be positive but was " + ss);

        M = 0;
        for (int ss : s) M = Math.max(M, ss);
        D = 1;
        while (1L << D <= M) D++;

        n = s.length;
        B = new FID[D];
        k = new int[D];

        int[] buf = new int[n], tmp;
        for (int i = D - 1; i >= 0; i--, tmp = s, s = buf, buf = tmp) {
            int pos = 0;
            for (int j = 0; j < n; j++) {
                if (s[j] << 31 - i >= 0) {
                    buf[pos++] = s[j];
                }
            }
            k[i] = pos;
            B[i] = new FID(n);
            for (int j = 0; j < n; j++) {
                if (s[j] << 31 - i < 0) {
                    B[i].set(j);
                    buf[pos++] = s[j];
                }
            }
            B[i].build();
        }
    }

    /**
     * The number of c-s in [0, pos)
     */
    public int rank(int c, int pos) {
        if (c > M) return 0;

        int s = 0, e = pos;
        for (int i = D - 1; i >= 0; i--) {
            boolean cur = c << 31 - i < 0;
            s = B[i].rank(cur, s);
            e = B[i].rank(cur, e);
            if (cur) {
                s += k[i];
                e += k[i];
            }
        }
        return e - s;
    }

    /**
     * The index of the ind-th c (0-origin).
     */
    public int select(int c, int ind) {
        if (c > M) return -1;

        int[] s = new int[D];
        for (int i = D - 1; i >= 1; i--) {
            boolean cur = c << 31 - i < 0;
            s[i - 1] = B[i].rank(cur, s[i]);
            if (cur) {
                s[i - 1] += k[i];
            }
        }

        for (int i = 0; i < D; i++) {
            boolean cur = c << 31 - i < 0;
            ind = B[i].select(cur, ind + B[i].rank(cur, s[i])) - s[i];
            if (ind < 0) return -1;
        }

        return ind;
    }

    /**
     * The maximum number in [s, e)
     */
    public int max(int s, int e) {
        if (s >= e) return 0;
        int res = 0;
        for (int i = D - 1; i >= 0; i--) {
            boolean one = B[i].rank(true, e) - B[i].rank(true, s) > 0;

            s = B[i].rank(one, s);
            e = B[i].rank(one, e);
            if (one) {
                res |= 1 << i;
                s += k[i];
                e += k[i];
            }
        }
        return res;
    }

    /**
     * The minimum number in [s, e)
     */
    public int min(int s, int e) {
        if (s >= e) return Integer.MAX_VALUE;
        int res = 0;
        for (int i = D - 1; i >= 0; i--) {
            boolean one = B[i].rank(false, e) - B[i].rank(false, s) == 0;

            s = B[i].rank(one, s);
            e = B[i].rank(one, e);
            if (one) {
                res |= 1 << i;
                s += k[i];
                e += k[i];
            }
        }
        return res;
    }

    /**
     * The j-th largest number in [s, e)
     */
    public int quantile(int s, int e, int j) {
        if (j >= e - s) throw new IllegalArgumentException("j >= e - s.  s: " + s + "  e: " + e + "  j: " + j);
        int res = 0;
        for (int i = D - 1; i >= 0; i--) {
            int one = B[i].rank(true, e) - B[i].rank(true, s);
            boolean cur = one > j;

            s = B[i].rank(cur, s);
            e = B[i].rank(cur, e);
            if (cur) {
                res |= 1 << i;
                s += k[i];
                e += k[i];
            } else {
                j -= one;
            }
        }
        return res;
    }

    /**
     * #numbers ∈ [x, y) in [s, e).
     */
    public int rangefreq(int s, int e, int x, int y) {
        x = Math.min(x, M + 1);
        y = Math.min(y, M + 1);

        return recurRF(D - 1, s, e, x, y, 0, (1 << D));
    }

    private int recurRF(int d, int s, int e, int x, int y, int lower, int upper) {
        if (x == lower && y == upper) return e - s;
        int mid = (lower + upper) >>> 1;
        int res = 0;
        if (x < mid) {
            res += recurRF(d - 1, B[d].rank(false, s), B[d].rank(false, e), x, Math.min(y, mid), lower, mid);
        }
        if (y > mid) {
            res += recurRF(d - 1, B[d].rank(true, s) + k[d], B[d].rank(true, e) + k[d], Math.max(x, mid), y, mid, upper);
        }
        return res;
    }

    /**
     * Largest c in [s, e) such that x <= c < y. -1 on not found.
     */
    public int prevvalue(int s, int e, int x, int y) {
        x = Math.min(x, M + 1);
        y = Math.min(y, M + 1);

        return recurPV(D - 1, s, e, x, y, 0, (1 << D));
    }

    private int recurPV(int d, int s, int e, int x, int y, int lower, int upper) {
        if (e == s) return -1;
        if (d < 0) {
            return x;
        }
        int mid = (lower + upper) >>> 1;
        if (y > mid) {
            int res = recurPV(d - 1, B[d].rank(true, s) + k[d], B[d].rank(true, e) + k[d], Math.max(x, mid), y, mid, upper);
            if (res >= 0) return res;
        }
        if (x < mid) {
            return recurPV(d - 1, B[d].rank(false, s), B[d].rank(false, e), x, Math.min(y, mid), lower, mid);
        }
        return -1;
    }

    /**
     * Smallest c in [s, e) such that x <= c < y. -1 on not found.
     */
    public int nextvalue(int s, int e, int x, int y) {
        x = Math.min(x, M + 1);
        y = Math.min(y, M + 1);

        return recurNV(D - 1, s, e, x, y, 0, (1 << D));
    }

    private int recurNV(int d, int s, int e, int x, int y, int lower, int upper) {
        if (e == s) return -1;
        if (d < 0) {
            return x;
        }
        int mid = (lower + upper) >>> 1;
        if (x < mid) {
            int res = recurNV(d - 1, B[d].rank(false, s), B[d].rank(false, e), x, Math.min(y, mid), lower, mid);
            if (res >= 0) return res;
        }
        if (y > mid) {
            return recurNV(d - 1, B[d].rank(true, s) + k[d], B[d].rank(true, e) + k[d], Math.max(x, mid), y, mid, upper);
        }
        return -1;
    }
}
