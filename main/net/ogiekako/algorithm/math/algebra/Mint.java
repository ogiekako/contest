package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.MOD;
import net.ogiekako.algorithm.math.MathUtils;

public class Mint extends Field<Mint> {
    public static Mint ZERO;
    public static Mint ONE;
    final long x;

    public Mint(long x) {
        this.x = MOD.normalize(x);
    }

    public int get() {
        return (int) x;
    }

    public static Mint of(long value) {
        return new Mint(value);
    }

    public Mint mulInv() {
        return of(MathUtils.inverse(x, MOD.get()));
    }

    public Mint mul(Mint other) {
        return mul(other.x);
    }

    public Mint mul(long y) {
        return of(MOD.mul(x, y));
    }

    public Mint div(Mint other) {
        return div(other.x);
    }

    public Mint div(long y) {
        return of(MOD.div(x, y));
    }

    public Mint add(Mint other) {
        return of(x + other.x);
    }

    public Mint add(long y) {
        return of(x + MOD.normalize(y));
    }

    public Mint minus(Mint other) {
        return minus(other.x);
    }

    public Mint minus(long y) {
        return of(x - MOD.normalize(y));
    }

    public Mint addInv() {
        return of(-x);
    }

    public boolean isZero() {
        return x == 0;
    }

    public Mint zero() {
        return ZERO;
    }

    public boolean isOne() {
        return x == MOD.normalize(1);
    }

    @Override
    public String toString() {
        return "" + x;
    }
}
