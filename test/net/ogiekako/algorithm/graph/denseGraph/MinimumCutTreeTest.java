package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;
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
            double[][] flow = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    Graph graph = new Graph(n);
//                    GraphAlgorithmTest.Dinic din = new GraphAlgorithmTest.Dinic(n);
                    for (int k = 0; k < m; k++) {
                        graph.addFlow(a[k], b[k], (int) cap[a[k]][b[k]]);
                        graph.addFlow(b[k], a[k], (int) cap[b[k]][a[k]]);
                    }
                    flow[i][j] = new MaxFlow(graph).maxFlow(i, j);
                }
            long start = System.currentTimeMillis();
            Graph graph = MinimumCutTree.minCutTree(cap);
            long time = System.currentTimeMillis() - start;
            System.out.println("time: " + time);
            maxTime = Math.max(maxTime, time);
            double[][] result = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    MaxFlow maxFlow = new MaxFlow(graph);
                    result[i][j] = maxFlow.maxFlow(i, j);
                    maxFlow.maxFlow(j, i, result[i][j]);
                }
            for (int i = 0; i < n; i++) {
                assertArrayEquals("" + i, flow[i], result[i], 1e-9);
            }
        }
        System.out.println("maxTime: " + maxTime);
    }
}
