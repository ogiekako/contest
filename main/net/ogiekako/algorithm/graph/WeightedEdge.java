package net.ogiekako.algorithm.graph;

public class WeightedEdge extends SimpleEdge implements Comparable<WeightedEdge> {
    Edge transposed;

    public WeightedEdge(int from, int to, double cost) {
        super(from, to);
        this.cost = cost;
    }

    public Edge transposed() {
        if (transposed == null)
            return transposed = new TransposedWeightedEdge();
        return transposed;
    }

    public int compareTo(WeightedEdge o) {
        return Double.compare(cost, o.cost);
    }

    @Override
    public String toString() {
        return from() + "->" + to() + "(" + cost() + ")";
    }

    private class TransposedWeightedEdge implements Edge {

        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public double cost() {
            return cost;
        }

        public void setCost(double cost) {
            WeightedEdge.this.cost = cost;
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
            return WeightedEdge.this;
        }

        public Edge reversed() {
            return null;
        }

        @Override
        public String toString() {
            return from() + "->" + to() + "(" + cost() + ")";
        }
    }
}
