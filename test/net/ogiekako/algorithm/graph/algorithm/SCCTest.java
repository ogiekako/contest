package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.SimpleEdge;
import net.ogiekako.algorithm.graph.test.GraphGenerator;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SCCTest {
    @Test
    public void testScc() throws Exception {
        GraphTester.test(new GraphTester.Generator<int[]>() {
            public int[] result(Graph graph) {
                return SCC.scc(graph);
            }

            public void assertCorrect(Graph graph, int[] comp) {
                if (graph.size() >= 1000) return;
                int n = graph.size();
                if (comp.length != graph.size()) throw new AssertionError();
                Set<Integer> set = new HashSet<Integer>();
                for (int i : comp) set.add(i);
                for (int i = 0; i < set.size(); i++) if (!set.contains(i)) throw new AssertionError();
                boolean[][] reachable = GraphUtils.toBoolArray(graph);
                for (int k = 0; k < n; k++)
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++) reachable[i][j] |= reachable[i][k] && reachable[k][j];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        boolean both = i == j || reachable[i][j] && reachable[j][i];
                        if (both != (comp[i] == comp[j])) throw new AssertionError();
                        if (reachable[i][j] && comp[i] > comp[j]) throw new AssertionError();
                    }
            }

            public boolean valid(Graph graph) {
                return true;
            }

            public Edge edge(int from, int to, Random rnd) {
                return new SimpleEdge(from, to);
            }
        });
    }


    @Test
    public void testSccWithComponents() {
        for (int o = 0; o < 50; o++) {
            int n = 50;
            int m = 100;
            Graph graph = GraphGenerator.ANY.generate(n, m);
            boolean[][] nei = GraphUtils.toBoolArray(graph);
            for (int i = 0; i < n; i++) nei[i][i] = true;
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) nei[i][j] |= nei[i][k] && nei[k][j];
            int[][] scc = SCC.sccWithComponents(graph).second;
            int count = 0;
            for (int[] is : scc) count += is.length;
            Assert.assertEquals(n, count);
            for (int[] comp : scc) {
                for (int v : comp) {
                    for (int u : comp) {
                        Assert.assertTrue(nei[v][u]);
                    }
                }
            }
            for (int i = 0; i < scc.length; i++)
                for (int j = 0; j < i; j++) {
                    for (int s : scc[j])
                        for (int t : scc[i]) {
                            Assert.assertFalse(nei[t][s]);
                        }
                }
        }
    }
}

