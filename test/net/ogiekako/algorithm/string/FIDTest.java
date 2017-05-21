package net.ogiekako.algorithm.string;

import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;
import java.util.Random;

import static org.junit.Assert.*;

public class FIDTest {
    @Test
    public void rank() throws Exception {
        // 10101001
        FID B = new FID(8);
        B.set(0);
        B.set(2);
        B.set(4);
        B.set(7);
        B.build();

        Assert.assertEquals(0, B.rank(true, 0));
        Assert.assertEquals(1, B.rank(true, 1));
        Assert.assertEquals(0, B.rank(false, 1));
        Assert.assertEquals(4, B.rank(false, 7));
        Assert.assertEquals(3, B.rank(true, 7));

        B = new FID(0);
        B.build();
        Assert.assertEquals(0, B.rank(false, 1));
        Assert.assertEquals(0, B.rank(true, 1));
    }

    @Test(timeout = 300)
    public void rankSpeed() throws Exception {
        int n = (int) 1e6;
        FID B = new FID(n);
        for (int i = 0; i < n; i++) {
            B.set(i);
        }
        B.build();
        for (int i = 0; i < n; i++) {
            B.rank(true, i);
        }
    }

    @Test
    public void select() throws Exception {
        // 10101001
        FID B = new FID(8);
        B.set(0);
        B.set(2);
        B.set(4);
        B.set(7);
        B.build();

        Assert.assertEquals(0, B.select(true, 0));
        Assert.assertEquals(1, B.select(false, 0));
        Assert.assertEquals(2, B.select(true, 1));
        Assert.assertEquals(3, B.select(false, 1));
        Assert.assertEquals(-1, B.select(false, 4));
        Assert.assertEquals(7, B.select(true, 3));
        Assert.assertEquals(-1, B.select(true, 4));

        // 10101001
        B = new FID(0);
        B.build();
        Assert.assertEquals(-1, B.select(true, 0));
        Assert.assertEquals(-1, B.select(false, 0));
    }

    @Test
    public void stress() throws Exception {
        Random rnd = new Random(1094102984L);

        for (int n : new int[]{1,2,63,64,65,255,256,257,300,1000}){
            FID B = new FID(n);
            for (int i = 0; i < n; i++) {
                if (rnd.nextBoolean()) {
                    B.set(i);
                }
            }
            B.build();

            int[][] rank = new int[2][n + 1];
            int[][] select = new int[2][n + 1];
            ArrayUtils.fill(select, -1);
            for (int i = 0, zero = 0, one = 0; i < n; i++) {
                rank[0][i + 1] = rank[0][i];
                rank[1][i + 1] = rank[1][i];
                rank[B.get(i) ? 1 : 0][i + 1]++;
                if (B.get(i)) {
                    select[1][one++] = i;
                } else {
                    select[0][zero++] = i;
                }
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < n + 1; j++) {
                    Assert.assertEquals(rank[i][j], B.rank(i == 1, j));
                    Assert.assertEquals(B + " " + i + " " + j, select[i][j], B.select(i == 1, j));
                }
            }
        }
    }

    @Test(timeout = 500L)
    public void selectSpeed() throws Exception {
        Random rnd = new Random(129871942);
        int n = (int) 1e6;
        FID B = new FID(n);
        for (int i = 0; i < n; i++) {
            if (rnd.nextBoolean()) B.set(i);
        }
        B.build();
        for (int i = 0; i < n; i++) {
            B.select(rnd.nextBoolean(), rnd.nextInt(n));
        }
    }
}
