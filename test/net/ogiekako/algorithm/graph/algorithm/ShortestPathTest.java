package net.ogiekako.algorithm.graph.algorithm;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.WeightedEdge;
import net.ogiekako.algorithm.graph.test.GraphTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
public class ShortestPathTest {
    int[] cnt = new int[3];
    @Test
    public void test() {
        GraphTester.test(new GraphTester.Generator<long[]>() {
            int counter = 0;
            public long[] result(Graph graph) {
                counter++;
                System.out.println(counter + " " + graph.size() + "  " + GraphUtils.edgeCount(graph));
                return new ShortestPath(graph).compute(0);
            }
            public void assertCorrect(Graph graph, long[] result) {
                if (graph.size() > 500) return;
                int n = graph.size();
                long[][] dist = new long[n][n];
                for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) dist[i][j] = Long.MAX_VALUE;
                for (int i = 0; i < n; i++) dist[i][i] = 0;
                for (int i = 0; i < n; i++)
                    for (Edge e : graph.edges(i)) dist[i][e.to()] = Math.min(dist[i][e.to()], e.cost());
                for (int k = 0; k < n; k++)
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            if (dist[i][k] < Long.MAX_VALUE && dist[k][j] < Long.MAX_VALUE)
                                dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                for (int i = 0; i < n; i++)
                    if (dist[i][i] < 0) {
                        dist[0] = null; break;
                    }
                Assert.assertArrayEquals(dist[0], result);
            }
            public boolean valid(Graph graph) {
                int n = graph.size(), m = GraphUtils.edgeCount(graph);
                boolean hasNegative = false;
                for (int i = 0; i < n; i++) for (Edge e : graph.edges(i)) if (e.cost() < 0) hasNegative = true;
                return !hasNegative || (long) n * m <= 1e7;
            }
            public Edge edge(int from, int to, Random rnd) {
                int cost;
                if (counter % 3 == 0) cost = rnd.nextInt((int) 1e9);
                else if (counter % 3 == 1) cost = rnd.nextInt();
                else {
                    cost = rnd.nextInt(10) == 0 ? -rnd.nextInt((int) 1e9) : rnd.nextInt((int) 1e9);
                }
                return new WeightedEdge(from, to, cost);
            }
        });
    }
}
