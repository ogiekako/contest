package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.EPS;

public class Irr implements Field<Irr>, Comparable<Irr> {
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
}
