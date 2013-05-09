package net.ogiekako.algorithm.graph.graphDouble;

/**
 * @author Egor Kulikov (kulikov@devexperts.com)
 */
public class FlowEdge extends SimpleEdge {
    // capacity = residue + flow.
    final double capacity;
    double residue;
    double flow = 0;
    EdgeD reverseEdge;

    public FlowEdge(int from, int to, double capacity) {
        super(from, to);
        this.capacity = this.residue = capacity;
        reverseEdge = new ReverseEdge();
    }

    @Override
    public long getWeight() {
        return 0;
    }

    @Override
    public double getResidue() {
        return residue;
    }

    @Override
    public double getFlow() {
        return flow;
    }

    @Override
    public void pushFlow(double flow) {
        if (flow > 0) {
            if (this.residue < flow)
                throw new IllegalArgumentException();
        } else {
            if (this.flow < -flow)
                throw new IllegalArgumentException();
        }
        residue -= flow;
        this.flow += flow;
        if (residue == 0) this.flow = capacity;
    }

    @Override
    public EdgeD getTransposedEdge() {
        return new FlowEdge(to, from, capacity);
    }

    @Override
    public EdgeD getReverseEdge() {
        return reverseEdge;
    }

    private class ReverseEdge implements EdgeD {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public long getWeight() {
            return -FlowEdge.this.getWeight();
        }

        public double getResidue() {
            return flow;
        }

        public double getFlow() {
            return residue;
        }

        public void pushFlow(double flow) {
            FlowEdge.this.pushFlow(-flow);
        }

        public EdgeD getTransposedEdge() {
            throw new UnsupportedOperationException();
        }

        public EdgeD getReverseEdge() {
            return FlowEdge.this;
        }
    }
}