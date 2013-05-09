package net.ogiekako.algorithm.string.problems;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;
// aoj2292
public class NumberOfCommonPalindrome {
    public static void main(String[] args) {
        new NumberOfCommonPalindrome().run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String t = sc.next();
        long res = solve(s, t);
        System.out.println(res);
    }

    private long solve(String s, String t) {
        s = sand(s);
        t = sand(t);
        StringBuilder b = new StringBuilder();
        b.append(s);
        b.append('$');
        b.append(rev(s));
        b.append('%');
        b.append(t);
        b.append('&');
        b.append(rev(t));
        b.append(' ');
        long res = solve(b.toString(), s.length(), t.length());
        res -= ((long) (s.length() + 1) / 2) * (t.length() + 1) / 2;
        res /= 2;
        return res;
    }

    private long solve(String s, int n, int m) {
        int[] sa = suffixArray_log(s);
        int[] sainv = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            sainv[sa[i]] = i;
        }
        int[] lcp = calcLCP(sa, s);
        E[] ordLcp = new E[lcp.length];
        for (int i = 0; i < lcp.length; i++) {
            ordLcp[i] = new E(i, lcp[i]);
        }
        sort(ordLcp);
        RMQ rmq = new RMQ(lcp.length);
        for (int i = 0; i < lcp.length; i++) {
            rmq.update(i, lcp[i]);
        }
        E[] ordSK = new E[n];
        E[] ordTK = new E[m];
        for (int i = 0; i < n; i++) {
            int j = 2 * n - i;
            int from = min(sainv[i], sainv[j]);
            int to = max(sainv[i], sainv[j]);
            ordSK[i] = new E(from, rmq.query(from, to));
        }
        for (int i2 = 0; i2 < m; i2++) {
            int i = 2 * n + 2 + i2;
            int j = 2 * n + 2 * m + 2 - i2;
            int from = min(sainv[i], sainv[j]);
            int to = max(sainv[i], sainv[j]);
            ordTK[i2] = new E(from, rmq.query(from, to));
        }
        sort(ordSK);
        sort(ordTK);

        X = 0;
        long res = 0;
        UnionFind uf = new UnionFind(s.length());
        for (int k = max(n, m), il = 0, is = 0, it = 0; k >= 1; k--) {
            while (is < ordSK.length && ordSK[is].k == k) {
                int g = uf.root(ordSK[is].i);
                uf.numS[g]++;
                X += uf.numT[g];
                is++;
            }
            while (it < ordTK.length && ordTK[it].k == k) {
                int g = uf.root(ordTK[it].i);
                uf.numT[g]++;
                X += uf.numS[g];
                it++;
            }
            while (il < ordLcp.length && ordLcp[il].k == k) {
                uf.union(ordLcp[il].i, ordLcp[il].i + 1);
                il++;
            }
            res += X;
        }
        return res;
    }

    long X;

    class UnionFind {
        final int[] tree;
        long[] numS;
        long[] numT;

        public UnionFind(int n) {
            this.tree = new int[n];
            numS = new long[n];
            numT = new long[n];
            fill(tree, -1);
        }
        void union(int x, int y) {
            x = root(x);
            y = root(y);
            if (x != y) {
                if (tree[x] > tree[y]) {
                    int t = x; x = y; y = t;
                }
                // x <- y
                X -= numS[x] * numT[x];
                X -= numS[y] * numT[y];
                numS[x] += numS[y];
                numT[x] += numT[y];
                numS[y] = numT[y] = 0;
                X += numS[x] * numT[x];

                tree[x] += tree[y];
                tree[y] = x;
            }
        }

        int root(int x) {
            return tree[x] < 0 ? x : (tree[x] = root(tree[x]));
        }
    }


    class RMQ {
        private int[] dat;
        private int n;
        public RMQ(int _n) {
            n = 1;
            while (n < _n) n <<= 1;
            dat = new int[n + n];
            Arrays.fill(dat, Integer.MAX_VALUE);
        }

        public void update(int i, int a) {
            i += n - 1;
            dat[i] = a;
            while (i > 0) {
                i = (i - 1) >> 1;
                dat[i] = Math.min(dat[(i << 1) + 1], dat[(i << 1) + 2]);
            }
        }

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
    }

    class E implements Comparable<E> {
        int i, k;

        public E(int i, int k) {
            super();
            this.i = i;
            this.k = k;
        }
        public int compareTo(E o) {
            return -(k - o.k);
        }
        @Override
        public String toString() {
            return "E [i=" + i + ", k=" + k + "]";
        }

    }

    public int[] calcLCP(int[] SA, String s) {
        if (SA.length != s.length()) throw new IllegalArgumentException();
        int n = s.length();
        int[] rank = new int[n];// s[i..] がSAにおいて何番目か.
        for (int i = 0; i < n; i++) rank[SA[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++)
            if (rank[i] < n - 1) {
                while (s.charAt(i + h) == s.charAt(SA[rank[i] + 1] + h)) h++;
                lcp[rank[i]] = h;
                if (h > 0) h--;
            }
        return lcp;
    }

    /**
     * s に対するsuffix arrayを返す.
     * s[n-1] は,それ以外のどの文字よりも小さくなければならない.
     * O(n log n).
     * n=800000 で1s くらい.
     * wataさんのライブラリを参考にした.
     */
    public int[] suffixArray_log(String s) {
        int n = s.length() - 1;
        char[] cs = s.toCharArray();
        int[] is = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            is[i] = cs[i];
        }
        int[] si = indexSort(is);
        int[] a = new int[n + 1], b = new int[n + 1];
        for (int h = 0; ; ) {
            for (int i = 0; i < n; i++) {
                int x = si[i + 1], y = si[i];
                b[i + 1] = b[i];
                if (is[x] > is[y] || is[x + h] > is[y + h]) b[i + 1]++;
            }
            for (int i = 0; i < n + 1; i++) {
                is[si[i]] = b[i];
            }
            if (b[n] == n) break;
            h = max(1, h << 1);
            for (int k = h; k >= 0; k -= h) {
                fill(b, 0);
                b[0] = k;
                for (int i = k; i < n + 1; i++) {
                    b[is[i]]++;
                }
                for (int i = 0; i < n; i++) {
                    b[i + 1] += b[i];
                }
                for (int i = n; i >= 0; i--) {
                    a[--b[si[i] + k > n ? 0 : is[si[i] + k]]] = si[i];
                }
                int[] tmp = si; si = a; a = tmp;
            }
        }
        return si;
    }

    private int[] indexSort(int[] is) {
        int n = is.length;
        long[] ls = new long[n];
        for (int i = 0; i < n; i++) {
            ls[i] = (long) is[i] << 32 | i;
        }
        sort(ls);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = (int) ls[i];
        }
        return res;
    }

    private String rev(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private String sand(String s) {
        StringBuilder b = new StringBuilder();
        for (char c : s.toCharArray()) {
            b.append('*');
            b.append(c);
        }
        b.append('*');
        return b.toString();
    }
}
