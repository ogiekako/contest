package net.ogiekako.algorithm.graph.algorithm;
import junit.framework.Assert;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.testing.GraphTester;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class MaxFlowTest {
    @Test
    public void test() {
        GraphTester.test(new GraphTester.Generator<Double>() {
            int source;
            int sink;

            public Double result(Graph graph, Random rnd) {
                source = 0;
                sink = graph.size() - 1;
                return new MaxFlow(graph).maxFlow(source, sink);
            }

            public void assertCorrect(Graph graph, Double result) {
                if (GraphUtils.edgeCount(graph) > 100000) return;
                double reversed = new MaxFlow(graph).maxFlow(sink, source, result);
                Assert.assertEquals(result, reversed, 1e-9);
                double exp = fordFulkerson(graph, source, sink);
                Assert.assertEquals(exp, result, 1e-9);
            }
            public boolean valid(Graph graph) {
                return true;
            }
            public Edge edge(int from, int to, Random rnd) {
                return new FlowEdge(from, to, rnd.nextInt((int) 1e9));
            }
        });
    }

    // http://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm
    double fordFulkerson(Graph graph, int source, int sink) {
        if (source == sink) return Double.POSITIVE_INFINITY;
        long flow = 0;
        int n = graph.size();
        Edge[] edgeTo = new Edge[n];
        int[] visited = new int[n];
        for (int flag = 1; ; flag++) {
            Queue<Integer> que = new LinkedList<Integer>();
            que.offer(source);
            visited[source] = flag;
            while (!que.isEmpty()) {
                int v = que.poll();
                for (Edge e : graph.edges(v)) {
                    if (e.residue() > 0 && visited[e.to()] < flag) {
                        visited[e.to()] = flag;
                        que.offer(e.to());
                        edgeTo[e.to()] = e;
                    }
                }
            }
            if (visited[sink] < flag) return flow;
            double pushFlow = Double.POSITIVE_INFINITY;
            for (Edge e = edgeTo[sink]; e != null; e = edgeTo[e.from()]) {
                pushFlow = Math.min(pushFlow, e.residue());
            }
            for (Edge e = edgeTo[sink]; e != null; e = edgeTo[e.from()]) {
                e.pushFlow(pushFlow);
            }
            flow += pushFlow;
        }
    }
}
