package net.ogiekako.algorithm.graph.algorithm;

import junit.framework.Assert;
import net.ogiekako.algorithm.graph.*;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class EulerPathTest {

    @Test
    public void testDirected() {
        GraphTester.test(new GraphTester.Generator<List<Edge>>() {
            public List<Edge> result(Graph graph, Random rnd) {
                return new EulerPath().directedEulerPath(graph);
            }

            public void assertCorrect(Graph graph, List<Edge> result) {
                int n = graph.size();
                int[] out = new int[n];
                for (int i = 0; i < n; i++)
                    for (Edge e : graph.edges(i)) {
                        out[i]++;
                        out[e.to()]--;
                    }
                int odd = 0;
                for (int i = 0; i < n; i++) {
                    if (out[i] != 0) odd++;
                    if (Math.abs(out[i]) > 1) {
                        Assert.assertNull(result);
                        return;
                    }
                }
                if (odd > 2) {
                    Assert.assertNull(result);
                    return;
                }
                if (n <= 200) {
                    boolean[][] reachable = GraphUtils.toBoolArray(graph);
                    for (int k = 0; k < n; k++)
                        for (int i = 0; i < n; i++)
                            for (int j = 0; j < n; j++) reachable[i][j] |= reachable[i][k] && reachable[k][j];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            if (i != j && !reachable[i][j] && !reachable[j][i]) {
                                Assert.assertNull(result); return;
                            }
                    Assert.assertNotNull(result);
                    Assert.assertEquals(GraphUtils.edgeCount(graph), result.size());
                }
                if (result == null) return;
                HashSet<Edge> set = new HashSet<Edge>();
                for (Edge e : result) set.add(e);
                Assert.assertEquals(result.size(), set.size());
                for (int i = 0; i < result.size() - 1; i++) {
                    Assert.assertEquals(result.get(i).to(), result.get(i + 1).from());
                }
            }

            public boolean valid(Graph graph) {
                return !(graph instanceof BidirectionalGraph);
            }

            public Edge edge(int from, int to, Random rnd) {
                return new SimpleEdge(from, to);
            }
        });
    }

    @Test
    public void testUndirected() {
        GraphTester.test(new GraphTester.Generator<List<Edge>>() {
            public List<Edge> result(Graph graph, Random rnd) {
                return new EulerPath().undirectedEulerPath((BidirectionalGraph) graph);
            }

            public void assertCorrect(Graph graph, List<Edge> result) {
                int n = graph.size();
                int[] deg = new int[n];
                for (int i = 0; i < n; i++) deg[i] = graph.edges(i).size();
                int odd = 0;
                for (int i = 0; i < n; i++) {
                    if (deg[i] % 2 != 0) odd++;
                }
                if (odd > 2) {
                    Assert.assertNull(result);
                    return;
                }

                if (n <= 200) {
                    boolean[][] reachable = GraphUtils.toBoolArray(graph);
                    for (int k = 0; k < n; k++)
                        for (int i = 0; i < n; i++)
                            for (int j = 0; j < n; j++) reachable[i][j] |= reachable[i][k] && reachable[k][j];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            if (i != j && !reachable[i][j]) {
                                Assert.assertNull(result);
                                return;
                            }
                    Assert.assertNotNull(result);
                    Assert.assertEquals(GraphUtils.edgeCount(graph), result.size());
                }
                if (result == null) return;
                HashSet<Edge> set = new HashSet<Edge>();
                for (Edge e : result) {
                    set.add(e);
                    set.add(e.transposed());
                }
                Assert.assertEquals(result.size() * 2, set.size());
                for (int i = 0; i < result.size() - 1; i++) {
                    Assert.assertEquals(result.get(i).to(), result.get(i + 1).from());
                }
            }

            public boolean valid(Graph graph) {
                return graph instanceof BidirectionalGraph;
            }

            public Edge edge(int from, int to, Random rnd) {
                return new SimpleEdge(from, to);
            }
        });
    }
}
