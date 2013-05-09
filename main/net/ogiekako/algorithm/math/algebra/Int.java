package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.utils.IntegerUtils;

public class Int implements Ring<Int>, Comparable<Int> {
    public final long value;
    public Int(long value) {
        this.value = value;
    }
    public static Int of(long value) {
        return new Int(value);
    }

    public Int mul(Int other) {
        return of(value * other.value);
    }

    public Int add(Int other) {
        return of(value + other.value);
    }

    public Int addInv() {
        return of(-value);
    }

    public boolean isZero() {
        return value == 0;
    }

    public Int zero() {
        return of(0);
    }

    public int compareTo(Int o) {
        return IntegerUtils.compare(value, o.value);
    }
}
