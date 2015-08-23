package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.flow.PrimalDual_double;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class MinimumCostFlowTest {
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    @Test
    public void testMinimumCostCirculation() throws Exception {
        GraphTester.test(new GraphTester.Generator<Long>() {
            int counter = -1;

            public Long result(Graph graph, Random rnd) {
                counter++;
                MinimumCostFlow flow = new MinimumCostFlow(graph);
                double res = flow.minimumCostCirculation();
                if (res == Double.POSITIVE_INFINITY) return Long.MAX_VALUE;
                return (long) res;
            }

            public void assertCorrect(Graph graph, Long result) {
                long exp = 0;
                for (int i = 0; i < graph.size(); i++)
                    for (Edge e : graph.edges(i)) {
                        if (e instanceof FlowEdge) {
                            exp += e.cost() * e.flow();
                        }
                    }
                Assert.assertEquals(exp, (long) result);
                assertNoNegativeCycle(graph);
            }

            public boolean valid(Graph graph) {
                int n = graph.size(), m = GraphUtils.edgeCount(graph);
                if (m > 1000) return false;
                if (n * m >= 500000) return false;
                return true;
            }

            public Edge edge(int from, int to, Random rnd) {
                int T = (int) 1e7;
                long cap = rnd.nextInt(T), cost = rnd.nextInt(T);
                if ((counter % 3) > 0 && rnd.nextBoolean()) cost = -cost;
                return new FlowEdge(from, to, cap, cost);
            }
        });
    }

    @Test
    public void testMinimumCostFlow() throws Exception {
        GraphTester.test(new GraphTester.Generator<Long>() {
            int counter = -1;
            long flow;
            int T = (int) 1e6;
            int source, sink;
            double maxFlow;

            public Long result(Graph graph, Random rnd) {
                counter++;
                flow = rnd.nextInt(T);
                source = 0;
                sink = graph.size() - 1;
                maxFlow = new MaxFlow(graph).maxFlow(source, sink);
                new MaxFlow(graph).maxFlow(sink, source, maxFlow);
                long addCost = 0;
                for (int v = 0; v < graph.size(); v++)
                    for (Edge e : graph.edges(v)) if (e instanceof FlowEdge) addCost += e.flow() * e.cost();
                MinimumCostFlow minimumCostFlow = new MinimumCostFlow(graph);
                double res = minimumCostFlow.negativeCancellation(source, sink, flow);
                if (res == Double.POSITIVE_INFINITY) return Long.MAX_VALUE;
                return (long) (addCost + res);
            }

            public void assertCorrect(Graph graph, Long result) {
                boolean testByPD = true;
                PrimalDual_double pd = new PrimalDual_double(graph.size());
                for (int i = 0; i < graph.size(); i++)
                    for (Edge e : graph.edges(i))
                        if (e instanceof FlowEdge) {
                            if (e.cost() < 0) testByPD = false;
                            pd.make(i, e.to(), (int) (e.flow() + e.residue()), e.cost());
                        }
                if (testByPD) {
                    double exp = pd.minCostFlow(source, sink, (int) flow);
                    if (exp == -1) Assert.assertEquals(Long.MAX_VALUE, (long) result);
                    else Assert.assertEquals((long) exp, (long) result);
                }
                long sending = 0;
                for (int v = 0; v < graph.size(); v++)
                    for (Edge e : graph.edges(v))
                        if (e instanceof FlowEdge) {
                            if (e.from() == source) sending += e.flow();
                            if (e.to() == source) sending -= e.flow();
                        }
                if (result == Long.MAX_VALUE) {
                    double restFlow = new MaxFlow(graph).maxFlow(source, sink);
                    Assert.assertEquals(0.0, restFlow, 1e-9);
                    Assert.assertTrue(maxFlow < flow);
                    return;
                }
                if (source == sink) flow = 0;
                Assert.assertEquals(sending, flow);
                double exp = 0;
                for (int i = 0; i < graph.size(); i++)
                    for (Edge e : graph.edges(i)) {
                        if (e instanceof FlowEdge) {
                            exp += e.cost() * e.flow();
                        }
                    }
                Assert.assertEquals((long) exp, (long) result);
                assertNoNegativeCycle(graph);
            }

            public boolean valid(Graph graph) {
                int n = graph.size(), m = GraphUtils.edgeCount(graph);
                if (m > 1000) return false;
                if (n * m >= 500000) return false;
                return true;
            }

            public Edge edge(int from, int to, Random rnd) {
                long cap = rnd.nextInt(T), cost = rnd.nextInt(T);
                if ((counter % 3) > 0 && rnd.nextBoolean()) cost = -cost;
                return new FlowEdge(from, to, cap, cost);
            }
        });
    }

    @Test(timeout = 100)
    public void primalDualShouldRobustForNumericalError() {
        Graph G = new Graph(4);
        G.addFlow(0, 1, 1, 2.0 / 3.0);
        G.addFlow(0, 2, 1, 1.0 / 3.0);
        G.addFlow(1, 2, 1, 0.0);
        G.addFlow(1, 3, 1, 1.0 / 3.0);
        G.addFlow(2, 3, 1, 2.0 / 3.0);
        double res = new MinimumCostFlow(G).primalDual(0, 3, 2);
        Assert.assertEquals(2.0, res, 1e-9);
    }

    private void assertNoNegativeCycle(Graph graph) {
        for (int v = 0; v < graph.size(); v++)
            for (Iterator<Edge> i = graph.edges(v).iterator(); i.hasNext(); ) {
                if (i.next().residue() == 0)
                    i.remove();
            }
        Assert.assertFalse(new BellmanFord(graph).hasNegativeCycle());
    }
}
