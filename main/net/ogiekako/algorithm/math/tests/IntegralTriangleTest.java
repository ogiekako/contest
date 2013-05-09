package net.ogiekako.algorithm.math.tests;

import junit.framework.Assert;
import net.ogiekako.algorithm.math.IntegralTriangle;
import org.junit.Test;

public class IntegralTriangleTest {

    @Test
    public void testPythagoreanUpto() {
        System.out.println("IntegralTriangleTest.testPythagoreanUpto()");
        int N = 10000000;
        IntegralTriangle[] ps = IntegralTriangle.generatePrimitivePythagoreans(N);
        System.out.println(ps.length);
        for (IntegralTriangle p : ps) {
            Assert.assertEquals(gcd(gcd(p.a, p.b), p.c), 1);
            Assert.assertEquals(p.a * p.a + p.b * p.b, p.c * p.c);
        }
    }

    @Test
    public void testIntegralCircumRadiusUpto() {
        System.out
                .println("IntegralTriangleTest.testIntegralCircumRadiusUpto()");
        int N = 100000;
        IntegralTriangle[] ts = IntegralTriangle.integralCircleRadiusUpto(N);
        long res = 0;
        for (IntegralTriangle t : ts) {
            long R = (long) Math.round(t.circumRadius());
            long k = N / R;
            res += R * k * (k + 1) / 2;
        }
//        Assert.assertEquals(727227472448913L, res); // N = 10000000
        Assert.assertEquals(33211076049L, res);
        System.out.println(ts.length);
    }

    long gcd(long x, long y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
