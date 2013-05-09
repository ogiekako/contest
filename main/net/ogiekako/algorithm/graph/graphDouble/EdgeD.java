package net.ogiekako.algorithm.graph.graphDouble;

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
