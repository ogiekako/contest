package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.UndirectedGraph;

/**
 * Gomory hu Tree
 * <p/>
 * Ref: <a href="http://apps.topcoder.com/forums/?module=Thread&threadID=637516&start=0&mc=10#1084800">TopCoder Forum</a>
 * <a href="http://www.prefield.com/algorithm/graph/gomory-hu.html">Spaghetti Source</a>
 * <br></br>
 * Problems: <a href="http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=2641">All Pairs Maximum Flow</a> - unsolved
 */
public class MinimumCutTree {
    UndirectedGraph graph;

    MinimumCutTree(UndirectedGraph graph) {
        this.graph = graph;
    }

    public static UndirectedGraph minCutTree(long[][] capacity) {
        int n = capacity.length;
        UndirectedGraph graph = new UndirectedGraph(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                if (capacity[i][j] != capacity[j][i])
                    throw new IllegalArgumentException(i + " " + j + " " + capacity[i][j] + " " + capacity[j][i]);
                if (capacity[i][j] > 0) graph.addFlow(i, j, capacity[i][j]);
            }
        return new MinimumCutTree(graph).minCutTree();
    }

    UndirectedGraph minCutTree() {
        int n = graph.size();
        int[] parent = new int[n];
        MaxFlow maxFlow = new MaxFlow(graph);
        UndirectedGraph tree = new UndirectedGraph(n);
        for (int u = 1; u < n; u++) {
            double capacity = maxFlow.maxFlow(u, parent[u]);
            tree.addFlow(u, parent[u], capacity);
            for (int v = u + 1; v < n; v++) if (maxFlow.sourceSide(v) && parent[v] == parent[u]) parent[v] = u;
            maxFlow.maxFlow(parent[u], u, capacity);
        }
        return tree;
    }
}
