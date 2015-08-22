package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    Graph graph;
    boolean[] visited;
    int n;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        n = graph.size();
        visited = new boolean[n];
    }

    /**
     * res[i][i] = 0.
     * O(nm log m)
     * <p>
     *     Verified: Test for minVertexToTrip010
     * </p>
     */
    public static double[][] allPairsShortestPath(Graph graph) {
        int n = graph.size();
        double[][] res = new double[n][];
        Dijkstra dijkstra = new Dijkstra(graph);
        for (int i = 0; i < n; i++) {
            res[i] = dijkstra.sssp(i);
        }
        return res;
    }

    /**
     * Compute SSSP (single source shorted path) from the given source.
     * res[v] is Double.POSITIVE_INFINITY if there is no path from source to v.
     * Otherwise res[v] is the distance from the source to v.
     * The graph shouldn't contain any negative-weighted edge.
     * <p>
     * Referred to: http://www.columbia.edu/~cs2035/courses/ieor6614.S12/sp.pdf
     * </p>
     * <p>
     * Verified:
     * - TCO 14 R2C 500 (unweighted)
     * - AOJ Single Source Shortest Path http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_1_A
     * </p>
     */
    public double[] sssp(int source) {
        Arrays.fill(visited, false);
        double[] distance = new double[n];
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        distance[source] = 0;

        PriorityQueue<E> que = new PriorityQueue<E>();
        que.offer(new E(0.0, source));
        while (!que.isEmpty()) {
            E entry = que.poll();
            double dist = entry.dist;
            int v = entry.v;
            if (visited[v]) continue;
            visited[v] = true;
            for (Edge e : graph.edges(v)) {
                int u = e.to();
                if (distance[u] > dist + e.cost()) {
                    distance[u] = dist + e.cost();
                    que.offer(new E(distance[u], u));
                }
            }
        }
        return distance;
    }

    static class E implements Comparable<E> {
        double dist;
        int v;

        @Override
        public int compareTo(E o) {
            return Double.compare(dist, o.dist);
        }

        public E(double dist, int v) {
            this.dist = dist;
            this.v = v;
        }
    }

}
