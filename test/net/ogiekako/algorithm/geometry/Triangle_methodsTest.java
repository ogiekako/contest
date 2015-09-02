package net.ogiekako.algorithm.geometry;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class Triangle_methodsTest {

    @Test
    public void singedArea() throws Exception {
        // C
        // AB
        Point a = new Point(0,0), b = new Point(1,0), c = new Point(0,1);
        Assert.assertEquals(0.5, Triangle_methods.signedArea(a, b, c), 1e-9);
        Assert.assertEquals(-0.5, Triangle_methods.signedArea(a, c, b), 1e-9);
    }
}
