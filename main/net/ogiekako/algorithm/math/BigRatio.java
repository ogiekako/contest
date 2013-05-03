package net.ogiekako.algorithm.math;

import java.math.BigInteger;

public class BigRatio implements Comparable<BigRatio> {
    // num/den, den>0
    public final BigInteger num;
    public final BigInteger den;
    public static final BigRatio ZERO = new BigRatio(BigInteger.ZERO, BigInteger.ONE);
    public static final BigRatio ONE = new BigRatio(BigInteger.ONE, BigInteger.ONE);

    public BigRatio(BigInteger num, BigInteger den) {
        if (den.equals(BigInteger.ZERO)) throw new IllegalArgumentException();
        BigInteger gcd = num.gcd(den);
        num = num.divide(gcd);
        den = den.divide(gcd);
        if (den.compareTo(BigInteger.ZERO) < 0) {
            num = num.negate();
            den = den.negate();
        }
        this.num = num;
        this.den = den;
    }

    public BigRatio add(BigRatio r) {
        return new BigRatio(num.multiply(r.den).add(den.multiply(r.num)), den.multiply(r.den));
    }

    public BigRatio sub(BigRatio r) {
        return new BigRatio(num.multiply(r.den).subtract(den.multiply(r.num)), den.multiply(r.den));
    }

    public BigRatio mul(BigRatio r) {
        return new BigRatio(num.multiply(r.num), den.multiply(r.den));
    }

    public BigRatio div(BigRatio r) {
        if (r.num.equals(BigInteger.ZERO)) throw new IllegalArgumentException();
        return new BigRatio(num.multiply(r.den), den.multiply(r.num));
    }

    public int compareTo(BigRatio o) {
        return sub(o).num.compareTo(BigInteger.ZERO);
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        BigRatio bigRatio = (BigRatio) o;

        if (!den.equals(bigRatio.den)) return false;
        if (!num.equals(bigRatio.num)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = num.hashCode();
        result = 31 * result + den.hashCode();
        return result;
    }
}