package net.ogiekako.algorithm.graph.graphDouble;

/**
* Created by IntelliJ IDEA.
* User: ogiekako
* Date: 12/05/01
* Time: 2:56
* To change this template use File | Settings | File Templates.
*/
public interface EdgeD {
    int from();

    int to();

    long getWeight();

    double getResidue();

    double getFlow();

    void pushFlow(double flow);

    EdgeD getTransposedEdge();

    EdgeD getReverseEdge();
}
