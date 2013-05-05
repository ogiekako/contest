package net.ogiekako.algorithm.graph.graphDouble.problems.test;

import net.ogiekako.algorithm.graph.graphDouble.GraphD;
import net.ogiekako.algorithm.graph.graphDouble.GraphAlgorithm;
import net.ogiekako.algorithm.graph.graphDouble.GraphDUtils;
import net.ogiekako.algorithm.graph.graphDouble.SimpleEdge;
import net.ogiekako.algorithm.graph.graphDouble.problems.Minimize;
import net.ogiekako.algorithm.graph.graphDouble.tests.GraphGenerator;
import net.ogiekako.algorithm.utils.BitUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:56
 * To change this template use File | Settings | File Templates.
 */
public class MinimizeTest {
    @Test
    public void testMinVertexToTrip010() throws Exception {
        for (int o = 0; o < 50; o++) {
            int n = 13, m = 30;
            GraphD graph = GraphGenerator.SIMPLE.generate(n, m);
            if(!GraphAlgorithm.reachable(graph, 0, 1) || !GraphAlgorithm.reachable(graph, 1, 0)){
                o--;continue;
            }
            boolean[][] nei = GraphDUtils.toBoolArray(graph);
            int res = Minimize.minVertexToTrip010(GraphDUtils.toBoolArray(graph));
            int exp = solveStupidTrip010(nei);
            Assert.assertEquals(exp,res);
        }
    }

    private int solveStupidTrip010(boolean[][] nei) {
        int n = nei.length;
        int res = Integer.MAX_VALUE;
        for(int S=0;S<1<<n;S++){
            GraphD graph = new GraphD(n);
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++)if(BitUtils.contains(S,i) && BitUtils.contains(S,j) && nei[i][j]){
                graph.add(new SimpleEdge(i,j));
            }
            if(GraphAlgorithm.reachable(graph, 0, 1) && GraphAlgorithm.reachable(graph, 1, 0)){
                res = Math.min(res, Integer.bitCount(S));
            }
        }
        return res;
    }
}
