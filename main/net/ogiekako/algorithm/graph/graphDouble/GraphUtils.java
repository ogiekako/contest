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
public class GraphUtils {
    public static boolean[][] toBoolArray(GraphD graph) {
        int n = graph.size();
        boolean[][] nei = new boolean[n][n];
        for (int i = 0; i < n; i++) for (EdgeD e : graph.getEdges(i)) nei[i][e.to()] = true;
        return nei;
    }

    public static String[] toStringArray(boolean[][] nei) {
        String[] ss = new String[nei.length];
        for (int i = 0; i < ss.length; i++) {
            ss[i] = "";
            for (int j = 0; j < nei[i].length; j++) {
                ss[i] += nei[i][j] ? 'Y' : 'N';
            }
        }
        return ss;
    }

    public static Graph transposed(Graph graph) {
        Graph res = new Graph(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            for (Edge e : graph.edges(i)) {
                res.add(e.transposed());
            }
        }
        return res;
    }

    /**
     * count the number of edges in the graph.
     * if the graph is bidirectional, result is the number of undirected edges,
     */
    public static int edgeCount(Graph graph) {
        int res = 0;
        for (int i = 0; i < graph.size(); i++) res += graph.edges(i).size();
        if (graph instanceof BidirectionalGraph) res /= 2;
        return res;
    }

    public static boolean[][] toBoolArray(Graph graph) {
        boolean[][] res = new boolean[graph.size()][graph.size()];
        for (int i = 0; i < res.length; i++) for (Edge e : graph.edges(i)) res[e.from()][e.to()] = true;
        return res;
    }
}
