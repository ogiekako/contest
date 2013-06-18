package net.ogiekako.algorithm.geometry;

import junit.framework.Assert;
import org.junit.Test;


public class Circle_methodsTest {
    //    @Ignore
    @Test
    public void testIsCC() {
        Point p1 = new Point(100, 100);
        Point p2 = new Point(100, 50);
        double r1 = 60;
        double r2 = 20;
        Point[] cc = Circle_methods.intersection(p1, r1, p2, r2, 1e-7);
        Point.setPrecisionOfString(10);
        Assert.assertEquals("81.2650060048 43.0000000000", cc[0].toString());
        Assert.assertEquals("118.7349939952 43.0000000000", cc[1].toString());
    }
    @Test
    public void testIsCL() {
        Point o = new Point(100, 100);
        double r = 50;
        Point p1 = new Point(110, 0);
        Point p2 = new Point(110, 200);
        Point[] cc = Circle_methods.isCL(o, r, p1, p2);
        Point.setPrecisionOfString(10);
        Assert.assertEquals("110.0000000000 51.0102051443", cc[0].toString());
        Assert.assertEquals("110.0000000000 148.9897948557", cc[1].toString());
    }
    @Test
    public void testTanCP() {
        Point o = new Point(100, 100);
        double r = 50;
        Point p = new Point(160, 20);
        Point[] is = Circle_methods.tanCP(o, r, p);
        Point.setPrecisionOfString(10);
        Assert.assertEquals("149.6410161514 105.9807621135", is[0].toString());
        Assert.assertEquals("80.3589838486 54.0192378865", is[1].toString());
    }
}
