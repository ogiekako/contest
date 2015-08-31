package net.ogiekako.algorithm.graph.problems;

import net.ogiekako.algorithm.graph.*;
import net.ogiekako.algorithm.graph.algorithm.Dijkstra;

public class Minimize {
    /*
    0 -> 1 -> 0 と旅をする時に, 通らなければならない頂点数の最小を返す.
    (複数回通っても, 1回カウントする.)

    COCI 2011 07 Kampanja
     */
    public static int minVertexToTrip010(boolean[][] nei) {
        int n = nei.length;
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (nei[i][j]) graph.add(new SimpleEdge(i, j));
        Graph rev = GraphUtils.transposed(graph);
        double[][] dist = new Dijkstra(graph).allPairsShortestPath();
        Graph graph2 = new Graph(n * n);
        for (int t = 0; t < n; t++)
            for (int r = 0; r < n; r++) {
                for (Edge e : graph.edges(t)) {
                    long weight = e.to() == r ? 0 : 1;
                    graph2.add(new WeightedEdge(t * n + r, e.to() * n + r, weight));
                }
                for (Edge e : rev.edges(r)) {
                    long weight = e.to() == t ? 0 : 1;
                    graph2.add(new WeightedEdge(t * n + r, t * n + e.to(), weight));
                }
                if (r != t && dist[t][r] < Double.POSITIVE_INFINITY) {
                    graph2.add(new WeightedEdge(t * n + r, r * n + t, dist[t][r] - 1));
                }
            }
        return (int) new Dijkstra(graph2).sssp(0)[n + 1] + 1;
    }
}
