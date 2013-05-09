package net.ogiekako.algorithm.graph.denseGraph;
import junit.framework.Assert;
import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Test;

import java.util.Random;
public class HungarianTest {
    @Test
    public void sample() {
        int[][] graph = new int[][]{
                {-1, -2},
                {-2, -4}
        };
        int res = Hungarian.maximumPerfectMatching(graph);
        Assert.assertEquals(-4, res);
    }
    @Test
    public void testMaximumPerfectMatching() throws Exception {
        Random rnd = new Random(1410248L);
        for (int iter = 0; iter < 50; iter++) {
            int n = rnd.nextInt(8) + 1;
            int[][] graph = new int[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = rnd.nextInt(2001) - 1000;
            int res = Hungarian.maximumPerfectMatching(graph);
            int exp = Integer.MIN_VALUE;
            int[] order = ArrayUtils.createOrder(n);
            do {
                int score = 0;
                for (int i = 0; i < n; i++) score += graph[i][order[i]];
                exp = Math.max(exp, score);
            } while (ArrayUtils.nextPermutation(order));
            Assert.assertEquals(exp, res);
        }
    }
    @Test(timeout = 1000)
    public void testMaximumPerfectMatchingSpeed() throws Exception {
        Random rnd = new Random(1410248L);
        for (int iter = 0; iter < 10; iter++) {
            System.out.println(iter);
            int n = 200;
            int[][] graph = new int[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = rnd.nextInt(2001) - 1000;
            Hungarian.maximumPerfectMatching(graph);
        }
    }
}
