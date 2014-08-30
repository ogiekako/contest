package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.*;
import net.ogiekako.algorithm.graph.test.GraphTester;
import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BridgeTest {

    @Test
    @Ignore()
    public void testBridge() {
        class Result {
            GraphTester.Generator<Result>()

            {
                public Result result (Graph graph, Random rnd){
                Bridge bridge = new Bridge((BidirectionalGraph) graph);
                int numComp = bridge.compute();
                return new Result(numComp, bridge.cut, bridge.bridges, bridge.comp);
            }

            public void assertCorrect(Graph graph, Result result) {
                if (graph.size() > 100) return;
                int n = graph.size();
                boolean[][] con2 = new boolean[n][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        Graph flowGraph = new Graph(n);
                        for (int k = 0; k < n; k++)
                            for (Edge e : graph.edges(k)) {
                                flowGraph.add(new FlowEdge(e.from(), e.to(), 1));
                            }
                        con2[i][j] = MaxFlow.maxFlow(flowGraph, i, j) >= 2;
                    }

                int[] expComp = new int[n];
                int expNumComp = 0;
                Arrays.fill(expComp, -1);
                for (int i = 0; i < n; i++)
                    if (expComp[i] < 0) {
                        for (int j = 0; j < n; j++) if (con2[i][j]) expComp[j] = expNumComp;
                        expNumComp++;
                    }

                Assert.assertEquals(expNumComp, result.numComp);

                Assert.assertArrayEquals(expComp, result.comp);

                boolean[][] nei = new boolean[n][n], denGraph = new boolean[n][n];
                for (int i = 0; i < n; i++)
                    for (Edge e : graph.edges(i)) {
                        nei[e.from()][e.to()] = nei[e.to()][e.from()] = true;
                        denGraph[e.from()][e.to()] = denGraph[e.to()][e.from()] = true;
                        boolean same = expComp[e.from()] == expComp[e.to()];
                        Assert.assertEquals(same, !result.bridges.contains(e));
                    }

                int numCon = components(denGraph);
                int[] expCut = new int[n];
                for (int i = 0; i < n; i++) {
                    boolean[][] newGraph = ArrayUtils.clone(denGraph);
                    boolean add = false;
                    for (int j = 0; j < n; j++) {
                        if (newGraph[i][j]) add = true;
                        newGraph[i][j] = newGraph[j][i] = false;
                    }
                    expCut[i] = components(newGraph) - (add ? 1 : 0) - numCon;
                }
                Assert.assertArrayEquals(expCut, result.cut);
            }

            private int components(boolean[][] graph) {
                graph = ArrayUtils.clone(graph);
                int n = graph.length;
                for (int k = 0; k < n; k++)
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            graph[i][j] |= graph[i][k] && graph[k][j];
                int numCon = 0;
                boolean[] visited = new boolean[n];
                for (int i = 0; i < n; i++)
                    if (!visited[i]) {
                        visited[i] = true;
                        for (int j = 0; j < n; j++) if (graph[i][j]) visited[j] = true;
                        numCon++;
                    }
                return numCon;
            }

            public boolean valid(Graph graph) {
                return graph instanceof BidirectionalGraph;
            }

            public Edge edge(int from, int to, Random rnd) {
                return new SimpleEdge(from, to);
            }
        } int numComp;
        int[] cut;
        Set<Edge> bridges;
        int[] comp;

        public Result( int numComp, int[] cut, Set<Edge > bridges,int[] comp){
            this.numComp = numComp;
            this.cut = cut;
            this.bridges = bridges;
            this.comp = comp;
        }
        }
        GraphTester.test(new);
    }

    @Test
    public void testConnectedComponent() {
        String[] INPUT = new String[]{
                "6 7 " +
                        "0 1 1 2 2 0 0 3 3 4 3 5 5 0",

                "1 0",

                "23 32 " +
                        "13 14 " +
                        "14 15 " +
                        "14 3 " +
                        "4 2 " +
                        "2 17 " +
                        "17 18 " +
                        "16 18 " +
                        "2 18 " +
                        "1 2 " +
                        "22 1 " +
                        "2 0 " +
                        "19 21 " +
                        "9 8 " +
                        "8 6 " +
                        "6 5 " +
                        "6 7 " +
                        "12 8 " +
                        "10 13 " +
                        "8 13 " +
                        "8 10 " +
                        "10 12 " +
                        "12 11 " +
                        "15 4 " +
                        "21 20 " +
                        "20 0 " +
                        "0 21 " +
                        "19 20 " +
                        "0 19 " +
                        "3 4 " +
                        "11 10 " +
                        "5 7 " +
                        "2 22"
        };
        int[][] CUT = new int[][]{
                {1, 0, 0, 1, 0, 0},
                {0},
                {1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0}
        };
        String[] ART = new String[]{
                "oxxoxx",
                "x",
                "oxoxoxoxoxxxxooxxxoxxxx"
        };
        String[] BR = new String[]{
                "xxxxoxx",
                "",
                "oxxoxxoxxxoxooxxxxxxxxxxxxxxxxxx"
        };
        int[] RES = new int[]{
                2,
                1,
                7
        };
        int[][] COMP = new int[][]{
                {0, 0, 0, 0, 1, 0},
                {0},
                {0, 1, 1, 2, 2, 3, 3, 3, 4, 5, 4, 4, 4, 4, 2, 2, 6, 1, 1, 0, 0, 0, 1}
        };
        for (int o = 0; o < INPUT.length; o++) {
            String input = INPUT[o];
            Scanner in = new Scanner(input);

            int n = in.nextInt();
            int m = in.nextInt();
            int[] xs = new int[m], ys = new int[m];
            for (int i = 0; i < m; i++) {
                xs[i] = in.nextInt();
                ys[i] = in.nextInt();
            }
            Assert.assertTrue(!in.hasNext());

            BidirectionalGraph graph = new BidirectionalGraph(n);
            Edge[] es2 = new Edge[m * 2];
            for (int i = 0; i < m; i++) {
                es2[i * 2] = new SimpleEdge(xs[i], ys[i]);
                es2[i * 2 + 1] = es2[i * 2].transposed();
                graph.add(es2[i * 2]);
            }

            Bridge bridge = new Bridge(graph);
            int exp = bridge.compute();
            Assert.assertEquals(RES[o], exp);
            int[] cut = bridge.cut;
            int[] comp = bridge.comp;
            for (int i = 0; i < n; i++) {
                Assert.assertEquals(ART[o].charAt(i) == 'o', cut[i] > 0);
                Assert.assertEquals(CUT[o][i], cut[i]);
                Assert.assertEquals(COMP[o][i], comp[i]);
            }

            for (int i = 0; i < m; i++) {
                boolean br = BR[o].charAt(i) == 'o';
                Assert.assertEquals(br, bridge.bridges.contains(es2[i * 2]) || bridge.bridges.contains(es2[i * 2 + 1]));
            }

        }
    }
}
