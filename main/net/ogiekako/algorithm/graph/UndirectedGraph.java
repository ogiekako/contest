package net.ogiekako.algorithm.graph;

/**
 * Undirected graph.
 */
public class UndirectedGraph extends Graph {
    public UndirectedGraph(int vertexCount) {
        super(vertexCount);
    }

    public boolean isDigraph() {
        return false;
    }

    @Override
    public void remove(Edge edge) {
        super.remove(edge);
        super.remove(edge.transposed());
    }

    @Override
    public void add(Edge edge) {
        super.add(edge);
        super.add(edge.transposed());
    }
}
