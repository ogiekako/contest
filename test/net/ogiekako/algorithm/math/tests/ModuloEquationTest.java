package net.ogiekako.algorithm.math.tests;

import net.ogiekako.algorithm.math.ModuloEquation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModuloEquationTest {
    @Test
    public void testModuloEquation() {
        int[] m = {5, 7, 11};
        int[] r = {4, 2, 3};
        long val = ModuloEquation.moduloEquation(m, r);
        for (int i = 0; i < r.length; i++) {
            assertEquals(r[i], val % m[i]);
        }

        m = new int[]{2, 3, 5 * 5, 7, 11, 13, 17, 19 * 19};
        assertTrue(2L * 3 * 5 * 5 * 7 * 11 * 13 * 17 * 19 * 19 < Integer.MAX_VALUE);
        r = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        val = ModuloEquation.moduloEquation(m, r);
        for (int i = 0; i < r.length; i++) {
            assertEquals(r[i], val % m[i]);
        }
    }
}
