package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.UndirectedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinimumSpanningForest {
    final Graph graph;
    final int n;

    /**
     * Given graph should represents an undirected graph.
     */
    public MinimumSpanningForest(Graph graph) {
        this.graph = graph;
        n = graph.size();
    }

    Comparator<Edge> comparator = new Comparator<Edge>() {
        @Override
        public int compare(Edge o1, Edge o2) {
            return Double.compare(o1.cost(), o2.cost());
        }
    };

    /**
     * Returns an undirected graph representing a minimum spanning tree of the given graph.
     * <pre>
     * Verified:
     *   AOJ Spannig Tree http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_2_A
     * </pre>
     */
    public UndirectedGraph compute() {
        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            for (Edge e : graph.edges(i)) {
                if (e.to() < i) continue;
                edges.add(e);
            }
        }
        Collections.sort(edges, comparator);

        UndirectedGraph res = new UndirectedGraph(n);
        UnionFind uf = new UnionFind(n);
        for (Edge e : edges) {
            int x = e.from(), y = e.to();
            if (uf.find(x, y)) continue;
            uf.union(x, y);
            res.add(e);
        }
        return res;
    }
}
