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

    @Override
    public String toString() {
        StringBuilder es = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            if (i > 0) es.append('\n');
            es.append(edges(i));
        }
        return "UndirectedGraph{" +
                "edges=" + es +
                '}';
    }
}
