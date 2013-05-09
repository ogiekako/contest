package net.ogiekako.algorithm.graph;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/11
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
public class BidirectionalGraph extends Graph {
    public BidirectionalGraph(int vertexCount) {
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
