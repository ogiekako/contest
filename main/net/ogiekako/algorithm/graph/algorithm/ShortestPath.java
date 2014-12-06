package net.ogiekako.algorithm.graph.algorithm;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
public class ShortestPath {
    public int[] parent;
    Graph graph;
    double[] distance;
    boolean[] visited;
    int n;
    int source;
    ShortestPath(Graph graph) {
        this.graph = graph;
        n = graph.size();
        parent = new int[n];
        distance = new double[n];
        visited = new boolean[n];
    }

    ////////// Unitities //////////
    /*
        O(m log m)
         */
    public static double[] singleSourceShortestPath(Graph graph, int from) {
        return new ShortestPath(graph).computeDistancesFrom(from);
    }

    /*
        res[i][i] = 0.
        O(nm log m)
        */
    public static double[][] allPairsShortestPath(Graph graph) {
        int n = graph.size();
        double[][] res = new double[n][];
        for (int i = 0; i < n; i++) {
            res[i] = singleSourceShortestPath(graph, i);
        }
        return res;
    }

    /**
     * After the computation parent[v] = parent of v in shortest-path tree.
     * This method does not modify the given graph.
     * <p/>
     * Ref: http://www.columbia.edu/~cs2035/courses/ieor6614.S12/sp.pdf
     *
     * @param source source
     * @return minimum distances of vertices from the source.
     *         If the graph contains a negative cycle, this method returns <code>null</code>.
     *         (Returns null even if there is no path to the negative cycle from the source vertex.)
     *         TODO(oka): Currently this algorithm doesn't return null if there is an unreachable negative cycle
     *         since the previous long weight was rewritten with double.
     */
    double[] computeDistancesFrom(int source) {
        this.source = source;
        for (int i = 0; i < n; i++) for (Edge e : graph.edges(i)) if (e.cost() < 0) return bellmanFord();
        return dijkstra();
    }

    /**
     * Compute minimum distance from the source.
     * res[i] = minimum distance or Double.POSITIVE_INFINITY if unreachable.
     * <p/>
     * O(VE)
     */
    double[] bellmanFord() {
        init();
        visited[source] = true;
        // If there is no negative cycle, every shortest path must be relaxed in order.
        // Thus, iteration stops after at most n-1 iterations.
        for (int iter = 0; iter < n; iter++) {
            boolean updated = false;
            for (int v = 0; v < n; v++)
                for (Edge e : graph.edges(v)) {
                    if (e.cost() < 0 || Double.isFinite(distance[v]))
                        if (distance[e.to()] > distance[v] + e.cost() + 1e-9) {
                            relax(e);
                            if (visited[v]) visited[e.to()] = true;
                            updated = true;
                        }
                }
            if (!updated) {
                for (int v = 0; v < n; v++)
                    if (!visited[v]) {
                        distance[v] = Double.POSITIVE_INFINITY;
                        parent[v] = -1;
                    }
                return distance;
            }
        }
        return null;
    }

    /**
     * O( (E+V)logE )
     * res[i] = minimum distance or Double.POSITIVE_INFINITY if unreachable.
     */
    double[] dijkstra() {
        init();
        Queue<Pair<Double, Integer>> que = new PriorityQueue<Pair<Double, Integer>>();
        que.offer(Pair.of(0.0, source));
        while (!que.isEmpty()) {
            Pair<Double, Integer> entry = que.poll();
            double dist = entry.first;
            int v = entry.second;
            if (visited[v]) continue;
            visited[v] = true;
            for (Edge e : graph.edges(v))
                if (e.residue() > 0)
                    if (distance[e.to()] > dist + e.cost()) {
                        relax(e);
                        que.offer(Pair.of(distance[e.to()], e.to()));
                    }
        }
        return distance;
    }

    private void init() {
        Arrays.fill(parent, -1);
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        Arrays.fill(visited, false);
        distance[source] = 0;
    }

    private void relax(Edge e) {
        int v = e.from(), u = e.to();
        distance[u] = distance[v] + e.cost();
        parent[u] = v;
    }

}
