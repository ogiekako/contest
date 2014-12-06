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

    double cost();

    void setCost(double cost);

    double residue();

    double flow();

    void pushFlow(double flow);

    /**
     * @return The edge simply its from and to are transposed.
     * It is not nullable.
     */
    Edge transposed();

    /**
     * @return The reverse edge if this edge is a flow edge.
     * It returns null otherwise.
     */
    Edge reversed();
}
