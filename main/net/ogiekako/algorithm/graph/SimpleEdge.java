package net.ogiekako.algorithm.graph;

public class SimpleEdge implements Edge {
    final int from;
    final int to;
    private Edge transposed;
    long cost = 1;

    @Override
    public String toString() {
        return from() + " -> " + to();
    }

    public SimpleEdge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public long cost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long residue() {
        return 0;
    }

    public long flow() {
        return 0;
    }

    public void pushFlow(long flow) {
        throw new UnsupportedOperationException();
    }

    public Edge transposed() {
        if (transposed == null)
            return transposed = new TransposedEdge();
        return transposed;
    }

    public Edge reversed() {
        return null;
    }

    private class TransposedEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public long cost() {
            return cost;
        }

        public long residue() {
            return 0;
        }

        public long flow() {
            return 0;
        }

        public void pushFlow(long flow) {
            throw new UnsupportedOperationException();
        }

        public Edge reversed() {
            return null;
        }

        public Edge transposed() {
            return SimpleEdge.this;
        }

        public void setCost(long cost) {
            SimpleEdge.this.cost = cost;
        }

        @Override
        public String toString() {
            return from() + " -> " + to();
        }
    }
}