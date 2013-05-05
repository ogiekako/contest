package net.ogiekako.algorithm.graph.graphDouble;

import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:59
 * To change this template use File | Settings | File Templates.
 */
public class GraphDUtils {
    public static boolean[][] toBoolArray(GraphD graph) {
        int n = graph.size();
        boolean[][] nei = new boolean[n][n];
        for (int i = 0; i < n; i++) for (EdgeD e : graph.getEdges(i)) nei[i][e.to()] = true;
        return nei;
    }
}
