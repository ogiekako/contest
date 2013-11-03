package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.EPS;

public class Irr extends Field<Irr> implements Comparable<Irr> {
    public final double value;
    public Irr(double value) {
        this.value = value;
    }
    public static Irr of(double value) {
        return new Irr(value);
    }
    public Irr mulInv() {
        return of(1 / value);
    }

    public Irr mul(Irr other) {
        return of(value * other.value);
    }

    public Irr add(Irr other) {
        return of(value + other.value);
    }

    public Irr addInv() {
        return of(-value);
    }

    public boolean isZero() {
        return Math.abs(value) < EPS.EPS;
    }

    public Irr zero() {
        return of(0);
    }

    public int compareTo(Irr o) {
        return Double.compare(value, o.value);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Irr irr = (Irr) o;

        if (Double.compare(irr.value, value) != 0) return false;

        return true;
    }
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
