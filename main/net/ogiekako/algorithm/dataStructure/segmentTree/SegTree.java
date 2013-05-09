package net.ogiekako.algorithm.dataStructure.segmentTree;


/**
 * references: http://www.slideshare.net/iwiwi/ss-3578491 p41
 *
 * @param <V> The type of the convoluted value.
 * @param <D> The type of values in the array.
 */
public abstract class SegTree<V, D> extends SegTreeSemigroup<V> {
    /**
     * The operation is required to be associative AND commutative.
     */
    @Override
    protected abstract V operate(V left, V right);

    /**
     * return the result when 'delta' is added uniformly to the segment of length 'length'
     * that has value 'value'.
     */
    protected abstract V operate(V value, int length, D delta);

    protected abstract D join(D was, D delta);

    // lazy propagation
    D[] uniform;
    boolean[] addLater;
    boolean[] setLater;

    /**
     * @param N - the length of the array
     */
    public SegTree(int N) {
        super(N);
        //noinspection unchecked
        uniform = (D[]) new Object[answer.length];
        addLater = new boolean[answer.length];
        setLater = new boolean[answer.length];
    }

    private void touch(int pos) {
        if (addLater[pos] && setLater[pos]) throw new AssertionError();
        if ((uniform[pos] == null) == (addLater[pos] || setLater[pos])) throw new AssertionError();
        if (addLater[pos]) {
            if (pos < n - 1) {
                for (int child = pos * 2 + 1; child <= pos * 2 + 2; child++) {
                    if (addLater[child] || setLater[child]) uniform[child] = join(uniform[child], uniform[pos]);
                    else {
                        addLater[child] = true;
                        uniform[child] = uniform[pos];
                    }
                }
            }
            answer[pos] = operate(answer[pos], right[pos] - left[pos], uniform[pos]);
            addLater[pos] = false;
            uniform[pos] = null;
        } else if (setLater[pos]) {
            if (pos < n - 1) {
                for (int child = pos * 2 + 1; child <= pos * 2 + 2; child++) {
                    uniform[child] = uniform[pos];
                    addLater[child] = false;
                    setLater[child] = true;
                }
            }
            answer[pos] = operate(identity(), right[pos] - left[pos], uniform[pos]);
            setLater[pos] = false;
            uniform[pos] = null;
        }
    }

    public void set(int from, int to, D value) {
        if (from >= to) return;
        set(0, from, to, value);
    }

    private void set(int pos, int from, int to, D value) {
        if (disjoint(pos, from, to)) return;
        touch(pos);
        if (inner(pos, from, to)) {
            setLater[pos] = true;
            uniform[pos] = value;
        } else {
            set(pos * 2 + 1, from, to, value);
            set(pos * 2 + 2, from, to, value);
            touch(pos * 2 + 1);
            touch(pos * 2 + 2);
            answer[pos] = operate(answer[pos * 2 + 1], answer[pos * 2 + 2]);
        }
    }

    /**
     * O(log n)
     */
    public void add(int from, int to, D value) {
        add(0, from, to, value);
    }

    private void add(int pos, int from, int to, D value) {
        if (disjoint(pos, from, to)) return;
        touch(pos);
        if (inner(pos, from, to)) {
            addLater[pos] = true;
            uniform[pos] = value;
        } else {
            add(pos * 2 + 1, from, to, value);
            add(pos * 2 + 2, from, to, value);
            touch(pos * 2 + 1);
            touch(pos * 2 + 2);
            answer[pos] = operate(answer[pos * 2 + 1], answer[pos * 2 + 2]);
        }
    }

    @Override
    V convolution(int pos, int from, int to) {
        touch(pos);
        if (disjoint(pos, from, to)) return identity();
        if (inner(pos, from, to)) return answer[pos];
        return operate(convolution(pos * 2 + 1, from, to), convolution(pos * 2 + 2, from, to));
    }

    /**
     * Range Minimum Query
     */
    public static class Min extends SegTree<Long, Long> {

        public Min(int N) {
            super(N);
        }

        @Override
        protected Long operate(Long value, int length, Long delta) {
            return Math.min(value, delta);
        }

        @Override
        protected Long join(Long was, Long delta) {
            return Math.min(was, delta);
        }

        @Override
        protected Long operate(Long left, Long right) {
            return Math.min(left, right);
        }

        @Override
        protected Long identity() {
            return Long.MAX_VALUE;
        }
    }

    public static class Sum extends SegTree<Long, Integer> {
        public Sum(int N) {
            super(N);
        }

        @Override
        protected Long operate(Long left, Long right) {
            return left + right;
        }

        @Override
        protected Long operate(Long value, int length, Integer delta) {
            return value + (long) length * delta;
        }

        @Override
        protected Integer join(Integer was, Integer delta) {
            return was + delta;
        }

        @Override
        protected Long identity() {
            return 0L;
        }
    }

    public static class Coin extends SegTree<Integer, Boolean> {
        /**
         * @param N - the length of the array
         */
        public Coin(int N) {
            super(N);
        }

        @Override
        protected Integer operate(Integer left, Integer right) {
            return left + right;
        }

        @Override
        protected Integer operate(Integer value, int length, Boolean delta) {
            return delta ? length - value : value;
        }

        @Override
        protected Boolean join(Boolean was, Boolean delta) {
            return was ^ delta;
        }

        @Override
        protected Integer identity() {
            return 0;
        }
    }
}
