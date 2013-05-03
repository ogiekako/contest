package net.ogiekako.algorithm.graph;

import net.ogiekako.algorithm.utils.IntegerUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 5:22
 * To change this template use File | Settings | File Templates.
 */
public class WeightedEdge extends SimpleEdge implements Comparable<WeightedEdge>{
    Edge transposed;

    public WeightedEdge(int from, int to, long cost) {
        super(from, to);
        this.cost = cost;
    }

    public Edge transposed() {
        if (transposed == null)
            return transposed = new TransposedWeightedEdge();
        return transposed;
    }

    public int compareTo(WeightedEdge o) {
        return IntegerUtils.compare(cost, o.cost);
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

        public long cost() {
            return cost;
        }

        public void setCost(long cost) {
            WeightedEdge.this.cost = cost;
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
