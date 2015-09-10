package net.ogiekako.algorithm;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MODTest {

    @Test
    public void testNormalize() throws Exception {
        MOD.set(5);
        Assert.assertEquals(0, MOD.normalize(0));
        Assert.assertEquals(0, MOD.normalize(5));
        Assert.assertEquals(0, MOD.normalize(10));
        Assert.assertEquals(0, MOD.normalize(15));
        Assert.assertEquals(0, MOD.normalize(-5));
        Assert.assertEquals(0, MOD.normalize(-10));
        Assert.assertEquals(0, MOD.normalize(-15));

        Assert.assertEquals(1, MOD.normalize(1));
        Assert.assertEquals(1, MOD.normalize(6));
        Assert.assertEquals(1, MOD.normalize(11));
        Assert.assertEquals(1, MOD.normalize(-4));
        Assert.assertEquals(1, MOD.normalize(-9));
        Assert.assertEquals(1, MOD.normalize(-14));

        Assert.assertEquals(4, MOD.normalize(4));
        Assert.assertEquals(4, MOD.normalize(9));
        Assert.assertEquals(4, MOD.normalize(14));
        Assert.assertEquals(4, MOD.normalize(-1));
        Assert.assertEquals(4, MOD.normalize(-6));
        Assert.assertEquals(4, MOD.normalize(-11));
    }

    @Test
    public void testDiv() {
        Assert.assertEquals(4, MOD.div(8, 2));
        Assert.assertEquals(9, MOD.div(9, 2) * 2 % MOD.get());
    }
}
