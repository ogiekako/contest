package net.ogiekako.algorithm.misc.knapsack;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;
public class Knap {
    /**
     * 半分に分けるナップサック.
     * aの部分列で,合計がm以下で最大となる部分列の合計値を返す.
     * O(2^(n/2) * n).
     *
     * @param a
     * @param m
     * @return
     */
    public static long knapsack(long[] a, long m) {
        int n = a.length;
        int n1 = n / 2;
        long[] list1 = new long[1 << n1];
        for (int i = 0; i < list1.length; i++) {
            for (int j = 0; j < n1; j++) {
                if (((i >> j) & 1) == 1) {
                    list1[i] += a[j];
                }
            }
        }
        int n2 = n - n1;
        long[] list2 = new long[1 << n2];
        for (int i = 0; i < list2.length; i++) {
            for (int j = 0; j < n2; j++) {
                if (((i >> j) & 1) == 1) {
                    list2[i] += a[n1 + j];
                }
            }
        }
        sort(list1);
        sort(list2);
        return knap(m, list1, list2);
    }
    private static long knap(long m, long[] list1, long[] list2) {
        int n1 = list1.length, n2 = list2.length;
        long res = Long.MIN_VALUE;
        for (int i = 0, j = n2 - 1; ; ) {
            if (list1[i] + list2[j] <= m) {
                res = max(res, list1[i] + list2[j]);
                if (i + 1 < n1 && list1[i + 1] + list2[j] <= m)
                    i++;
                else if (j > 0)
                    j--;
                else
                    break;
            } else {
                if (j == 0)
                    break;
                j--;
            }
        }
        return res;
    }

    /**
     * 個数制約のあるナップサック.
     * 重さw[i],コストc[i]の荷物がu[i] 個ある
     * W = sum(w[i]u[i]), T = sum(log u[i]).
     * としたとき,O(WT) で,
     * 長さW+1の配列b であって,b[i] : ちょうど重さiにする時の最小コスト
     * なる配列を返す.
     *
     * @param w - 重さ配列
     * @param c - コスト配列
     * @param u - 個数配列
     * @return 長さW+1の配列b - b[i] : ちょうど重さiにする時の最小コスト. 不可能な場合は,Long.MAX_VALUE
     */
    public static long[] knapsack(int[] w, long[] c, int[] u) {
        assert w.length == c.length && c.length == u.length;
        int n = w.length;
        int W = 0;
        for (int i = 0; i < n; i++) W += w[i] * u[i];
        ArrayList<Integer> weights = new ArrayList<Integer>();
        ArrayList<Long> costs = new ArrayList<Long>();
        for (int i = 0; i < n; i++) {
            int uu = u[i];
            int b = 1;
            while (b <= uu) {
                uu -= b;
                weights.add(w[i] * b);
                costs.add(c[i] * b);
                b <<= 1;
            }
            if (uu > 0) {
                weights.add(w[i] * uu);
                costs.add(c[i] * uu);
            }
        }
        n = weights.size();
        long[][] dp = new long[2][W + 1];
        fill(dp[0], Long.MAX_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            int cur = i & 1, nxt = (i + 1) & 1;
            int ww = weights.get(i);
            long cc = costs.get(i);
            for (int j = 0; j < W + 1; j++)
                if (dp[cur][j] < Long.MAX_VALUE) {
                    dp[nxt][j] = min(dp[nxt][j], dp[cur][j]);
                    dp[nxt][j + ww] = min(dp[nxt][j + ww], dp[cur][i] + cc);
                }
        }
        return dp[n & 1];
    }

    public static void main(String[] args) {// m-found 10108

    }
}