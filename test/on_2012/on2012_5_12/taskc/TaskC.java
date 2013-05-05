package on_2012.on2012_5_12.taskc;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.TreeSet;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        TreeSet<Rational> set = new TreeSet<Rational>();
        for (int i = 0; i < N; i++) {
            String s = in.next();
            if (s.contains("(")) {
                String[] ss = s.split("[\\.\\(\\)]");
                int k = ss[1].length();
                int l = ss[2].length();
                Rational r = new Rational(
                        new BigInteger(ss[0] + ss[1] + ss[2]).subtract(new BigInteger(ss[0] + ss[1])),
                        BigInteger.TEN.pow(k).multiply(BigInteger.TEN.pow(l).subtract(BigInteger.ONE))
                );
                set.add(r);
            } else {
                String[] ss = s.split("\\.");
                int k = ss[1].length();
                BigInteger ten = BigInteger.TEN.pow(k);
                BigInteger num = new BigInteger(ss[0] + ss[1]);
                Rational r = new Rational(num, ten);
                set.add(r);
            }
        }
        out.println(set.size());
    }
    class Rational implements Comparable<Rational> {
        public final BigInteger num, den;

        public Rational(BigInteger num, BigInteger den) {
//			if(den==0)throw new ArithmeticException("/ by zero");
            BigInteger g = num.gcd(den);
//        BigInteger g = gcd(num.abs(), den.abs());
            num = num.divide(g); den = den.divide(g);
            if (den.compareTo(BigInteger.ZERO) < 0) {
                den = den.negate();
                num = num.negate();
            }
            this.num = num; this.den = den;
        }

        public Rational(long num, long den) {
            this(BigInteger.valueOf(num), BigInteger.valueOf(den));
        }

        public Rational add(Rational o) {
            return new Rational(num.multiply(o.den).add(den.multiply(o.num)), den.multiply(o.den));
//        return new Rational(num * o.den + den * o.num, den * o.den);
        }

        public Rational sub(Rational o) {
            return new Rational(num.multiply(o.den).subtract(den.multiply(o.num)), den.multiply(o.den));
//        return new Rational(num * o.den - den * o.num, den * o.den);
        }

        public Rational mul(Rational o) {
            return new Rational(num.multiply(o.num), den.multiply(o.den));

//        return new Rational(num * o.num, den * o.den);
        }

        public Rational div(Rational o) {
            return new Rational(num.multiply(o.den), den.multiply(o.num));
//        return new Rational(num * o.den, den * o.num);
        }

        public String toString() {
            return num + "/" + den;
        }

        public int compareTo(Rational o) {
            return sub(o).signum();
        }

        public int signum() {
            return num.signum();
//        return Long.signum(num);
        }

        public double doubleValue() {
            BigDecimal _num = new BigDecimal(num);
            BigDecimal _den = new BigDecimal(den);
            BigDecimal res = _num.divide(_den, new MathContext(20, RoundingMode.HALF_EVEN));
            return res.doubleValue();
//        return (double)num / den;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Rational rational = (Rational) o;

            if (den != null ? !den.equals(rational.den) : rational.den != null) return false;
            if (num != null ? !num.equals(rational.num) : rational.num != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = num != null ? num.hashCode() : 0;
            result = 31 * result + (den != null ? den.hashCode() : 0);
            return result;
        }
    }
}
