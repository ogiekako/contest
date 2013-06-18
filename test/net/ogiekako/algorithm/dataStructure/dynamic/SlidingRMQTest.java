package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.SlidingRMQ;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SlidingRMQTest {

    @Test
    public void testSlidingRMQ() {
        Random rnd = new Random(102984091824L);
        for (int o = 0; o < 1000; o++) {
            int n = rnd.nextBoolean() ? rnd.nextInt(10) + 1 : rnd.nextInt(100000);
            int m = rnd.nextInt(1000);
            int k = rnd.nextBoolean() ? rnd.nextInt(1000000) : rnd.nextInt(10) + 1;
            int[] as = new int[m], bs = new int[m];
            for (int i = 0; i < m; i++) {
                as[i] = i == 0 ? 0 : as[i - 1] + rnd.nextInt(n / m + 2);
                bs[i] = i == 0 ? 1 : bs[i - 1] + rnd.nextInt(n / m + 2);
                if (as[i] > bs[i] || bs[i] > n) {
                    i--;
                }
            }
            long[] is = new long[n];
            for (int i = 0; i < n; i++) is[i] = rnd.nextInt(k);
            long[] res = SlidingRMQ.slidingRMQ(is, as, bs);
            long[] exp = solveStupid(is, as, bs);
            Assert.assertArrayEquals(exp, res);
        }
    }

    private long[] solveStupid(long[] is, int[] as, int[] bs) {
        long[] res = new long[as.length];
        for (int i = 0; i < as.length; i++) {
            long value = Long.MAX_VALUE;
            for (int j = as[i]; j < bs[i]; j++) value = Math.min(value, is[j]);
            res[i] = value;
        }
        return res;
    }

}
