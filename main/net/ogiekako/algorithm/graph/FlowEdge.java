package net.ogiekako.algorithm.graph;

public class FlowEdge extends SimpleEdge {
    // capacity = residue + flow.
    double residue;
    double flow = 0;
    Edge reversed;
    Edge transposed;

    public FlowEdge(int from, int to, double capacity) {
        super(from, to);
        if (capacity < 0) throw new IllegalArgumentException(from + " " + to + " " + capacity);
        this.residue = capacity;
    }

    public FlowEdge(int from, int to, double capacity, double cost) {
        this(from, to, capacity);
        this.cost = cost;
    }

    @Override
    public double residue() {
        return residue;
    }

    @Override
    public double flow() {
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
    }

    @Override
    public Edge transposed() {
        if (transposed == null) {
            transposed = new FlowEdge(to, from, residue + flow, cost) {
                @Override
                public Edge transposed() {
                    return FlowEdge.this;
                }
            };
        }
        return transposed;
    }

    @Override
    public Edge reversed() {
        if (reversed == null) reversed = new ReverseEdge();
        return reversed;
    }

    @Override
    public String toString() {
        return str(this);
    }

    private static String str(Edge e) {
        return String.format("%d->%d(%.2f/%.2f,%.2f)", e.from(), e.to(), e.flow(), e.flow() + e.residue(), e.cost());
    }

    private class ReverseEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public void setCost(double cost) {
            FlowEdge.this.cost = cost;
        }

        public double cost() {
            return -FlowEdge.this.cost();
        }

        public double residue() {
            return flow;
        }

        public double flow() {
            return residue;
        }

        public void pushFlow(double flow) {
            FlowEdge.this.pushFlow(-flow);
        }

        public Edge transposed() {
            return FlowEdge.this.transposed().reversed();
        }

        public Edge reversed() {
            return FlowEdge.this;
        }

        @Override
        public String toString() {
            return str(this);
        }
    }
}
