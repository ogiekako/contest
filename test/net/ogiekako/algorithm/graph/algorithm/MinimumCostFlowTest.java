package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.*;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
            int T = (int) 1e4;
            int source, sink;
            double maxFlow;

            public Long result(Graph graph, Random rnd) {
                if (graph instanceof UndirectedGraph) return 0L;
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
                double INF = 1e9;
                Edge added = minimumCostFlow.graph.addFlow(sink, source, (double) flow, -INF);
                double res = minimumCostFlow.minimumCostCirculation() + INF * (double) flow;
                minimumCostFlow.graph.remove(added);

                if (added.flow() < flow) return Long.MAX_VALUE;
                return (long) (addCost + res);
            }

            public void assertCorrect(Graph graph, Long result) {
                if (graph instanceof UndirectedGraph) return;
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

class PrimalDual_double {
    static final double INF = 1L << 61;
    int n;
    V[] vs;
    V s, t;

    public PrimalDual_double(int nn) {
        n = nn;
        vs = new V[n];
        for (int i = 0; i < n; i++)
            vs[i] = new V(i);
    }

    public void make(int from, int to, int cap, double cost) {
        E e = new E(vs[to], cap, cost);
        E r = new E(vs[from], 0, -cost);
        e.rev = r;
        r.rev = e;
        vs[from].es.add(e);
        vs[to].es.add(r);
    }

    public double minCostFlow(int from, int to, int flow) {
        if (from == to) return 0;
        s = vs[from];
        t = vs[to];
        double cost = 0;
        while (flow > 0) {
            for (V v : vs)
                v.cost = INF;
            Queue<E> q = new PriorityQueue<E>();
            s.cost = 0;
            s.bef = null;
            t.bef = null;
            q.add(new E(s, 0, 0));
            while (!q.isEmpty()) {
                V v = q.poll().to;
                if (v == t) break;
                for (E e : v.es) {
                    if (e.cap > 0 && e.to.cost > e.cost + v.cost) {
                        e.to.cost = e.cost + v.cost;
                        e.to.bef = e;
                        q.add(new E(e.to, 0, e.to.cost));
                    }
                }
            }
            if (t.bef == null) return -1;
            double min = flow;
            for (E e = t.bef; e != null; e = e.rev.to.bef) {
                min = Math.min(min, e.cap);
            }
            for (E e = t.bef; e != null; e = e.rev.to.bef) {
                e.cap -= min;
                cost += e.cost * min;
                e.rev.cap += min;
            }
            flow -= min;
        }
        return cost;
    }
    class V {
        int id;
        ArrayList<E> es = new ArrayList<E>();
        E bef;
        double cost;

        V(int id) {
            this.id = id;
        }

    }

    class E implements Comparable<E> {
        E rev;
        V to;
        int cap;
        double cost;

        E(V to, int cap, double cost) {
            this.to = to;
            this.cap = cap;
            this.cost = cost;
        }

        public int compareTo(E o) {
            return Double.compare(cost, o.cost);
        }
    }
}