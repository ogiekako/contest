package net.ogiekako.algorithm.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    final int vertexCount;
    private EdgeList[] edges;

    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new EdgeList[vertexCount];
        for (int i = 0; i < vertexCount; i++) edges[i] = new EdgeList();
    }

    public static Graph of(List<Integer>[] graph) {
        Graph res = new Graph(graph.length);
        for (int i = 0; i < res.size(); i++)
            for (int j : graph[i]) {
                res.add(i, j);
            }
        return res;
    }

    public boolean isDigraph() {
        return true;
    }

    public void remove(Edge edge) {
        edges[edge.from()].remove(edge);
        Edge rev = edge.reversed();
        if (rev != null) {
            edges[rev.from()].remove(rev);
        }
    }

    public void addWeighted(int from, int to, double cost) {
        add(new WeightedEdge(from, to, cost));
    }

    public void addFlow(int from, int to, double capacity) {
        add(new FlowEdge(from, to, capacity));
    }

    public Edge addFlow(int from, int to, double capacity, double cost) {
        FlowEdge edge = new FlowEdge(from, to, capacity, cost);
        add(edge);
        return edge;
    }

    public void add(Edge edge) {
        edges[edge.from()].add(edge);
        Edge rev = edge.reversed();
        if (rev != null) {
            edges[rev.from()].add(rev);
        }
    }

    public void add(int from, int to) {
        add(new SimpleEdge(from, to));
    }

    public List<Edge> edges(int vertex) {
        return edges[vertex];
    }

    public int size() {
        return vertexCount;
    }

    @Override
    public String toString() {
        StringBuilder es = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            if (i > 0) es.append('\n');
            es.append(edges(i));
        }
        return "Graph{" +
                "edges=" + es +
                '}';
    }

    /**
     * Returns if this graph contains the given edge in O(degree) time.
     */
    public boolean contains(Edge e) {
        return edges[e.from()].contains(e);
    }

    private static class EdgeList extends ArrayList<Edge> {
        EdgeList() {
            super(0);
        }
    }
}
