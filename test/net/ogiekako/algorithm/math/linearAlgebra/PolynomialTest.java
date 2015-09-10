package net.ogiekako.algorithm.math.linearAlgebra;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialTest {

    @Test
    public void fromString() {
        Polynomial P = Polynomial.fromString("x + 1");
        Assert.assertEquals(1, P.degree());
        Assert.assertEquals(1, P.evaluate(0).get());
        Assert.assertEquals(2, P.evaluate(1).get());

        P = Polynomial.fromString("x^2 + x + 1");
        Assert.assertEquals(2, P.degree());
        Assert.assertEquals(1, P.evaluate(0).get());
        Assert.assertEquals(3, P.evaluate(1).get());
        Assert.assertEquals(7, P.evaluate(2).get());
    }

    @Test
    public void mul() throws Exception {
        Polynomial P = Polynomial.fromString("x + 1");
        Polynomial Q = Polynomial.fromString("x^2 + x + 2");
        Assert.assertEquals("x^3 + 2x^2 + 3x + 2", P.mul(Q).toString());
    }

    @Test
    public void add() throws Exception {
        Polynomial P = Polynomial.fromString("x + 1");
        Polynomial Q = Polynomial.fromString("x^2 + x + 2");
        Assert.assertEquals("x^2 + 2x + 3", P.add(Q).toString());
    }

    @Test
    public void evaluate() throws Exception {
        Polynomial P = Polynomial.fromString("x + 1");
        Assert.assertEquals(0, P.evaluate(-1).get());
        Assert.assertEquals(2, P.evaluate(1).get());

        P = Polynomial.fromString("x^2 + x + 1");
        Assert.assertEquals(73, P.evaluate(-9).get());
    }
}
