package net.ogiekako.algorithm.misc;

import org.junit.Assert;
import org.junit.Test;

public class MaxRectangleTest {
    @Test
    public void maxRectangle() throws Exception {
        boolean[][] grid = new boolean[][]{
                {true, true, false},
                {false, true, true},
                {false, true, true},
        };
        int res = MaxRectangle.maxRectangle(grid);
        Assert.assertEquals(4, res);
    }

}