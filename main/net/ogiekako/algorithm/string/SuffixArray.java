package net.ogiekako.algorithm.string;

import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Math.max;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;

public class SuffixArray {
    /**
     * s に対するsuffix arrayを返す.
     * sは以下を満たさねばならない.
     * s[n-1] は,それ以外のどの文字よりも小さい.
     * 150万で1sくらい
     * O(n).
     *
     * @param s
     * @return
     */
    public int[] suffixArray(String s) {
        int n = s.length();
        for (int i = 0; i < n - 1; i++)
            if (s.charAt(i) <= s.charAt(n - 1))
                throw new IllegalArgumentException("last character must be sentinel.");
        boolean[] ex = new boolean[256];
        int numLetter = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (!ex[c]) numLetter++;
            ex[c] = true;
        }
        int[] id = new int[256];
        numLetter = 0;
        for (int i = 0; i < 256; i++) if (ex[i]) id[i] = numLetter++;
        int[] is = new int[n];
        for (int i = 0; i < n; i++) is[i] = id[s.charAt(i)];
        int[] SA = suffixArray(is, numLetter);
        return SA;
    }

    private int[] suffixArray(int[] a, final int numLetter) {
        int n = a.length;
        if (numLetter == n) {
            int[] res = new int[n];
            for (int i = 0; i < n; i++) res[a[i]] = i;
            return res;
        }
        int[] to = new int[numLetter];
        int[] head = new int[numLetter];// staticにおいてもいい.
        int[] tail = new int[numLetter];// staticにおいてもいい.
        for (int i = 0; i < n; i++) to[a[i]]++;
        for (int i = 0; i < numLetter - 1; i++) to[i + 1] += to[i];
        for (int i = 0; i < numLetter; i++) head[i] = i == 0 ? 0 : to[i - 1];
        System.arraycopy(to, 0, tail, 0, numLetter);

        boolean[] S_Type = new boolean[n];
        S_Type[n - 1] = true;
        int numLMS = 0;
        for (int i = n - 2; i >= 0; i--) {
            if (a[i] < a[i + 1] || a[i] == a[i + 1] && S_Type[i + 1]) S_Type[i] = true;
            if (!S_Type[i] && S_Type[i + 1]) numLMS++;
        }
        int[] SA = new int[n];
        Arrays.fill(SA, -1);
        int[] i2LMS = new int[n];
        int[] LMS2i = new int[numLMS];
        for (int i = n - 1, j = numLMS; i >= 1; i--)
            if (i == n - 1 || S_Type[i] && !S_Type[i - 1]) {// i : LMS
                i2LMS[i] = --j;
                LMS2i[j] = i;
                SA[--tail[a[i]]] = i;
            }
        // step2
        for (int i = 0; i < n; i++)
            if (SA[i] > 0 && !S_Type[SA[i] - 1]) {
                SA[head[a[SA[i] - 1]]++] = SA[i] - 1;
            }
        System.arraycopy(to, 0, tail, 0, numLetter);
        // step3
        for (int i = n - 1; i >= 0; i--)
            if (SA[i] > 0 && S_Type[SA[i] - 1]) {
                SA[--tail[a[SA[i] - 1]]] = SA[i] - 1;
            }
        // construct a1
        int[] a1 = new int[numLMS];
        int numLet1 = -1;
        for (int i = 0, bef = -1; i < n; i++)
            if (SA[i] > 0 && S_Type[SA[i]] && !S_Type[SA[i] - 1]) {// SA1[i] : LMS
                boolean same = true;
                if (bef == -1) same = false;
                else {
                    int tmp = i2LMS[SA[i]];// LMS id
                    if (bef == numLMS - 1 || tmp == numLMS - 1 || LMS2i[bef + 1] - LMS2i[bef] != LMS2i[tmp + 1] - LMS2i[tmp]) {
                        same = false;
                    } else {
                        for (int j = 0; j < LMS2i[bef + 1] - LMS2i[bef]; j++) {
                            if (a[LMS2i[bef] + j] != a[LMS2i[tmp] + j] || S_Type[LMS2i[bef] + j] != S_Type[LMS2i[tmp] + j]) {
                                same = false; break;
                            }
                        }
                    }
                }
                if (same) a1[i2LMS[SA[i]]] = numLet1;
                else {
                    bef = i2LMS[SA[i]];// LMS id
                    a1[i2LMS[SA[i]]] = ++numLet1;
                }
            }
        numLet1++;
        int[] SA1 = suffixArray(a1, numLet1);// i -> i-th substr id

        // reuse SA,head,tail
        Arrays.fill(SA, -1);
        for (int i = 0; i < numLetter; i++) head[i] = i == 0 ? 0 : to[i - 1];
        System.arraycopy(to, 0, tail, 0, numLetter);

        // step1
        for (int lms = numLMS - 1; lms >= 0; lms--) {
            int i = LMS2i[SA1[lms]];
            SA[--tail[a[i]]] = i;
        }
        // step2
        for (int i = 0; i < n; i++)
            if (SA[i] > 0 && !S_Type[SA[i] - 1]) {
                SA[head[a[SA[i] - 1]]++] = SA[i] - 1;
            }
        // step3
        System.arraycopy(to, 0, tail, 0, numLetter);
        for (int i = n - 1; i >= 0; i--)
            if (SA[i] > 0 && S_Type[SA[i] - 1]) {
                SA[--tail[a[SA[i] - 1]]] = SA[i] - 1;
            }
        return SA;
    }

    class SAComp implements Comparator<Integer> {
        int h;
        int[] g;
        SAComp(int h, int[] g) {
            this.h = h; this.g = g;
        }
        public int compare(Integer a, Integer b) {
            return g[a] != g[b] ? g[a] - g[b] : g[a + h] - g[b + h];
        }
    }

    /**
     * s に対するsuffix arrayを返す.
     * s[n-1] は,それ以外のどの文字よりも小さくなければならない.
     * O(n log n).
     * n=800000 で1s くらい.
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

    /**
     * s に対するsuffix arrayを返す.
     * s[n-1] は,それ以外のどの文字よりも小さくなければならない.
     * O(n (log n)^2).
     * n = 300000 で1s くらい.
     * spaghetti sourceを参考にした.
     * http://www.prefield.com/algorithm/string/suffix_array.html
     */
    public int[] suffixArray_log2(String s) {
        int n = s.length() - 1;
        int[] g = new int[n + 1];
        int[] b = new int[n + 1];
        Integer[] v = new Integer[n + 1];
        for (int i = 0; i < n + 1; i++) {
            v[i] = i;
            g[i] = s.charAt(i);
        }
        for (int h = 1; b[n] != n; h *= 2) {
            SAComp comp = new SAComp(h, g);
            sort(v, comp);
            for (int i = 0; i < n; i++) {
                b[i + 1] = b[i];
                if (comp.compare(v[i], v[i + 1]) < 0) {
                    b[i + 1]++;
                }
            }
            for (int i = 0; i < n + 1; i++) {
                g[v[i]] = b[i];
            }
        }
        int[] res = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            res[i] = v[i];
        }
        return res;
    }

    /**
     * 計算されたSA と対応するs から,LCP(longest common prefix)配列をつくる.
     * LCP[i] は, s[SA[i]..], s[SA[i]+1..] の最大の共通prefix の長さ.
     * O(n).
     *
     * @param SA
     * @param s
     * @return
     */
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
}
