package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.*;

public class EulerPath {

    public List<Edge> directedEulerPath(Graph graph) {
        return directedEulerPath(graph, -1);
    }

    public List<Edge> directedEulerPath(Graph graph, int source) {
        int n = graph.size();
        int[] out = new int[n];
        for (int v = 0; v < n; v++) {
            List<Edge> es = graph.edges(v);
            out[v] += es.size();
            for (Edge e : es) out[e.to()]--;
        }
        for (int v = 0; v < n; v++) {
            if (out[v] > 1) return null;
            if (out[v] == 1) {
                if (source < 0) source = v;
                if (source != v) return null;
            }
        }
        if (source == -1) source = 0;
        return new DFS<List<Edge>>(graph) {
            List<Edge> result = new ArrayList<Edge>();

            @Override
            protected List<Edge> result() {
                for (boolean b : visited) if (!b) return null;
                Collections.reverse(result);
                return result;
            }

            @Override
            protected boolean enter(Edge e) {
                return true;
            }

            @Override
            protected void exit(Edge e) {
                if (e.from() >= 0)
                    result.add(e);
            }
        }.run(new int[]{source});
    }

    public List<Edge> undirectedEulerPath(UndirectedGraph graph) {
        return undirectedEulerPath(graph, -1);
    }

    private List<Edge> undirectedEulerPath(UndirectedGraph graph, int source) {
        int oddCount = 0;
        for (int v = 0; v < graph.size(); v++) {
            int deg = graph.edges(v).size();
            if (deg % 2 != 0) {
                oddCount++;
                if (source == -1) source = v;
            }
        }
        if (oddCount > 2) return null;
        if (source == -1) source = 0;

        return new DFS<List<Edge>>(graph) {
            List<Edge> result = new ArrayList<Edge>();
            Set<Edge> dejavu = new HashSet<Edge>();

            @Override
            protected List<Edge> result() {
                for (boolean b : visited) if (!b) return null;
                Collections.reverse(result);
                return result;
            }

            @Override
            protected void exit(Edge e) {
                if (e.from() >= 0) result.add(e);
            }

            @Override
            protected boolean enter(Edge e) {
                if (dejavu.contains(e)) return false;
                dejavu.add(e);
                dejavu.add(e.transposed());
                return true;
            }
        }.run(new int[]{source});
    }
}
