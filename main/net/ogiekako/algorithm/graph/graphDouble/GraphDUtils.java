package net.ogiekako.algorithm.graph.graphDouble;

public class GraphDUtils {
    public static boolean[][] toBoolArray(GraphD graph) {
        int n = graph.size();
        boolean[][] nei = new boolean[n][n];
        for (int i = 0; i < n; i++) for (EdgeD e : graph.getEdges(i)) nei[i][e.to()] = true;
        return nei;
    }
}
