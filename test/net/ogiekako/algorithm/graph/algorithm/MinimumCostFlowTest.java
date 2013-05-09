package net.ogiekako.algorithm.graph.algorithm;
import junit.framework.Assert;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.flow.PrimalDual_double;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
public class MinimumCostFlowTest {
    @Test
    public void testMinimumCostCirculation() throws Exception {
        GraphTester.test(new GraphTester.Generator<Long>() {
            int counter = -1;
            public Long result(Graph graph, Random rnd) {
                counter++;
                MinimumCostFlow flow = new MinimumCostFlow(graph);
                return flow.minimumCostCirculation();
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
            int source
                    ,
                    sink;
            long maxFlow;
            public Long result(Graph graph, Random rnd) {
                counter++;
                flow = rnd.nextInt(T);
                source = 0; sink = graph.size() - 1;
                maxFlow = MaxFlow.maxFlow(graph, source, sink);
                MaxFlow.maxFlow(graph, sink, source, maxFlow);
                long addCost = 0;
                for (int v = 0; v < graph.size(); v++)
                    for (Edge e : graph.edges(v)) if (e instanceof FlowEdge) addCost += e.flow() * e.cost();
                MinimumCostFlow minimumCostFlow = new MinimumCostFlow(graph);
                long res = minimumCostFlow.minimumCostFlow(source, sink, flow);
                if (res == Long.MAX_VALUE) return res;
                return addCost + res;
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
                    long restFlow = MaxFlow.maxFlow(graph, source, sink);
                    Assert.assertEquals(0, restFlow);
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
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    private void assertNoNegativeCycle(Graph graph) {
        ShortestPath shortestPath = new ShortestPath(graph);
        for (int v = 0; v < graph.size(); v++)
            for (Iterator<Edge> i = graph.edges(v).iterator(); i.hasNext(); ) {
                if (i.next().residue() == 0)
                    i.remove();
            }
        long[] distance = shortestPath.bellmanFord();
        Assert.assertNotNull(distance);
    }
}
