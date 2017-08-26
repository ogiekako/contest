package on2017_08.on2017_08_20_TopCoder_SRM__719.OwaskiAndTree;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class OwaskiAndTree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    int[] parent;
    int[] pleasure;
    int[][] memo; // 0: maybe visited, 1: unvisited
    Graph graph;
    public int maximalScore(int[] parent, int[] pleasure) {
        n = parent.length + 1;
        this.parent = parent;
        this.pleasure = pleasure;
        memo = new int[n][2];
        ArrayUtils.fill(memo, -1);
        graph = new Graph(n);
        for (int i = 0; i < n - 1; i++) {
            graph.add(parent[i], i + 1);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            int val = 0;
            val += recur(i, 1);
            int j = i;
            while (j > 0) {
                int prev = j;
                j = parent[j - 1];
                for (Edge e : graph.edges(j)) {
                    if (e.to() == prev) continue;
                    val += recur(e.to(), 0);
                }
            }
            res = Math.max(res, val);
        }
        return res;
    }

    private int recur(int v, int flag) {
        if (memo[v][flag] >= 0) return memo[v][flag];
        int res;
        if (flag == 1) {
            res = pleasure[v];
            for (Edge e : graph.edges(v)) {
                res += recur(e.to(), flag);
            }
        } else {
            res = recur(v, 1);
            int res2 = 0;
            for (Edge e : graph.edges(v)) {
                res2 += recur(e.to(), 0);
            }
            res = Math.max(res, res2);
        }
        return memo[v][flag] = Math.max(0, res);
    }
}
