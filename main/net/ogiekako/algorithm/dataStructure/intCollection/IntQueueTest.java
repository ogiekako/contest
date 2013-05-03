package net.ogiekako.algorithm.dataStructure.intCollection;

import junit.framework.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class IntQueueTest {
    @Test
    public void testManual() throws Exception {
        IntQueue que = new IntQueue(2);
        Assert.assertTrue(que.isEmpty());
        que.offer(0);
        que.offer(1);
        que.offer(2);
        int v = que.poll();
        Assert.assertEquals(0, v);
        Assert.assertFalse(que.isEmpty());// 1, 2
        v = que.poll();
        Assert.assertEquals(1, v);
        Assert.assertFalse(que.isEmpty());// 2
        v = que.poll();
        Assert.assertEquals(2, v);
        Assert.assertTrue(que.isEmpty());
    }

    @Test
    public void testRandom() throws Exception {
        IntQueue que = new IntQueue();
        Queue<Integer> que2 = new LinkedList<Integer>();
        Random rnd = new Random(120812L);
        int n = 100000;
        for (int i = 0; i < n; i++) {
            if (rnd.nextBoolean()) {
                int v = rnd.nextInt();
                que2.offer(v);
                que.offer(v);
            } else {
                if (que2.isEmpty()) {
                    Assert.assertTrue(que.isEmpty());
                } else {
                    int v = que2.poll();
                    int u = que.poll();
                    Assert.assertEquals(v, u);
                }
            }
            Assert.assertEquals(que.size(), que2.size());
        }
    }
}
