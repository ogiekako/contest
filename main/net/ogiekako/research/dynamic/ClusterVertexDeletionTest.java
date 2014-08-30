package net.ogiekako.research.dynamic;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

public class ClusterVertexDeletionTest {
    @Test
    @Ignore(value = "TOO LONG")
    public void testCompute() throws Exception {
        Random rnd = new Random(1049129084L);
        int n = 10;
        int m = 1000;
        ClusterVertexDeletion clusterVertexDeletion = new ClusterVertexDeletion();
        clusterVertexDeletion.init(n);
        _UndirectedGraph G = new _UndirectedGraph();
        G.init(n);
        for (int iteration = 0; iteration < m; iteration++) {
            int u = rnd.nextInt(n), v = rnd.nextInt(n);
            if (u == v) {
                iteration--;
                continue;
            }
            if (G.contains(u, v)) {
                log("remove " + u + " " + v);
                G.remove(u, v);
                clusterVertexDeletion.remove(u, v);
            } else {
                log("add " + u + " " + v);
                G.add(u, v);
                clusterVertexDeletion.add(u, v);
            }
            log("computing actual K ... ");
            int actualK = clusterVertexDeletion.compute();
            log("actualK = " + actualK);
            log("computing expected K ... ");
            int expectedK = clusterVertexDeletion.computeNaive(G).size();
            log("expectedK = " + expectedK);
            Assert.assertEquals(expectedK, actualK);
        }
    }

    private void log(String s) {
        System.err.println(s);
    }
}
