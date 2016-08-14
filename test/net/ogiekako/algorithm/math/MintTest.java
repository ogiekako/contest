package net.ogiekako.algorithm.math;

import org.junit.Assert;
import org.junit.Test;

public class MintTest {

    @Test
    public void testNormalize() throws Exception {
        Mint.setMod(5);
        Assert.assertEquals(0, Mint.normalize(0));
        Assert.assertEquals(0, Mint.normalize(5));
        Assert.assertEquals(0, Mint.normalize(10));
        Assert.assertEquals(0, Mint.normalize(15));
        Assert.assertEquals(0, Mint.normalize(-5));
        Assert.assertEquals(0, Mint.normalize(-10));
        Assert.assertEquals(0, Mint.normalize(-15));

        Assert.assertEquals(1, Mint.normalize(1));
        Assert.assertEquals(1, Mint.normalize(6));
        Assert.assertEquals(1, Mint.normalize(11));
        Assert.assertEquals(1, Mint.normalize(-4));
        Assert.assertEquals(1, Mint.normalize(-9));
        Assert.assertEquals(1, Mint.normalize(-14));

        Assert.assertEquals(4, Mint.normalize(4));
        Assert.assertEquals(4, Mint.normalize(9));
        Assert.assertEquals(4, Mint.normalize(14));
        Assert.assertEquals(4, Mint.normalize(-1));
        Assert.assertEquals(4, Mint.normalize(-6));
        Assert.assertEquals(4, Mint.normalize(-11));
    }

    @Test
    public void testDiv() {
        Mint.set1e9_7();
        Assert.assertEquals(4, Mint.divLong(8, 2));
        Assert.assertEquals(9, Mint.divLong(9, 2) * 2 % Mint.getMod());
    }
}
