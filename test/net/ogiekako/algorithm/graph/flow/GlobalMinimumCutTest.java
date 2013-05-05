package net.ogiekako.algorithm.graph.flow;
import junit.framework.Assert;
import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Test;

import java.util.Random;
/**
 * Created with IntelliJ IDEA.
 * User: ogiekako
 * Date: 13/04/28
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */
public class GlobalMinimumCutTest {
    @Test
    public void testSolve() throws Exception {
        GraphTester.test(new GraphTester.Generator<Long>() {
            public Long result(Graph graph, Random rnd) {
                return new GlobalMinimumCut((BidirectionalGraph) graph).compute();
            }
            public void assertCorrect(Graph graph, Long result) {
                long exp = Long.MAX_VALUE;
                for (int s = 0; s < graph.size(); s++)
                    for (int t = 0; t < s; t++) {
                        long flow = net.ogiekako.algorithm.graph.algorithm.MaxFlow.maxFlow(graph, s, t);
                        exp = Math.min(exp, flow);
                        net.ogiekako.algorithm.graph.algorithm.MaxFlow.maxFlow(graph, t, s, flow);// make a circular flow
                    }
                Assert.assertEquals(exp, (long) result);
            }
            public boolean valid(Graph graph) {
                if (graph.isDigraph()) return false;
                return graph.size() < 300;
            }
            public Edge edge(int from, int to, Random rnd) {
                return new FlowEdge(from, to, rnd.nextInt((int) 10));
            }
        });
    }
}
