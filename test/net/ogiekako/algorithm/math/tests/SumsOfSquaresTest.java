package net.ogiekako.algorithm.math.tests;

import junit.framework.Assert;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.SumsOfSquares;
import org.junit.Test;

public class SumsOfSquaresTest {

    @Test
    public void testExpressedAsSumOfTwoSquaresLong() {
        int MX = 100010;
        boolean[] exp = new boolean[MX];
        for (int i = 0; i * i <= MX; i++) {
            for (int j = 0; j * j + i * i < MX; j++) {
                exp[i * i + j * j] = true;
            }
        }
        for (int i = 0; i < MX; i++) {
            boolean res = SumsOfSquares.expressedAsSumOfTwoSquares(i);
            Assert.assertEquals(i + "", exp[i], res);
        }
    }

    @Test
    public void testExpressedAsSumOfTwoSquaresIntArrayLong() {
        int MX = 100010;
        boolean[] exp = new boolean[MX];
        for (int i = 0; i * i <= MX; i++) {
            for (int j = 0; j * j + i * i < MX; j++) {
                exp[i * i + j * j] = true;
            }
        }
        int sq = (int) Math.sqrt(MX) + 2;
        int[] ps = SumsOfSquares.makePrimeMod4is3UpTo(sq);
        for (int i = 0; i < MX; i++) {
            boolean res = SumsOfSquares.expressedAsSumOfTwoSquares(ps, i);
            Assert.assertEquals(exp[i] + "", exp[i], res);
        }
    }

    @Test
    public void testWayToWriteAsSumOfTwoSquaresLong() {
        int MX = 1000010;
        int[] exp = new int[MX];
        for (int i = 0; i * i <= MX; i++)
            for (int j = 0; i * i + j * j < MX; j++) {
                int val = 1;
                if (i > 0) val *= 2;
                if (j > 0) val *= 2;
                exp[i * i + j * j] += val;
            }
        for (int i = 0; i < MX; i++) {
            long res = SumsOfSquares.wayToWriteAsSumOfTwoSquares(i);
            Assert.assertEquals(i + "", exp[i], res);
        }
    }

    @Test
    public void testWayToWriteAsSumOfTwoSquaresIntArrayLong() {
        int MX = 1000010;
        int[] exp = new int[MX];
        for (int i = 0; i * i <= MX; i++)
            for (int j = 0; i * i + j * j < MX; j++) {
                int val = 1;
                if (i > 0) val *= 2;
                if (j > 0) val *= 2;
                exp[i * i + j * j] += val;
            }
        int sq = (int) Math.sqrt(MX) + 2;
        int[] ps = MathUtils.generatePrimes(sq);
        for (int i = 0; i < MX; i++) {
            long res = SumsOfSquares.wayToWriteAsSumOfTwoSquares(ps, i);
            Assert.assertEquals(i + "", exp[i], res);
        }
    }
}
