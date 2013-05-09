package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.dataStructure.intCollection.IntQueue;
import net.ogiekako.algorithm.utils.Cast;

import java.util.Arrays;

public class Hungarian {
    long[][] cost;
    int N;
    int[] Rof, Lof;
    long[] potL, potR;
    Hungarian(long[][] cost) {
        this.cost = cost;
    }
    /**
     * 2部グラフの最大重み完全マッチングを求める.
     * O(n^3).
     * from Spaghetti source. Thanks to maehara-san.
     * <p/>
     * Ref: <a href=""></a>
     *
     * @param cost
     * @return
     */
    public static int maximumPerfectMatching(int[][] cost) {// PKU2195
        int n = cost.length;
        int[][] nCost = new int[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) nCost[i][j] = -cost[i][j];
//        return -minimumPerfectMatching(cost);
        return (int) maximumPerfectMatching(Cast.toLong(cost));
    }

    /**
     * U * V.
     *
     * @return
     */
    int minimumPerfectMatching() {
        N = cost.length;
        Rof = new int[N]; Arrays.fill(Rof, -1);
        Lof = new int[N]; Arrays.fill(Lof, -1);
        potL = new long[N]; potR = new long[N];
        Arrays.fill(potR, Long.MAX_VALUE);
        for (int l = 0; l < N; ++l) for (int r = 0; r < N; ++r) potR[r] = Math.min(potR[r], cost[l][r]);
        for (int flow = 0; flow < N; ) {
            boolean[] visL = new boolean[N], visR = new boolean[N];
            int[] prevL = new int[N], prevR = new int[N];
            IntQueue queL = new IntQueue(), queR = new IntQueue();
            for (int l = 0; l < N; l++)
                if (Rof[l] == -1) {
                    queL.offer(l);
                    visL[l] = true;
                }
            while (!queL.isEmpty()) {
                while (!queL.isEmpty()) {
                    int l = queL.poll();
                    for (int r = 0; r < N; r++)
                        if (Lof[l] != r && cost(l, r) == 0 && !visR[r]) {
                            queR.offer(r);
                            visR[r] = true;
                            prevR[r] = l;
                        }
                }
                while (!queR.isEmpty()) {
                    int r = queR.poll();
                    for (int l = 0; l < N; l++)
                        if (Rof[r] == l && cost(l, r) == 0 && !visL[l]) {
                            queL.offer(l);
                            visL[l] = true;
                            prevL[l] = r;
                        }
                }
            }
            boolean augmented = false;
            for (int r = 0; r < N; r++)
                if (visR[r] && Lof[r] == -1) {
                    augmented = true;
                    int nR = r;
                    int nL = prevR[nR];

                    break;
                }
        }
        int ret = 0;
        for (int l = 0; l < N; ++l) ret += cost[l][Rof[l]];
        return ret;
    }

    long cost(int l, int r) {
        return potL[l] + cost[l][r] - potR[r];
    }

    public static int minimumCostMatching(long[][] cost) {
        if (cost.length == 0) return 0;
        int n = cost.length, m = cost[0].length;
        return 0;
    }

    public static long maximumPerfectMatching(long[][] a) {// PKU2195
        int n = a.length;
        long[] fx = new long[n], fy = new long[n];
        Arrays.fill(fx, Long.MIN_VALUE);
        int[] x = new int[n], y = new int[n];
        Arrays.fill(x, -1);
        Arrays.fill(y, -1);
        for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) fx[i] = Math.max(fx[i], a[i][j]);
        for (int i = 0; i < n; ) {
            int[] t = new int[n], s = new int[n + 1];
            Arrays.fill(t, -1);
            Arrays.fill(s, i);
            int q = 0;
            for (int p = 0; p <= q && x[i] < 0; ++p)
                for (int k = s[p], j = 0; j < n && x[i] < 0; ++j)
                    if (fx[k] + fy[j] == a[k][j] && t[j] < 0) {
                        s[++q] = y[j];
                        t[j] = k;
                        if (s[q] < 0) for (p = j; p >= 0; j = p) {
                            y[j] = k = t[j];
                            p = x[k];
                            x[k] = j;
                        }
                    }
            if (x[i] < 0) { // no augment path. Increase potential and make zero edge.
                long d = Long.MAX_VALUE / 2;
                for (int k = 0; k <= q; ++k)
                    for (int j = 0; j < n; ++j) if (t[j] < 0) d = Math.min(d, fx[s[k]] + fy[j] - a[s[k]][j]);
                for (int j = 0; j < n; ++j) fy[j] += (t[j] < 0 ? 0 : d);
                for (int k = 0; k <= q; ++k) fx[s[k]] -= d;
            } else ++i;
        }
        long ret = 0;
        for (int i = 0; i < n; ++i) ret += a[i][x[i]];
        return ret;
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
