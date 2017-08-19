package on2017_08.on2017_08_18_TopCoder_Open_Round__3B.CommutativeMapping;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class CommutativeMapping {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    boolean[] in;
    Graph rev;
    long[][] memo;
    int MOD = (int) (1e9 + 7);

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
        int[] count = new int[n];
        int[] rep = new int[n];
        boolean[] visited = new boolean[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (in[i] && !visited[i]) {
                rep[m] = i;
                count[m] = 0;
                int v = i;
                do {
                    v = f[v];
                    count[m]++;
                    visited[v] = true;
                } while (v != i);
                m++;
            }
        }
        count = Arrays.copyOf(count, m);
        rep = Arrays.copyOf(rep, m);
        long res = 1;
        for (int i = 0; i < m; i++) {
            long val = 0;
            for (int j = 0; j < m; j++) {
                if (count[i] % count[j] != 0) continue;
                int start = rep[j];
                for (int k = 0; k < count[j]; k++) {
                    start = f[start];

                    long val2 = 1;
                    int x = start;
                    int y = rep[i];
                    for (int l = 0; l < count[i]; l++) {
                        val2 = val2 * recur(y, x) % MOD;
                        x = f[x];
                        y = f[y];
                    }
                    val += val2;
                    if (val >= MOD) val -= MOD;
                }
            }
            res = res * val % MOD;
        }
        return (int) res;
    }

    private long recur(int from, int to) {
        if (memo[from][to] >= 0) return memo[from][to];
        long res = 1;
        for (Edge e1 : rev.edges(from)) {
            if (in[e1.to()]) continue;
            long val = 0;
            for (Edge e2 : rev.edges(to)) {
                val += recur(e1.to(), e2.to());
                if (val >= MOD) val -= MOD;
            }
            res = res * val % MOD;
        }
        memo[from][to] = res;
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(12984719274L);
        int n = 5;
        for (int iter = 0; iter < 100000; iter++) {
            int[] f = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = rnd.nextInt(n);
                int res = new CommutativeMapping().count(f);
                int exp = 0;
                long power = MathUtils.power(n, n);
                for (int j = 0; j < power; j++) {
                    int[] g = new int[n];
                    int tmp = j;
                    for (int k = 0; k < n; k++) {
                        g[k] = tmp % n;
                        tmp /= n;
                    }
                    boolean ok = true;
                    for (int k = 0; k < n; k++) {
                        if(g[f[k]] != f[g[k]]) ok = false;
                    }
                    if (ok) exp++;
                }
                if (exp != res) {
                    debug(iter, f, exp, res);
                    throw new RuntimeException();
                }
            }
        }
    }
}
