package net.ogiekako.algorithm.dataStructure.tree;
import java.util.Arrays;

public class RmqBit {
    long initValue;
    long[] bit;
    public RmqBit(int n) {
        this(n, 0);
    }
    public RmqBit(int n, long initValue) {
        this.initValue = initValue;
        bit = new long[n + 1];
        Arrays.fill(bit, initValue);
    }
    /**
     * 最小値の更新.
     * 増やすことはできないので注意.
     *
     * @param i index
     * @param v updated value
     */
    public void update(int i, long v) {
        for (int j = i + 1; j < bit.length; j += j & -j) {
            bit[j] = Math.min(bit[j], v);
        }
    }

    public long getMin(int right) {// [0,right)
        long res = initValue;
        for (int i = right; i > 0; i -= i & -i) {
            res = Math.min(res, bit[i]);
        }
        return res;
    }
}
