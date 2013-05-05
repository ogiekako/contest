package net.ogiekako.algorithm.graph;
import net.ogiekako.algorithm.graph.algorithm.SCC;
import net.ogiekako.algorithm.graph.algorithm.ShortestPath;
/**
 * Graph utility methods whose algorithms are straightforward..
 */

public class GraphUtils {
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
    public static boolean isAcyclic(Graph graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) for (Edge e : graph.edges(i)) if (e.to() == i) return false;
        return SCC.sccWithComponents(graph).second.length == n;
    }
    public static boolean reachable(Graph graph, int from, int to) {
        return ShortestPath.singleSourceShortestPath(graph, from)[to] < Long.MAX_VALUE;
    }
}
