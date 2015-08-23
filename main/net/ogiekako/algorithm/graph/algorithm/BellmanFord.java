package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;

public class BellmanFord {
    Graph graph;
    double[] distance;
    int n;

    public BellmanFord(Graph graph) {
        this.graph = graph;
        n = graph.size();
        distance = new double[n];
    }

    /**
     * Compute minimum distance from the source.
     * If there is a negative cycle <strong>reachable</strong> from the source, returns null.
     * Otherwise, returns an array res where
     * res[i] = distance from source to i or Double.POSITIVE_INFINITY if i is unreachable from source.
     * <p/>
     * O(VE)
     * <p/>
     * verified: http://utpc2013.contest.atcoder.jp/tasks/utpc2013_08 778ms
     */
    public double[] sssp(int source) {
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        distance[source] = 0;
        return bellmanFord();
    }

    private double[] bellmanFord() {
        boolean[] updated = new boolean[n];
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            updated[i] = distance[i] < Double.POSITIVE_INFINITY;
            parent[i] = i;
        }

        // If there is no negative cycle, there is a shortest-path tree rooted at some vertex.
        // This algorithm iteratively relaxes edges in the shortest-path tree from the root.
        // Since there is no more than n-1 edges in the shortest-path tree, n-th iteration should produce
        // no update. If n-th iteration has update, that means there is a negative cycle reachable from the source(s).
        for (int iter = 0; iter < n; iter++) {
            boolean[] nUpdated = new boolean[n];
            boolean hasUpdate = false;
            for (int v = 0; v < n; v++) {
                if (!updated[v]) continue;

                for (Edge e : graph.edges(v)) {
                    int u = e.to();
                    if (e.residue() <= 0) continue;

                    // Add EPS not to interpret a zero-cycle as a negative cycle.
                    if (distance[u] > distance[v] + e.cost() + 1e-9) {
                        distance[u] = distance[v] + e.cost();
                        nUpdated[u] = true;
                        hasUpdate = true;

                        // A heuristic for speed up.
                        if (parent[v] == u) return null;
                        parent[u] = parent[v];
                    }
                }
            }

            if (!hasUpdate) return distance;
            updated = nUpdated;
        }
        return null;
    }

    /**
     * Returns whether the graph has a negative cycle or not.
     * <p>
     * O(VE)
     * </p>
     * <p>
     * Verified: http://utpc2013.contest.atcoder.jp/tasks/utpc2013_08 592ms
     * </p>
     */
    public boolean hasNegativeCycle() {
        Arrays.fill(distance, 0);
        return bellmanFord() == null;
    }
}
