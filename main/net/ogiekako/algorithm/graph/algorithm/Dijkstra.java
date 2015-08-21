package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    Graph graph;
    double[] distance;
    boolean[] visited;
    int n;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        n = graph.size();
        distance = new double[n];
        visited = new boolean[n];
    }

    /**
     * res[i][i] = 0.
     * O(nm log m)
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
     * The graph shouldn't contain any negative-weighted edge.
     * <p/>
     * Referred: http://www.columbia.edu/~cs2035/courses/ieor6614.S12/sp.pdf
     *
     * @param source source
     * @return minimum distances from the source. If there is unreachable vertex, Double.POSITIVE_INFINITY is filled for
     * the vertex.
     * <p/>
     * <p>
     * Verified:
     * - TCO 14 R2C 500 (unweighted)
     * - AOJ Single Source Shorted Path http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_1_A
     * </p>
     */
    public double[] sssp(int source) {
        Arrays.fill(visited, false);
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
