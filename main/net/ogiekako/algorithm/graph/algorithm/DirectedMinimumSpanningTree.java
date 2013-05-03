package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;

public class DirectedMinimumSpanningTree {
    /**
     * compute the weight of the directed minimum spanning tree whose root is 'root'.
     */
    long compute(Graph graph, int root){
        for(long res = 0;;){
            int n = graph.size();
            long[] minInEdge = new long[n];
            Arrays.fill(minInEdge, Long.MAX_VALUE);
            for (int v = 0; v < n; v++)
                for (Edge e : graph.edges(v)) if (e.to() != v) minInEdge[e.to()] = Math.min(minInEdge[e.to()], e.cost());

            for (int i = 0; i < n; i++)
                if (i != root) {
                    if (minInEdge[i] == Long.MAX_VALUE) return Long.MAX_VALUE;
                    res += minInEdge[i];// choose minimum in edge.
                }

            Graph graphScc = new Graph(n);
            for (int v = 0; v < n; v++)
                for (Edge e : graph.edges(v))
                    if (e.to() != root) {
                        e.setCost(e.cost() - minInEdge[e.to()]);
                        if (e.cost() == 0) {
                            graphScc.add(e);
                        }
                    }

            int[] comp = SCC.scc(graphScc);
            int numComp = 0;
            for (int i : comp) numComp = Math.max(numComp, i + 1);
            if (numComp == graph.size()) return res;
            Graph nGraph = new Graph(numComp);
            for (int v = 0; v < n; v++)
                for (Edge e : graph.edges(v)) if (comp[v] != comp[e.to()]) nGraph.addWeighted(comp[v], comp[e.to()], e.cost());

            graph = nGraph;
            root = comp[root];
        }
    }
}

