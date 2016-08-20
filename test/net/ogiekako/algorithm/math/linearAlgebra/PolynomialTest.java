package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PolynomialTest {

    @Test
    public void fromString() {
        Mint.set1e9_7();

        Polynomial P = Polynomial.fromString("1");
        Assert.assertEquals(0, P.degree());
        Assert.assertEquals(1, P.evaluate(0).get());

        P = Polynomial.fromString("0");
        Assert.assertEquals(0, P.degree());
        Assert.assertEquals(0, P.evaluate(0).get());

        P = Polynomial.fromString("x + 1");
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
    public void of() {
        Mint.set1e9_7();
        Mint[] m = new Mint[0];
        Polynomial zero = Polynomial.of(m);
        Assert.assertEquals("0", zero.toString());
        Assert.assertEquals(0, zero.degree());
        Assert.assertEquals(Mint.ZERO, zero.coeff(0));
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

    public void checkDivMod(String P, String Q, String R, String mod) {
        Pair<Polynomial, Polynomial> res = Polynomial.fromString(P).divMod(Polynomial.fromString(Q));
//        Assert.assertEquals("div", res.first.toString(), Polynomial.fromString(R).toString());
        Assert.assertEquals("div", Polynomial.fromString(R).toString(), res.first.toString());
        Assert.assertEquals("mod", Polynomial.fromString(mod).toString(), res.second.toString());
    }

    @Test
    public void divMod() {
        Mint.setMod(5);
        checkDivMod("0", "1", "0", "0");
        checkDivMod("1", "1", "1", "0");
        // 3 * 4 = 2 (mod 5).
        checkDivMod("2", "3", "4", "0");
        checkDivMod("x+1", "1", "x+1", "0");
        checkDivMod("x+1", "x+1", "1", "0");
        checkDivMod("1", "x+1", "0", "1");
        checkDivMod("4x^2 + 4x + 2", "2x + 1", "2x + 1", "1");
        checkDivMod("x^2", "x", "x", "0");
    }

    @Test(timeout=1000)
    public void divModLarge() {
        int mod = (int) (1e9+7);
        Mint.setMod(mod);
        int n = 4000;
        int m = 2000;
        Random rnd = new Random(120810284L);
        Mint[] P = new Mint[n];
        for (int i = 0; i < n; i++) {
            P[i] = Mint.of(rnd.nextInt(mod));
        }
        Mint[] Q = new Mint[m];
        for (int i = 0; i < m; i++) {
            Q[i] = Mint.of(rnd.nextInt(mod));
        }
        Pair<Polynomial, Polynomial> res = Polynomial.of(P).divMod(Polynomial.of(Q));
        Assert.assertEquals(Polynomial.of(P), Polynomial.of(Q).mul(res.first).add(res.second));
    }

    @Test(timeout=1000)
    public void divModLarge2() {
        int mod = (int) (1e9+7);
        Mint.setMod(mod);
        int n = 400000;
        int m = 2;
        Random rnd = new Random(120810284L);
        Mint[] P = new Mint[n];
        for (int i = 0; i < n; i++) {
            P[i] = Mint.of(rnd.nextInt(mod));
        }
        Mint[] Q = new Mint[m];
        for (int i = 0; i < m; i++) {
            Q[i] = Mint.of(rnd.nextInt(mod));
        }
        Pair<Polynomial, Polynomial> res = Polynomial.of(P).divMod(Polynomial.of(Q));
        Assert.assertEquals(Polynomial.of(P), Polynomial.of(Q).mul(res.first).add(res.second));
    }
}
