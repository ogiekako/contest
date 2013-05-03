package net.ogiekako.algorithm.math.algebra;

public class Frac<V extends Ring<V>> implements Field<Frac<V>> {
    V num;
    V den;

    public Frac(V num, V den) {
//        Poly g = num.gcd(den);
        this.num = num;
        this.den = den;
    }

    public static Frac<Poly<Irr>> one() {
        return new Frac(Poly.one(), Poly.one());
    }

    private Frac of(V num, V den) {
        return new Frac(num, den);
    }

    public Frac<V> minus(Frac<V> other) {
        V nNum = num.mul(other.den).add(den.mul(other.num).addInv());
        V nDen = den.mul(other.den);
        return of(nNum, nDen);
    }

    public Frac div(V other) {
        V nDen = den.mul(other);
        return of(num, nDen);
    }

    public Frac<V> mulInv() {
        return of(den, num);
    }

    public Frac<V> mul(Frac<V> other) {
        return of(num.mul(other.num), den.mul(other.den));
    }

    public Frac<V> add(Frac<V> other) {
        return minus(addInv());
    }

    public Frac<V> addInv() {
        return minus(this).minus(this);
    }

    public boolean isZero() {
        return num.isZero();
    }

    public Frac<V> zero() {
        return new Frac(num.zero(),num.zero());
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }
}
