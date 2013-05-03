package net.ogiekako.algorithm.graph.graphDouble;

import net.ogiekako.algorithm.utils.IntegerUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 5:22
 * To change this template use File | Settings | File Templates.
 */
public class WeightedEdge extends SimpleEdge implements Comparable<WeightedEdge>{
    long weight;
    EdgeD transposed;

    public WeightedEdge(int from, int to, long weight) {
        super(from, to);
        this.weight = weight;
    }

    public long getWeight() {
        return weight;
    }

    public EdgeD getTransposedEdge() {
        if (transposed == null)
            return transposed = new TransposedWeightedEdge();
        return transposed;
    }

    public int compareTo(WeightedEdge o) {
        return IntegerUtils.compare(weight, o.weight);
    }

    private class TransposedWeightedEdge implements EdgeD {

        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public long getWeight() {
            return weight;
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
            return WeightedEdge.this;
        }

        public EdgeD getReverseEdge() {
            return null;
        }
    }
}
