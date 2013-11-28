package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TaskD {
    long[][] dp = new long[50010][510];
    int[] parent = new int[50010];
    int[] id = new int[50010];
    int[] di = new int[50010];
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int K = in.nextInt();
        V[] vs = new V[n];
        for (int i = 0; i < n; i++) vs[i] = new V();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            vs[a].add(vs[b]);
            vs[b].add(vs[a]);
        }
        long res = countPairs(vs, K);
        out.println(res);
    }

    /**
     * VK Cup 2012 round 1 D.
     * O(n * distance)
     *
     * @param tree
     * @param distance
     * @return
     */
    private long countPairs(V[] tree, int distance) {
        return count(tree[0], distance);
    }
    long[] num = new long[1000];

    // vを根とした木で,
    long count(V v, int k) {
        size(v, null);
        // 重心
        v = choose(v, null, v.size);
        v.used = true;
        long res = 0;
        for (V u : v)
            if (!u.used) {
                res += count(u, k);
            }
        v.used = false;
        dist(v, null, 0, 1);
        for (V u : v)
            if (!u.used) {
                dist(u, v, 1, -1);
                res += count(u, v, k - 1);
            }
        num[0] = 0;
        return res;
    }

    long count(V v, V p, int k) {
        if (k < 0) return 0;
        long res = num[k];
        for (V u : v) if (!u.used && u != p) res += count(u, v, k - 1);
        return res;
    }

    void dist(V v, V p, int d, int add) {
        if (d >= num.length) return;
        num[d] += add;
        for (V u : v) if (!u.used && u != p) dist(u, v, d + 1, add);
    }

    /**
     * vを根とする部分木を考え,そのサイズを設定する.
     *
     * @param v
     * @param p
     * @return
     */
    int size(V v, V p) {
        v.size = 1;
        for (V u : v) if (!u.used && u != p) v.size += size(u, v);
        return v.size;
    }

    /**
     * vを根とする木に関して,重心を選ぶ.
     * そこで分割した時に,size/2 以下になるような点.
     *
     * @param v
     * @param p
     * @param n
     * @return
     */
    V choose(V v, V p, int n) {
        for (V u : v) if (!u.used && u != p && u.size * 2 > n) return choose(u, v, n);
        return v;
    }

    class V extends ArrayList<V> {
        boolean used;
        int size;
    }

    public long solve(int n, int K, ArrayList<Integer>[] es) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(0);
        parent[0] = -1;
        Arrays.fill(di, -1);
        di[0] = 0;
        int p = 1;
        while (!que.isEmpty()) {
            int cur = que.poll();
            for (int j : es[cur])
                if (di[j] == -1) {
                    id[p] = j;
                    di[j] = p++;
                    parent[di[j]] = di[cur];
                    que.offer(j);
                }
        }

        long res = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i][0] = 1;
            for (int j : es[id[i]]) {
                int to = di[j];
                if (i < to) {
                    for (int l = 0; l <= K - 1; l++) {
                        res += dp[i][l] * dp[to][K - 1 - l];
                    }
                    for (int l = 0; l < K - 1; l++) {
                        dp[i][l + 1] += dp[to][l];
                    }
                }
            }
        }
        return res;
    }
}
