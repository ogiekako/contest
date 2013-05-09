package net.ogiekako.algorithm.graph.graphDouble;

public class SimpleEdge implements EdgeD {
    final int from;
    final int to;
    private EdgeD transposed;

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

    public long getWeight() {
        return 1;
    }

    public double getResidue() {
        return 0;
    }

    public double getFlow() {
        return 0;
    }

    public void pushFlow(double flow) {
        throw new UnsupportedOperationException();
    }

    public EdgeD getTransposedEdge() {
        if (transposed == null)
            return transposed = new TransposedEdge();
        return transposed;
    }

    public EdgeD getReverseEdge() {
        return null;
    }

    private class TransposedEdge implements EdgeD {

        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public long getWeight() {
            return 1;
        }

        public double getResidue() {
            return 0;
        }

        public double getFlow() {
            return 0;
        }

        public void pushFlow(double flow) {
            throw new UnsupportedOperationException();
        }

        public EdgeD getTransposedEdge() {
            return SimpleEdge.this;
        }

        public EdgeD getReverseEdge() {
            return null;
        }


    }
}
