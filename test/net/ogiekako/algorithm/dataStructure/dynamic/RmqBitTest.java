package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.tree.RmqBit;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RmqBitTest {

    @Test
    public final void testRMQ_BIT() {
        int n = 100;
        RmqBit rmq = new RmqBit(n);
        RMQStupid rmqStupid = new RMQStupid(n);
        Random r = new Random(1093810283L);
        for (int o = 0; o < 1000; o++) {
            System.out.print(".");
            int q = r.nextInt(2);
            debug(q);
            if (q == 0) {
                int i = r.nextInt(n), v = r.nextInt();
//				debug(i,v);
                rmq.update(i, v);
                rmqStupid.update(i, v);
            } else {
                int right = r.nextInt(n) + 1;
                debug(right);
                long res = rmq.getMin(right);
                long exp = rmqStupid.getMin(right);
                assertEquals(Arrays.toString(rmqStupid.is), exp, res);
            }
        }
    }
    private void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    class RMQStupid {
        long[] is;
        public RMQStupid(int n) {
            is = new long[n];
            for (int i = 0; i < n; i++) is[i] = 0;
        }
        public long getMin(int right) {
            long res = Long.MAX_VALUE;
            for (int i = 0; i < right; i++) res = Math.min(res, is[i]);
            return res;
        }
        public void update(int i, int v) {
            is[i] = Math.min(is[i], v);
        }

    }
}
