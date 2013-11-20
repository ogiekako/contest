package net.ogiekako.algorithm.graph;

import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.graph.test.GraphGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class GraphAlgorithmTest {

    @Test
    public void testIsAcyclicDigraph() {
        int n = 50, m = 100;
        Graph graph = GraphGenerator.ACYCLIC.generate(n, m);
        Assert.assertTrue(GraphUtils.isAcyclic(graph));
        boolean[][] nei = GraphUtils.toBoolArray(graph);
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    nei[i][j] |= nei[i][k] && nei[k][j];
                }
        for (int i = 0; i < n; i++) {
            Assert.assertFalse(nei[i][i]);
        }
    }

    @Test
    public void testMaxFlow() throws Exception {
        int n = 10;
        Random rnd = new Random(124098124L);
        for (int iteration = 0; iteration < 5000; iteration++) {
            int[][] cap = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < i; j++) {
                    int p = rnd.nextInt(3);
                    if (p == 0) cap[i][j] = cap[j][i] = 2;
                    else if (p == 1) cap[i][j] = cap[j][i] = 300;
                }
            Graph graph = new Graph(n);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (cap[i][j] > 0) {
                        graph.add(new FlowEdge(i, j, cap[i][j]));
                    }
            int s = rnd.nextInt(n);
            int t = s; while (t == s) t = rnd.nextInt(n);
            long res = MaxFlow.maxFlow(graph, s, t);
            long exp = solve(cap, s, t);
            Assert.assertEquals(exp, res);
        }
    }

    private int solve(int[][] cap, int s, int t) {
        int n = cap.length;
        boolean[] used = new boolean[n];
        int flow = 0;
        while (dfs(s, t, cap, used)) {
            flow++;
            Arrays.fill(used, false);
        }
        return flow;
    }

    private boolean dfs(int v, int t, int[][] cap, boolean[] used) {
        if (v == t) return true;
        used[v] = true;
        for (int u = 0; u < used.length; u++) {
            if (!used[u] && cap[v][u] > 0 && dfs(u, t, cap, used)) {
                cap[v][u]--;
                cap[u][v]++;
                return true;
            }
        }
        return false;
    }
}
