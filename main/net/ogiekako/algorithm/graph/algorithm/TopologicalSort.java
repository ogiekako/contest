package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;

/**
 * Perform topological sort.
 */
public class TopologicalSort {
    final Graph graph;
    final int n;
    State[] states;
    int[] res;
    int p;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
        n = graph.size();
        states = new State[n];
    }

    /**
     * Perform topological sort in O(n + m) time.
     * Returns null if there is a cycle.
     * Otherwise returns an array u such that for any edge (u[i], u[j]), i < j holds.
     * <p>
     * Verified: TCO13 2B 500
     * </p>
     */
    public int[] sortedOrder() {
        Arrays.fill(states, State.UNVISITED);
        res = new int[n];
        p = n;
        for (int i = 0; i < n; i++) {
            if (states[i] == State.VISITED) continue;
            if (dfs(i)) return null;
        }
        return res;
    }

    // Return true if a cycle was detected.
    private boolean dfs(int v) {
        if (states[v] == State.VISITING) return true;
        if (states[v] == State.VISITED) return false;
        states[v] = State.VISITING;
        for (Edge e : graph.edges(v)) {
            if(dfs(e.to()))return true;
        }
        res[--p] = v;
        states[v] = State.VISITED;
        return false;
    }

    enum State {
        UNVISITED, VISITING, VISITED;
    }
}
