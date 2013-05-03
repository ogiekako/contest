package net.ogiekako.algorithm.math;

public abstract class PowerOperation<T> {
    protected abstract T associativeOperation(T a, T b);

    protected T identity() {return null;}

    // Returns null when identity() is not overridden.
    public T power(T original, long K) {
        for (T cur = identity(); ; ) {
            if ((K & 1) == 1) cur = cur == null ? original : associativeOperation(cur, original);
            if ((K >>>= 1) == 0) return cur;
            original = associativeOperation(original, original);
        }
    }
}
