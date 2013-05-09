package net.ogiekako.algorithm.graph.graphDouble;

import java.util.ArrayList;
import java.util.List;

public class GraphD {
    private int vertexCount;
    private EdgeList[] edges;

    public boolean isDigraph() {
        return true;
    }

    public void remove(EdgeD edge) {
        edges[edge.from()].remove(edge);
    }

    private static class EdgeList extends ArrayList<EdgeD> {
        EdgeList() {super(0);}
    }

    public GraphD(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new EdgeList[vertexCount];
        for (int i = 0; i < vertexCount; i++) edges[i] = new EdgeList();
    }

    public void add(EdgeD edge) {
        edges[edge.from()].add(edge);
        EdgeD rev = edge.getReverseEdge();
        if (rev != null) {
            edges[rev.from()].add(rev);
        }
    }

    public List<EdgeD> getEdges(int vertex) {
        return edges[vertex];
    }

    public GraphD getTransposedGraph() {
        GraphD res = new GraphD(vertexCount);
        for (int i = 0; i < vertexCount; i++)
            for (EdgeD e : edges[i]) {
                res.add(e.getTransposedEdge());
            }
        return res;
    }

    public int size() {
        return vertexCount;
    }
}
