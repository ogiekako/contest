package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.geometry.testing.GeoAssert;
import org.junit.Assert;
import org.junit.Test;

public class SegmentTest {
    // GH
    // EF
    // CD
    // AB
    Point a = new Point(0, 0), b = new Point(1, 0), c = new Point(0, 1), d = new Point(1, 1), e = new Point(0, 2), f = new Point(1, 2), g = new Point(0, 3), h = new Point(1, 3);

    @Test
    public void intersectionWithLine() {
        assertEq(null, new Segment(a, b).intersection(new Line(c, d)));
        assertEq(new Segment(a, a), new Segment(a, b).intersection(new Line(c, a)));
        assertEq(new Segment(a, c), new Segment(a, c).intersection(new Line(c, e)));
        assertEq(new Segment(0.5, 0.5, 0.5, 0.5), new Segment(a, d).intersection(new Line(b, c)));
    }

    @Test
    public void intersectWithLine() {
        Assert.assertFalse(new Segment(a, b).intersect(new Line(c, d)));
        Assert.assertTrue(new Segment(a, b).intersect(new Line(c, a)));
        Assert.assertTrue(new Segment(a, c).intersect(new Line(c, e)));
        Assert.assertTrue(new Segment(a, d).intersect(new Line(b, c)));
    }

    @Test
    public void intersectWithSegment() {
        Assert.assertTrue(new Segment(a, b).intersect(new Segment(a, c)));
        Assert.assertTrue(new Segment(c, e).intersect(new Segment(a, g)));
        Assert.assertTrue(new Segment(a, f).intersect(new Segment(b, c)));
        Assert.assertTrue(new Segment(a, d).intersect(new Segment(b, f)));
        Assert.assertFalse(new Segment(a, b).intersect(new Segment(f, c)));
        Assert.assertFalse(new Segment(a, c).intersect(new Segment(e, g)));
        Assert.assertFalse(new Segment(a, c).intersect(new Segment(b, d)));
        Assert.assertFalse(new Segment(e, g).intersect(new Segment(a, c)));
        Assert.assertFalse(new Segment(b, d).intersect(new Segment(a, c)));
        Assert.assertFalse(new Segment(a, d).intersect(new Segment(f, c)));
    }

    @Test
    public void distanceToPoint() {
        Assert.assertEquals(1.0, new Segment(a, b).distance(c), 1e-9);
        Assert.assertEquals(0.0, new Segment(a, c).distance(c), 1e-9);
        Assert.assertEquals(1.0, new Segment(a, c).distance(e), 1e-9);
        Assert.assertEquals(1.0, new Segment(a, d).distance(f), 1e-9);
        Assert.assertEquals(Math.sqrt(2) / 2, new Segment(a, d).distance(b), 1e-9);
    }

    private void assertEq(Segment expect, Segment actual) {
        GeoAssert.assertThat(actual).isEqualTo(expect);
    }
}