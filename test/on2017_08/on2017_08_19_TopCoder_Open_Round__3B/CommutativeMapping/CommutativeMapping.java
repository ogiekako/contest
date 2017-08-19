package on2017_08.on2017_08_19_TopCoder_Open_Round__3B.CommutativeMapping;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class CommutativeMapping {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    Graph rev;
    boolean[] in;
    long[][] memo;
    int MOD = (int) (1e9+7);
    public int count(int[] f) {
        n = f.length;
        memo = new long[n][n];
        ArrayUtils.fill(memo, -1);
        rev = new Graph(n);
        for (int i = 0; i < n; i++) {
            rev.add(f[i], i);
        }
        in = new boolean[n];
        for (int i = 0; i < n; i++) {
            int v = i;
            for (int j = 0; j < n; j++) {
                v = f[v];
            }
            in[v] = true;
        }
        long res = 1;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!in[i] || visited[i]) continue;
            int v = i;
            while(!visited[v]) {
                visited[v] = true;
                v = f[v];
            }
            long cur = 0;
            for (int j = 0; j < n; j++) {
                if (!in[j]) continue;
                int u = j;
                long w = 1;
                do {
                    w = w * recur(v, u) % MOD;
                    v = f[v];
                    u = f[u];
                } while (i != v);
                if (j == u) {
                    cur += w;
                }
            }
            cur %= MOD;
            res = res * cur % MOD;
        }
        return (int) res;
    }

    private long recur(int from, int to) {
        if (memo[from][to] >= 0) return memo[from][to];
        long res = 1;
        for (Edge e : rev.edges(from)) {
            if (in[e.to()]) continue;
            long cur = 0;
            for (Edge f : rev.edges(to)) {
                cur += recur(e.to(), f.to());
            }
            cur %= MOD;
            res = res * cur % MOD;
        }
        return memo[from][to] = res;
    }
}
