package net.ogiekako.algorithm.dataStructure.segmentTree;

import java.util.Arrays;

/**
 * The segment tree data structure for a semigroup defined over V.
 *
 * @param <V>
 */
public abstract class SegTreeSemigroup<V> {
    /**
     * The operation is required to be associative, where as is not required to be commutative.
     */
    protected abstract V operate(V left, V right);

    /**
     * identity I must satisfy both conditions operate(x,I) = x and operate(I,x) = x for any x in V.
     */
    protected abstract V identity();

    final int n;

    // root : 0
    // parent : (i-1)/2
    // left,right : 2*i+1, 2*i+2
    // leaf : i >= n-1
    final V[] answer;
    final int[] left;
    final int[] right;

    public SegTreeSemigroup(int N) {
        n = Integer.highestOneBit(N) << 1;
        //noinspection unchecked
        answer = (V[]) new Object[n * 2 - 1];
        Arrays.fill(answer, identity());
        left = new int[n * 2 - 1];
        right = new int[n * 2 - 1];
        init(0, 0, n);
    }

    private void init(int pos, int left, int right) {
        if (pos >= answer.length) return;
        this.left[pos] = left;
        this.right[pos] = right;
        init(pos * 2 + 1, left, (left + right) / 2);
        init(pos * 2 + 2, (left + right) / 2, right);
    }

    public void set(int i, V value) {
        i += n - 1;
        answer[i] = value;
        while (i > 0) {
            i = (i - 1) / 2;
            answer[i] = operate(answer[i * 2 + 1], answer[i * 2 + 2]);
        }
    }

    public V convolution(int from, int to) {
        return convolution(0, from, to);
    }

    V convolution(int pos, int from, int to) {
        if (disjoint(pos, from, to)) return identity();
        if (inner(pos, from, to)) return answer[pos];
        return operate(convolution(pos * 2 + 1, from, to), convolution(pos * 2 + 2, from, to));
    }

    final boolean inner(int pos, int from, int to) {return from <= left[pos] && right[pos] <= to;}

    final boolean disjoint(int pos, int from, int to) { return right[pos] <= from || to <= left[pos]; }
}
