package net.ogiekako.algorithm.graph.denseGraph;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;
import net.ogiekako.algorithm.graph.flow.Dinic;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
public class MinimumCutTreeTest {

    @Test
    public void testCutTree() {
        Random random = new Random(120981029830L);
        long maxTime = 0;
        for (int iter = 0; iter < 10; iter++) {
            int n = 30;
            int m = 100;
            long[][] cap = new long[n][n];
            int[] a = new int[m];
            int[] b = new int[m];
            for (int i = 0; i < m; ) {
                a[i] = random.nextInt(n);
                b[i] = random.nextInt(n);
                if (a[i] == b[i] || cap[a[i]][b[i]] > 0) continue;
                cap[b[i]][a[i]] = cap[a[i]][b[i]] = random.nextInt(1000) + 1;
                i++;
            }
            long[][] flow = new long[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    Dinic din = new Dinic(n, m * 2);
                    for (int k = 0; k < m; k++) {
                        din.make(a[k], b[k], (int) cap[a[k]][b[k]]);
                        din.make(b[k], a[k], (int) cap[b[k]][a[k]]);
                    }
                    flow[i][j] = din.maxFlow(i, j);
                    if (flow[i][j] == Integer.MAX_VALUE) flow[i][j] = Long.MAX_VALUE;
                }
            long start = System.currentTimeMillis();
            Graph graph = MinimumCutTree.minCutTree(cap);
            long time = System.currentTimeMillis() - start;
            System.out.println("time: " + time);
            maxTime = Math.max(maxTime, time);
            long[][] result = new long[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    result[i][j] = MaxFlow.maxFlow(graph, i, j);
                    MaxFlow.maxFlow(graph, j, i, result[i][j]);
                }
            for (int i = 0; i < n; i++) {
                assertArrayEquals(flow[i], result[i]);
            }
        }
        System.out.println("maxTime: " + maxTime);
    }
}
