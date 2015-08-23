package net.ogiekako.algorithm.graph.flow;
import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class GlobalMinimumCutTest {
    @Test
    public void testSolve() throws Exception {
        GraphTester.test(new GraphTester.Generator<Double>() {
            public Double result(Graph graph, Random rnd) {
                return new GlobalMinimumCut((UndirectedGraph) graph).compute();
            }

            public void assertCorrect(Graph graph, Double result) {
                double exp = Double.POSITIVE_INFINITY;
                for (int s = 0; s < graph.size(); s++)
                    for (int t = 0; t < s; t++) {
                        MaxFlow maxFlow = new MaxFlow(graph);
                        double flow = maxFlow.maxFlow(s, t);
                        exp = Math.min(exp, flow);
                        maxFlow.maxFlow(t, s, flow);// make a circular flow
                    }
                Assert.assertEquals(exp, result, 1e-9);
            }
            public boolean valid(Graph graph) {
                if (graph.isDigraph()) return false;
                return graph.size() < 300;
            }
            public Edge edge(int from, int to, Random rnd) {
                return new FlowEdge(from, to, rnd.nextInt(10));
            }
        });
    }
}
