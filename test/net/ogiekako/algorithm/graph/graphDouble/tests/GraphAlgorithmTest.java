package net.ogiekako.algorithm.graph.graphDouble.tests;

import net.ogiekako.algorithm.graph.graphDouble.GraphAlgorithm;
import net.ogiekako.algorithm.graph.graphDouble.GraphD;
import net.ogiekako.algorithm.graph.graphDouble.GraphDUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:13
 * To change this template use File | Settings | File Templates.
 */
public class GraphAlgorithmTest {
    @Test
    public void testMinDistanceDijkstra() throws Exception {

    }

    @Test
    public void testDijkstra() throws Exception {

    }

    @Test
    public void testScc() {
        for (int o = 0; o < 50; o++) {
            int n = 50;
            int m = 100;
            GraphD graph = GraphGenerator.ANY.generate(n, m);
            boolean[][] nei = GraphDUtils.toBoolArray(graph);
            for (int i = 0; i < n; i++) nei[i][i] = true;
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) nei[i][j] |= nei[i][k] && nei[k][j];
            int[][] scc = GraphAlgorithm.scc(graph).second;
            int count = 0;
            for (int[] is : scc) count += is.length;
            Assert.assertEquals(n, count);
            for (int i = 0; i < scc.length; i++) {
                for (int j = 0; j < scc[i].length; j++) {
                    for (int k = 0; k < scc[i].length; k++) {
                        Assert.assertTrue(nei[scc[i][j]][scc[i][k]]);
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
        for (int o = 0; o < 5; o++) {
            int n = 100000;
            int m = 100000;
            GraphD graph = GraphGenerator.ANY.generate(n, m);
            long start = System.currentTimeMillis();
            GraphAlgorithm.scc(graph);
            long time = System.currentTimeMillis() - start;
            Assert.assertTrue(time < 1000);
        }
    }

    @Test
    public void testIsAcyclicDigraph() {
        int n = 50, m = 100;
        GraphD graph = GraphGenerator.ACYCLIC.generate(n, m);
        Assert.assertTrue(GraphAlgorithm.isAcyclic(graph));
        boolean[][] nei = GraphDUtils.toBoolArray(graph);
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    nei[i][j] |= nei[i][k] && nei[k][j];
                }
        for (int i = 0; i < n; i++) {
            Assert.assertFalse(nei[i][i]);
        }
    }
}
