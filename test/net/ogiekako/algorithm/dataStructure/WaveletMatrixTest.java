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
        Assert.assertEquals(0, wm.rank(0, 0));
        wm = new WaveletMatrix(new int[]{0});
        Assert.assertEquals(1, wm.rank(0, 1));
    }

    @Test
    public void select() {
        WaveletMatrix wm = new WaveletMatrix(new int[]{0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2});
        Assert.assertEquals(0, wm.select(0, 0));
        Assert.assertEquals(1, wm.select(7, 0));
        Assert.assertEquals(2, wm.select(2, 0));
        Assert.assertEquals(3, wm.select(1, 0));
        Assert.assertEquals(5, wm.select(3, 0));
        Assert.assertEquals(6, wm.select(6, 0));
        Assert.assertEquals(8, wm.select(2, 1));
        Assert.assertEquals(9, wm.select(5, 0));
        Assert.assertEquals(10, wm.select(0, 1));
        Assert.assertEquals(11, wm.select(4, 1));
        Assert.assertEquals(12, wm.select(7, 2));
        Assert.assertEquals(13, wm.select(2, 2));
        Assert.assertEquals(-1, wm.select(2, 3));

        wm = new WaveletMatrix(new int[0]);
        Assert.assertEquals(-1, wm.select(0, 0));
        wm = new WaveletMatrix(new int[]{0});
        Assert.assertEquals(0, wm.select(0, 0));
    }

    @Test
    public void max() {
        WaveletMatrix wm = new WaveletMatrix(new int[]{0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2});
        Assert.assertEquals(0, wm.max(0, 0));
        Assert.assertEquals(6, wm.max(2, 7));
        Assert.assertEquals(5, wm.max(8, 12));
    }

    @Test
    public void min() {
        WaveletMatrix wm = new WaveletMatrix(new int[]{0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2});
        Assert.assertEquals(Integer.MAX_VALUE, wm.min(0, 0));
        Assert.assertEquals(1, wm.min(2, 7));
        Assert.assertEquals(0, wm.min(8, 12));
        Assert.assertEquals(3, wm.min(4, 8));
    }

    @Test
    public void quantile() {
        WaveletMatrix wm = new WaveletMatrix(new int[]{0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2});
        Assert.assertEquals(2, wm.quantile(0, 3, 1));
        Assert.assertEquals(0, wm.quantile(0, 3, 2));
        Assert.assertEquals(1, wm.quantile(0, 4, 2));
        Assert.assertEquals(4, wm.quantile(2, 13, 5));
    }

    @Test
    public void rangefreq() {
        int[] S = {0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2};
        WaveletMatrix wm = new WaveletMatrix(S.clone());
        Assert.assertEquals(6, wm.rangefreq(2, 11, 2, 7));
        Assert.assertEquals(14, wm.rangefreq(0, 14, 0, 8));

        Random rnd = new Random(1209812L);
        int n = S.length;
        for (int i = 0; i < 100; ) {
            int s = rnd.nextInt(n);
            int t = rnd.nextInt(n);
            if (s > t) continue;
            int x = rnd.nextInt(8);
            int y = rnd.nextInt(8);

            int res = wm.rangefreq(s, t, x, y);
            int exp = 0;
            for (int j = s; j < t; j++)
                if (x <= S[j] && S[j] < y) {
                    exp++;
                }
            Assert.assertEquals(s + " " + t + " " + x + " " + y, exp, res);

            i++;
        }
    }

    @Test
    public void prevvalue() {
        int[] S = {0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2};
        WaveletMatrix wm = new WaveletMatrix(S.clone());
        Assert.assertEquals(6, wm.prevvalue(2, 11, 2, 7));
        Assert.assertEquals(7, wm.prevvalue(0, 14, 0, 8));
        Assert.assertEquals(4, wm.prevvalue(2, 9, 2, 6));
    }

    @Test
    public void nextvalue() {
        int[] S = {0, 7, 2, 1, 4, 3, 6, 7, 2, 5, 0, 4, 7, 2};
        WaveletMatrix wm = new WaveletMatrix(S.clone());
        Assert.assertEquals(2, wm.nextvalue(2, 11, 2, 7));
        Assert.assertEquals(0, wm.nextvalue(0, 14, 0, 8));
        Assert.assertEquals(2, wm.nextvalue(4, 10, 1, 6));
    }

    @Test
    public void stress() throws Exception {
        Random rnd = new Random(102498109248L);
        for (int n : new int[]{1, 2, 3, 10, 1000}) {
            int m = 65;
            int[] S = new int[n];
            for (int i = 0; i < n; i++) {
                S[i] = rnd.nextInt(m);
            }
            int[][] rank = new int[m][n + 1];
            int[][] select = new int[m][n + 1];
            ArrayUtils.fill(select, -1);
            for (int i = 0; i < m; i++) {
                int cnt = 0;
                for (int j = 0; j < n; j++) {
                    rank[i][j + 1] = rank[i][j];
                    if (S[j] == i) {
                        rank[i][j + 1]++;
                        select[i][cnt++] = j;
                    }
                }
            }

            WaveletMatrix wm = new WaveletMatrix(S.clone());
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n + 1; j++) {
                    Assert.assertEquals(i + " " + j, rank[i][j], wm.rank(i, j));
                    Assert.assertEquals(i + " " + j, select[i][j], wm.select(i, j));
                }
            }

            for (int i = 0; i < 100; ) {
                int s = rnd.nextInt(n);
                int t = rnd.nextInt(n);
                if (s > t) continue;
                int x = rnd.nextInt(m + 1);
                int y = rnd.nextInt(m + 1);

                int res = wm.rangefreq(s, t, x, y);
                int exp = 0;
                for (int j = s; j < t; j++)
                    if (x <= S[j] && S[j] < y) {
                        exp++;
                    }
                Assert.assertEquals(s + " " + t + " " + x + " " + y, exp, res);

                res = wm.prevvalue(s, t, x, y);
                exp = -1;
                for (int j = s; j < t; j++)
                    if (x <= S[j] && S[j] < y) {
                        exp = Math.max(exp, S[j]);
                    }
                Assert.assertEquals(s + " " + t + " " + x + " " + y, exp, res);

                res = wm.nextvalue(s, t, x, y);
                exp = Integer.MAX_VALUE;
                for (int j = s; j < t; j++)
                    if (x <= S[j] && S[j] < y) {
                        exp = Math.min(exp, S[j]);
                    }
                exp = exp == Integer.MAX_VALUE ? -1 : exp;
                Assert.assertEquals(s + " " + t + " " + x + " " + y, exp, res);

                i++;
            }
        }
    }
}
