package net.ogiekako.algorithm.graph.algorithm;

import junit.framework.Assert;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.WeightedEdge;
import net.ogiekako.algorithm.graph.graphDouble.GraphUtils;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DirectedMinimumSpanningTreeTest {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    @Test
    public void test() {
        GraphTester.test(new GraphTester.Generator<Long>() {
            public Long result(Graph graph) {
                Graph nGraph = new Graph(graph.size());
                for (int i = 0; i < graph.size(); i++)
                    for (Edge e : graph.edges(i))
                        nGraph.addWeighted(e.from(), e.to(), e.cost());
                return new DirectedMinimumSpanningTree().compute(nGraph, 0);
            }

            public void assertCorrect(Graph graph, Long result) {
                if (graph.size() > 14) return;
                int n = graph.size();
                V[] vs = new V[n];
                for (int i = 0; i < n; i++) vs[i] = new V(i);
                for (int i = 0; i < n; i++)
                    for (Edge e : graph.edges(i)) {
                        vs[i].es.add(new E(vs[e.to()], e.cost()));
                    }
                Assert.assertEquals(solveStupid(vs, 0), (long) result);
            }


            public boolean valid(Graph graph) {
                long edges = GraphUtils.edgeCount(graph);
                return edges * graph.size() < 1e7;
            }

            public Edge edge(int from, int to, Random rnd) {
                return new WeightedEdge(from, to, rnd.nextInt((int) 1e9));
            }
        });
    }

    @Test
    public final void testArborescence() {
        DirectedMinimumSpanningTree dmst = new DirectedMinimumSpanningTree();
        for (int o = 0; o < 100; o++) {
            int n = 14;
            Random r = new Random(1029210984L);
            int[][] cost = new int[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) cost[i][j] = r.nextInt(1000) + 1;
            V[] vs = new V[n];
            for (int i = 0; i < n; i++) vs[i] = new V(i);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) if (i != j) vs[i].es.add(new E(vs[j], cost[i][j]));
            long exp = solveStupid(vs, 0);

            Graph graph = new Graph(n);
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (i != j) graph.addWeighted(i, j, cost[i][j]);
            long res = dmst.compute(graph, 0);
            assertEquals(exp, res);
        }
    }

    private long solveStupid(V[] vs, int root) {
        int n = vs.length;
        long[] dp = new long[1 << n];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[1 << root] = 0;
        for (int i = 1; i < n + 1; i++)
            for (int j = 0; j < 1 << n; j++)
                if (Integer.bitCount(j) == i) if (dp[j] < Long.MAX_VALUE) {
                    for (int k = 0; k < n; k++)
                        if ((j >> k & 1) == 1) for (E e : vs[k].es)
                            if ((j >> e.to.id & 1) == 0) {
                                dp[j | 1 << e.to.id] = Math.min(dp[j | 1 << e.to.id], dp[j] + e.cost);
                            }
                }
        return dp[(1 << n) - 1];
    }

    class V {
        List<E> es = new ArrayList<E>();
        int id;

        public V(int id) {
            this.id = id;
        }
    }

    class E {
        V to;
        long cost;

        public E(V to, long cost) {
            this.to = to; this.cost = cost;
        }
    }

}
