package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.geometry.testing.GeoAssert;
import org.junit.Assert;
import org.junit.Test;

public class SegmentTest {

    @Test
    public void intersection() throws Exception {
        // EF
        // CD
        // AB
        Point a = new Point(0, 0), b = new Point(1, 0), c = new Point(0, 1), d = new Point(1, 1), e = new Point(0, 2), f = new Point(1, 2);
        assertEq(null, new Segment(a, b).intersection(new Line(c, d)));
        assertEq(new Segment(a, a), new Segment(a, b).intersection(new Line(c, a)));
        assertEq(new Segment(a, c), new Segment(a, c).intersection(new Line(c, e)));
        assertEq(new Segment(0.5, 0.5, 0.5, 0.5), new Segment(a, d).intersection(new Line(b, c)));
    }

    private void assertEq(Segment expect, Segment actual) {
        GeoAssert.assertThat(actual).isEqualTo(expect);
    }
}