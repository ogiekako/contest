package net.ogiekako.algorithm.misc.syakutori;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SyakutoriTest {

    @Test
    public void testMinDist() {
        Random rand = new Random(1203981092L);
        for (int i = 0; i < 1000; i++) {
            int n = rand.nextInt(1000) + 1;
            boolean[] as = new boolean[n];
            boolean[] bs = new boolean[n];
            for (int j = 0; j < n; j++) as[j] = rand.nextInt(n) < 3;
            for (int j = 0; j < n; j++) bs[j] = rand.nextInt(n) < 3;
            int exp = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++) if (as[j] && bs[k]) exp = Math.min(exp, Math.abs(j - k));
            int res = new Syakutori().minDist(as, bs);
            assertEquals(exp, res);
        }
    }

}
