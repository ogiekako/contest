package net.ogiekako.algorithm.graph.graphDouble.problems;

import net.ogiekako.algorithm.graph.graphDouble.*;
import net.ogiekako.algorithm.utils.ArrayUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 3:03
 * To change this template use File | Settings | File Templates.
 */
public class Minimize {
    /*
    0 -> 1 -> 0 と旅をする時に, 通らなければならない頂点数の最小を返す.
    (複数回通っても, 1回カウントする.)

    COCI 2011 07 Kampanja
     */
    public static int minVertexToTrip010(boolean[][] nei) {
        int n = nei.length;
        GraphD graph = new GraphD(n);
        for (int i = 0; i < n; i++)for (int j = 0; j < n; j++)if (nei[i][j]) graph.add(new SimpleEdge(i, j));
        GraphD rev = graph.getTransposedGraph();
        long[][] dp = new long[n][n];// from, to
        ArrayUtils.fill(dp, Long.MAX_VALUE / 2);
        long[][] dist = GraphAlgorithm.minDistanceDijkstra(graph);
        GraphD graph2 = new GraphD(n * n);
        for (int t = 0; t < n; t++) for (int r = 0; r < n; r++) {
            for (EdgeD e : graph.getEdges(t)) {
                long weight = e.to() == r ? 0 : 1;
                graph2.add(new WeightedEdge(t*n+r, e.to()*n+r, weight));
            }
            for (EdgeD e : rev.getEdges(r)) {
                long weight = e.to() == t ? 0 : 1;
                graph2.add(new WeightedEdge(t*n+r, t*n+e.to(), weight));
            }
            if (r != t) {
                graph2.add(new WeightedEdge(t*n+r, r*n+t, dist[t][r] - 1));
            }
        }
        return (int) GraphAlgorithm.dijkstra(graph2, 0)[n + 1] + 1;
    }
}
