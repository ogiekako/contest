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

    /**
     * Precondition: s[i] >= 0.
     */
    public WaveletMatrix(int[] s) {
        for (int ss : s) if (ss < 0) throw new IllegalArgumentException("ss should be positive but was " + ss);

        int max = 0;
        for (int ss : s) max = Math.max(max, ss);
        D = 1;
        while (1L << D <= max) D++;

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
}
