package net.ogiekako.algorithm.geometry.testing;

import net.ogiekako.algorithm.geometry.GeometricalObject;

public class GeoAssert {
    GeometricalObject actual;

    private GeoAssert(GeometricalObject o) {
        actual = o;
    }

    public static GeoAssert assertThat(GeometricalObject o) {
        return new GeoAssert(o);
    }

    /**
     * Compare the equality taking numerical errors into account
     */
    public void isEqualTo(GeometricalObject expected) {
        if (actual == null) {
            if (expected == null) return;
            throw new AssertionError("Expected: " + expected + "  Actual: " + actual);
        }
        boolean ok = actual.isEqualTo(expected);
        if (!ok) {
            throw new AssertionError("Expected: " + expected + "  Actual: " + actual);
        }
    }
}
