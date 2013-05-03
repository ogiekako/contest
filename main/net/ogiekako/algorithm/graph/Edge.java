package net.ogiekako.algorithm.graph;


/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 2:56
 * To change this template use File | Settings | File Templates.
 */
public interface Edge {
    int from();

    int to();

    long cost();

    void setCost(long cost);

    long residue();

    long flow();

    void pushFlow(long flow);

    /**
     * @return The edge simply its from and to are transposed.
     *         It is not nullable.
     */
    Edge transposed();

    /**
     * @return The reverse edge in case this edge is a flow edge.
     *         It may throw UnsupportedOperationException if this edge is not a flow edge.
     */
    Edge reversed();
}
