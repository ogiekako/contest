package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Graph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class BellmanFordTest {

    @Test
    public void ssspShouldNotConsiderZeroCycleNegative() throws Exception {
        Graph G = new Graph(3);
        G.addWeighted(0, 1, -1.0 / 3.0);
        G.addWeighted(0, 2, -2.0 / 3.0);
        G.addWeighted(1, 2, 1.0 / 3.0);
        G.addWeighted(2, 1, -1.0 / 3.0);
        double[] dist = new BellmanFord(G).sssp(0);
        Assert.assertArrayEquals(new double[]{0.0, -1.0, -2.0 / 3.0}, dist, 1e-9);
    }

}
