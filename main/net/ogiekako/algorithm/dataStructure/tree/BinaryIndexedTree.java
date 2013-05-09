package net.ogiekako.algorithm.dataStructure.tree;

import java.util.Arrays;

public abstract class BinaryIndexedTree<T> {
    T[] bit;

    public BinaryIndexedTree(int n) {
        //noinspection unchecked
        bit = (T[]) new Object[n + 1];
        Arrays.fill(bit, identityElement());
    }

    protected abstract T identityElement();

    public void add(int index, T value) {
        for (int j = index + 1; j < bit.length; j += j & -j) {
            bit[j] = addImpl(bit[j], value);
        }
    }

    /*
     * associative operation
     */
    protected abstract T addImpl(T a, T b);

    public T sum(int to) {// [0,to)
        T res = identityElement();
        for (int i = to; i > 0; i -= i & -i) {
            res = addImpl(res, bit[i]);
        }
        return res;
    }

    public static class INT extends BinaryIndexedTree<Integer> {
        INT(int n) {
            super(n);
        }
        @Override
        protected Integer identityElement() {
            return 0;
        }

        @Override
        protected Integer addImpl(Integer a, Integer b) {
            return a + b;
        }
    }

    public static class LONG extends BinaryIndexedTree<Long> {
        LONG(int n) {
            super(n);
        }

        @Override
        protected Long identityElement() {
            return 0L;
        }

        @Override
        protected Long addImpl(Long a, Long b) {
            return a + b;
        }
    }
}
