package net.ogiekako.algorithm.math.rationalNumber;

public class RationalNumber implements Comparable<RationalNumber> {
    public final long num, den;

    /*
     * den >= 0 となるように正規化される.
     * den == 0  は 無限大 を表す. (num>0 +∞, num<0 -∞)
     */
    public RationalNumber(long num, long den) {
//			if(den==0)throw new ArithmeticException("/ by zero");
        long g = gcd(num, den);
        num /= g;
        den /= g;
        if (den < 0) {
            den = -den;
            num = -num;
        }
        this.num = num; this.den = den;
    }

    long gcd(long x, long y) {
        return x == 0 ? y : gcd(y % x, x);
    }

    public RationalNumber add(RationalNumber o) {
        return new RationalNumber(num * o.den + den * o.num, den * o.den);
    }

    public RationalNumber sub(RationalNumber o) {
        return new RationalNumber(num * o.den - den * o.num, den * o.den);
    }

    public RationalNumber mul(RationalNumber o) {
        return new RationalNumber(num * o.num, den * o.den);
    }

    public RationalNumber div(RationalNumber o) {
        return new RationalNumber(num * o.den, den * o.num);
    }

    public String toString() {
        return num + "/" + den;
    }

    public int compareTo(RationalNumber o) {
        return sub(o).signum();
    }

    public int signum() {
        return Long.signum(num);
    }

    public static final RationalNumber ZERO = new RationalNumber(0, 1);
    public static final RationalNumber ONE = new RationalNumber(1, 1);

    public static RationalNumber valueOf(long num) {
        return new RationalNumber(num, 1);
    }

    public double doubleValue() {
        return (double) num / den;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) throw new RuntimeException();
        RationalNumber that = (RationalNumber) o;
        return den == that.den && num == that.num;
    }

    @Override
    public int hashCode() {
        int result = (int) (num ^ (num >>> 32));
        result = 31 * result + (int) (den ^ (den >>> 32));
        return result;
    }
}
