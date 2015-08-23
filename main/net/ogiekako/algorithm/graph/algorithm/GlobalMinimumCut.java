package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.utils.ArrayUtils;

/**
 * Computes the global minimum cut value of an undirected graph.
 */
public class GlobalMinimumCut {
    UndirectedGraph graph;

    public GlobalMinimumCut(UndirectedGraph graph) {
        this.graph = graph;
    }

    /**
     * O(n^3).
     * <p/>
     * Verified: <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1930">Bomb, Divide and Conquer</a>
     * <p/>
     * Referred to: http://www.prefield.com/algorithm/graph/minimum_cut.html
     *
     * @return The size of the global minimum cut.
     */
    public double compute() {
        int n = graph.size();
        double[][] h = new double[n][n];
        for (int u = 0; u < n; u++)
            for (Edge e : graph.edges(u)) {
                h[u][e.to()] += e.flow();
            }
        int[] V = ArrayUtils.createOrder(n);
        double cut = Double.POSITIVE_INFINITY;
        for (int m = n; m > 1; m--) {
            double[] ws = new double[m];
            int u = -1, v = -1;
            double w = -1;
            for (int k = 0; k < m; k++) {
                u = v;
                v = ArrayUtils.maxIndex(ws);
                w = ws[v];
                ws[v] = -1;
                for (int i = 0; i < m; i++) if (ws[i] >= 0) ws[i] += h[V[v]][V[i]];
            }
            for (int i = 0; i < m; i++) {
                h[V[i]][V[u]] += h[V[i]][V[v]];
                h[V[u]][V[i]] += h[V[v]][V[i]];
            }
            V = ArrayUtils.removeIndex(V, v);
            cut = Math.min(cut, w);
        }
        return cut;
    }
}
