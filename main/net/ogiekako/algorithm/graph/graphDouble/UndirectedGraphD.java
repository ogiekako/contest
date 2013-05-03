package net.ogiekako.algorithm.graph.graphDouble;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/11
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
public class UndirectedGraphD extends GraphD {
    public UndirectedGraphD(int vertexCount) {
        super(vertexCount);
    }

    @Override
    public void remove(EdgeD edge) {
        super.remove(edge);
        super.remove(edge.getTransposedEdge());
    }

    @Override
    public void add(EdgeD edge) {
        super.add(edge);
        super.add(edge.getTransposedEdge());
    }
}
