package net.ogiekako.algorithm.graph.algorithm;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
public class ShortestPath {
    Graph graph;
    public int[] parent;
    long[] distance;
    boolean[] visited;
    int n;
    int source;
    ShortestPath(Graph graph) {
        this.graph = graph;
        n = graph.size();
        parent = new int[n];
        distance = new long[n];
        visited = new boolean[n];
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
     */
    long[] compute(int source) {
        this.source = source;
        for (int i = 0; i < n; i++) for (Edge e : graph.edges(i)) if (e.cost() < 0) return bellmanFord();
        return dijkstra();
    }

    /**
     * Compute minimum distance from source.
     * O(VE)
     */
    long[] bellmanFord() {
        init();
        visited[source] = true;
        // If there is no negative cycle, every shortest path must be relaxed in order.
        // Thus, iteration stops after at most n-1 iterations.
        for (int iter = 0; iter < n; iter++) {
            boolean updated = false;
            for (int v = 0; v < n; v++)
                for (Edge e : graph.edges(v)) {
                    if (e.cost() < 0 || distance[v] < Long.MAX_VALUE - e.cost())
                        if (distance[e.to()] > distance[v] + e.cost()) {
                            relax(e);
                            if (visited[v]) visited[e.to()] = true;
                            updated = true;
                        }
                }
            if (!updated) {
                for (int v = 0; v < n; v++)
                    if (!visited[v]) {
                        distance[v] = Long.MAX_VALUE;
                        parent[v] = -1;
                    }
                return distance;
            }
        }
        return null;
    }

    /**
     * O( (E+V)logE )
     */
    long[] dijkstra() {
        init();
        Queue<Pair<Long, Integer>> que = new PriorityQueue<Pair<Long, Integer>>();
        que.offer(Pair.of(0L, source));
        while (!que.isEmpty()) {
            Pair<Long, Integer> entry = que.poll();
            long dist = entry.first;
            int v = entry.second;
            if (visited[v]) continue;
            visited[v] = true;
            for (Edge e : graph.edges(v))
                if (distance[e.to()] > dist + e.cost()) {
                    relax(e);
                    que.offer(Pair.of(distance[e.to()], e.to()));
                }
        }
        return distance;
    }

    private void init() {
        Arrays.fill(parent, -1);
        Arrays.fill(distance, Long.MAX_VALUE);
        Arrays.fill(visited, false);
        distance[source] = 0;
    }

    private void relax(Edge e) {
        int v = e.from(), u = e.to();
        distance[u] = distance[v] + e.cost();
        parent[u] = v;
    }


    ////////// Unitities //////////
    /*
        O(m log m)
         */
    public static long[] singleSourceShortestPath(Graph graph, int from) {
        return new ShortestPath(graph).compute(from);
    }
    /*
        res[i][i] = 0.
        O(nm log m)
        */
    public static long[][] allPairsShortestPath(Graph graph) {
        int n = graph.size();
        long[][] res = new long[n][];
        for (int i = 0; i < n; i++) {
            res[i] = singleSourceShortestPath(graph, i);
        }
        return res;
    }

}
