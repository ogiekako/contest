package net.ogiekako.algorithm.dataStructure;

import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class WaveletMatrixTest {
    @Test
    public void rank() throws Exception {
        WaveletMatrix wm = new WaveletMatrix(new int[]{1, 1, 3, 4, 0, 6, 7, 2, 3, Integer.MAX_VALUE});
        Assert.assertEquals(2, wm.rank(3, 9));
        Assert.assertEquals(1, wm.rank(3, 8));
        Assert.assertEquals(1, wm.rank(0, 8));
        Assert.assertEquals(0, wm.rank(8, 8));
        Assert.assertEquals(0, wm.rank(1, 0));
        Assert.assertEquals(1, wm.rank(1, 1));
        Assert.assertEquals(1, wm.rank(Integer.MAX_VALUE, 10));
        Assert.assertEquals(0, wm.rank(Integer.MAX_VALUE, 9));

        wm = new WaveletMatrix(new int[0]);
        Assert.assertEquals(0, wm.rank(0,0));
        wm = new WaveletMatrix(new int[]{0});
        Assert.assertEquals(1, wm.rank(0,1));
    }

    @Test
    public void select() {
        WaveletMatrix wm = new WaveletMatrix(new int[]{0,7,2,1,4,3,6,7,2,5,0,4,7,2});
        Assert.assertEquals(2, wm.select(2, 0));
        Assert.assertEquals(8, wm.select(2, 1));
        Assert.assertEquals(13, wm.select(2, 2));
        Assert.assertEquals(-1, wm.select(2, 3));

        Assert.assertEquals(0, wm.select(0, 0));
        Assert.assertEquals(1, wm.select(7, 0));
        Assert.assertEquals(2, wm.select(2, 0));
        Assert.assertEquals(3, wm.select(1, 0));
        Assert.assertEquals(4, wm.select(4, 0));
        Assert.assertEquals(5, wm.select(3, 0));
        Assert.assertEquals(6, wm.select(6, 0));
        Assert.assertEquals(7, wm.select(7, 1));
        Assert.assertEquals(8, wm.select(2, 1));
        Assert.assertEquals(9, wm.select(5, 0));
        Assert.assertEquals(10, wm.select(0, 1));
        Assert.assertEquals(11, wm.select(4, 1));
        Assert.assertEquals(12, wm.select(7, 2));
        Assert.assertEquals(13, wm.select(2, 2));

        wm = new WaveletMatrix(new int[0]);
        Assert.assertEquals(-1, wm.select(0,0));
        wm = new WaveletMatrix(new int[]{0});
        Assert.assertEquals(0, wm.select(0,0));
    }

    @Test
    public void stress() throws Exception {
        int n = 1000;
        int m = 65;
        int[] s = new int[n];
        Random rnd = new Random(102498109248L);
        for (int i = 0; i < n; i++) {
            s[i] = rnd.nextInt(m);
        }
        int[][] rank = new int[m][n + 1];
        int[][] select = new int[m][n + 1];
        ArrayUtils.fill(select, -1);
        for (int i = 0; i < m; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                rank[i][j + 1] = rank[i][j];
                if (s[j] == i) {
                    rank[i][j + 1]++;
                    select[i][cnt++] = j;
                }
            }
        }

        WaveletMatrix wm = new WaveletMatrix(s);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n + 1; j++) {
                Assert.assertEquals(i + " " + j, rank[i][j], wm.rank(i, j));
                Assert.assertEquals(i + " " + j, select[i][j], wm.select(i, j));
            }
        }
    }
}
