package net.ogiekako.algorithm.graph.problems.test;

import junit.framework.Assert;
import net.ogiekako.algorithm.graph.problems.PathWidth;
import net.ogiekako.algorithm.utils.Permutation;
import org.junit.Test;

import java.util.Random;

public class PathWidthTest {
    @Test
    public void testPathWidth() {
        Random rnd = new Random(12349120498L);
        int n = 8;
        for (int i = 0; i < 100; i++) {
            boolean[][] graph = new boolean[n][n];
            for (int j = 0; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    graph[j][k] = graph[k][j] = rnd.nextBoolean();
                }
            }
            int exp = pathWidthStupid(graph);
            int res = PathWidth.pathWidth(graph);
            Assert.assertEquals(exp, res);
        }
    }

    @Test
    public void testPathWidth2() {
        Random rnd = new Random(12341289720498L);
        int n = 20;
        for (int i = 0; i < 10; i++) {
            boolean[][] graph = new boolean[n][n];
            for (int j = 0; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    graph[j][k] = graph[k][j] = rnd.nextBoolean();
                }
            }
            PathWidth.pathWidth(graph);
        }
    }

    private int pathWidthStupid(boolean[][] graph) {
        int n = graph.length;
        int[] is = new int[n];
        for (int i = 0; i < n; i++) {
            is[i] = i;
        }
        int[] nei = new int[n];
        for (int i = 0; i < n; i++) {
            nei[i] |= 1 << i;
            for (int j = 0; j < n; j++) {
                if (graph[i][j]) nei[i] |= 1 << j;
            }
        }
        int res = Integer.MAX_VALUE;
        do {
            int val = 0;
            for (int i = 0; i < n; i++) {
                int bag = 0;
                for (int j = 0; j <= i; j++) {
                    bag |= nei[is[j]];
                }
                for (int j = 0; j < i; j++) {
                    bag &= ~(1 << is[j]);
                }
                val = Math.max(val, Integer.bitCount(bag));
            }
            res = Math.min(res, val);
        } while (Permutation.nextPermutation(is));
        return res - 1;
    }
}
