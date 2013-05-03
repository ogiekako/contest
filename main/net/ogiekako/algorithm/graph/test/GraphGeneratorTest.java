package net.ogiekako.algorithm.graph.test;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:37
 * To change this template use File | Settings | File Templates.
 */
public class GraphGeneratorTest {
    @Test
    public void testGenerateAcyclic() throws Exception {
        int n = 50, m = 100;
        for (int o = 0; o < 50; o++) {
            Graph graph = GraphGenerator.ACYCLIC.generate(n, m);
            boolean[][] reachable = new boolean[n][n];
            for (int i = 0; i < n; i++)
                for (Edge e : graph.edges(i)) {
                    reachable[i][e.to()] = true;
                }
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) reachable[i][j] |= reachable[i][k] && reachable[k][j];
            for (int i = 0; i < n; i++) Assert.assertFalse(reachable[i][i]);
        }
    }
}
