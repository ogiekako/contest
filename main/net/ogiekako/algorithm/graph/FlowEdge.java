package net.ogiekako.algorithm.graph;

public class FlowEdge extends SimpleEdge {
    // capacity = residue + flow.
    long residue;
    long flow = 0;
    Edge reversed;
    Edge transposed;

    public FlowEdge(int from, int to, long capacity) {
        super(from, to);
        this.residue = capacity;
    }

    public FlowEdge(int from, int to, long capacity, long cost) {
        this(from, to, capacity);
        this.cost = cost;
    }

    @Override
    public long residue() {
        return residue;
    }

    @Override
    public long flow() {
        return flow;
    }

    @Override
    public void pushFlow(long flow) {
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
            transposed = new FlowEdge(to, from, residue + flow) {
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
        return String.format("%d->%d(%d/%d)", from(), to(), flow(), flow() + residue());
    }

    private class ReverseEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public void setCost(long cost) {
            FlowEdge.this.cost = cost;
        }

        public long cost() {
            return -FlowEdge.this.cost();
        }

        public long residue() {
            return flow;
        }

        public long flow() {
            return residue;
        }

        public void pushFlow(long flow) {
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
            return String.format("%d->%d(%d/%d)", from(), to(), flow(), flow() + residue());
        }
    }
}