package net.ogiekako.algorithm.graph.flow;
import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.ArrayUtils;
/**
 * Created with IntelliJ IDEA.
 * User: ogiekako
 * Date: 13/04/28
 * Time: 9:17
 * To change this template use File | Settings | File Templates.
 */
public class GlobalMinimumCut {
    BidirectionalGraph graph;
    public GlobalMinimumCut(BidirectionalGraph graph) {
        this.graph = graph;
    }

    /**
     * O(n^3).
     * <p/>
     * Solved: <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1930">Bomb, Divide and Conquer</a>
     * <p/>
     * Ref: http://www.prefield.com/algorithm/graph/minimum_cut.html
     *
     * @return
     */
    public long compute() {
        int n = graph.size();
        long[][] h = new long[n][n];
        for (int u = 0; u < n; u++)
            for (Edge e : graph.edges(u)) {
                h[u][e.to()] += e.flow();
            }
        int[] V = ArrayUtils.createOrder(n);
        long cut = Long.MAX_VALUE;
        for (int m = n; m > 1; m--) {
            long[] ws = new long[m];
            int u = -1, v = -1;
            long w = -1;
            for (int k = 0; k < m; k++) {
                u = v; v = ArrayUtils.maxIndex(ws);
                w = ws[v]; ws[v] = -1;
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
    public static long globalMinCut(BidirectionalGraph graph) {
        return new GlobalMinimumCut(graph).compute();
    }
}
