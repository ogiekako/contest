package net.ogiekako.algorithm.graph;

public class SimpleEdge implements Edge {
    final int from;
    final int to;
    double cost = 1;
    private Edge transposed;

    public SimpleEdge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from() + " -> " + to();
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double cost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double residue() {
        return 1;
    }

    public double flow() {
        return 0;
    }

    public void pushFlow(double flow) {
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

        public double cost() {
            return cost;
        }

        public double residue() {
            return 1;
        }

        public double flow() {
            return 0;
        }

        public void pushFlow(double flow) {
            throw new UnsupportedOperationException();
        }

        public Edge reversed() {
            return null;
        }

        public Edge transposed() {
            return SimpleEdge.this;
        }

        public void setCost(double cost) {
            SimpleEdge.this.cost = cost;
        }

        @Override
        public String toString() {
            return from() + " -> " + to();
        }
    }
}