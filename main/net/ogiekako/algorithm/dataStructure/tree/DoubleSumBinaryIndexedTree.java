package net.ogiekako.algorithm.dataStructure.tree;

public class DoubleSumBinaryIndexedTree {
    double[] is;

    public DoubleSumBinaryIndexedTree(int n) {
        is = new double[n + 1];
    }

    public double sum(int s, int t) {// [s,t)
        if (s > 0) return sum(0, t) - sum(0, s);
        double res = 0;
        for (int i = t; i > 0; i -= i & -i) {
            res += is[i];
        }
        return res;
    }

    public void add(int id, double val) {
        for (int i = id + 1; i < is.length; i += i & -i) {
            is[i] += val;
        }
    }

    public void set(int id, double val) {
        val -= sum(id, id + 1);
        add(id, val);
    }
}
