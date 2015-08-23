package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;

import java.util.Arrays;

public class BipartiteGraphAlgorithm {
    int leftNode, rightNode;
    boolean[][] graph;

    /**
     * The argument 'rightNode' is necessary to deal with the case graph.length == 0.
     *
     * @param leftNode  The number of left nodes
     * @param rightNode The number of right nodes
     * @param graph     Given bipartite graph
     */
    public BipartiteGraphAlgorithm(int leftNode, int rightNode, boolean[][] graph) {
        if (leftNode != graph.length) throw new IllegalArgumentException(graph.length + " " + leftNode);
        if (leftNode > 0 && rightNode != graph[0].length)
            throw new IllegalArgumentException(graph[0].length + " " + rightNode);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.graph = graph;
    }

    public static int maximumMatching(boolean[][] graph) {
        return new BipartiteGraphAlgorithm(graph.length, graph.length > 0 ? graph[0].length : 0, graph).maximumMatching();
    }

    /**
     * Note: We need 'w' to deal with the case of graph.length == 0.
     */
    public static int maximumIndependentSet(int h, int w, boolean[][] bipartiteGraph) {
        return new BipartiteGraphAlgorithm(h, w, bipartiteGraph).maximumIndependentSet();
    }

    public static long maximumCostMatching(long[][] costs) {
        if (costs.length == 0) return 0;
        int n = costs.length, m = costs[0].length;
        Graph graph = new Graph(n + m + 2);
        int source = n + m, sink = source + 1;
        for (int i = 0; i < n; i++) graph.addFlow(source, i, 1., 0.);
        for (int j = 0; j < m; j++) graph.addFlow(n + j, sink, 1., 0.);
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) graph.addFlow(i, n + j, 1, -costs[i][j]);
        return - (long) new MinimumCostFlow(graph).primalDual(source, sink, Math.min(n,m));
    }

    // SRM 491(C)
    public static double minimumCostMatching(double[][] graph, int k) {
        if (graph.length == 0) return 0;
        int n = graph.length, m = graph[0].length;
        Graph G = new Graph(n + m + 2);
        int source = n + m, sink = source + 1;
        for (int i = 0; i < n; i++) {
//            graphD.add(new FlowEdge());
        }
        return 0;
    }

    /**
     * Compute the size of the maximum matching of the given graph.
     * <p/>
     * O(L^2 R).
     * matchには、右側の節点に対応する、左側の節点の番号が格納される。
     *
     * @return the size of maximum matching of the give graph.
     */
    public int maximumMatching() {
        int[] matched = new int[rightNode];
        Arrays.fill(matched, -1);
        int res = 0;
        for (int u = 0; u < leftNode; u++) {
            boolean[] visited = new boolean[rightNode];
            if (argument(u, graph, visited, matched)) res++;
        }
        return res;
    }

    private boolean argument(int u, boolean[][] graph, boolean[] visited, int[] matched) {
        for (int v = 0; v < rightNode; v++)
            if (!visited[v] && graph[u][v] && matched[v] == -1) {
                visited[v] = true;
                matched[v] = u;
                return true;
            }
        for (int v = 0; v < rightNode; v++)
            if (!visited[v] && graph[u][v]) {
                visited[v] = true;
                if (argument(matched[v], graph, visited, matched)) {
                    matched[v] = u;
                    return true;
                }
            }
        return false;
    }

    /**
     * In general graphs, the complement of a vertex cover is an independent set.
     */
    public int maximumIndependentSet() {
        return leftNode + rightNode - minimumVertexCover();
    }

    /**
     * By Konig's theorem, the size of a minimum vertex cover is equal to the size of a maximum matching.
     * <p/>
     * Once a maximum matching M is computed, a minimum vertex cover can be constructed as follows:
     * Let L be nodes that are not reachable from the source node on the residual network, and
     * R be nodes that are reachable from the source node on the residual network.
     * Then, C = L \cup R becomes a minimum vertex cover.
     * <h3>Proof</h3>
     * Lemma 1: C is a vertex cover.   <br></br>
     * Proof: Assume there is an uncovered edge (u,v). It means u is reachable and v is not reachable.
     * Thus (u,v) is a matching edge. (Unless, v can be reached from u.) However, there no way to reach u in that condition, a contradiction.
     * <p/>
     * Lemma 2: |C| = |M|     <br></br>
     * Proof: |C| >= |M| holds because C is a vertex cover. Let us show that |C| <= |M|.
     * Since there is no argument path, any vertex in R(reachable) has an incident matching edge. Let the set of them be M_R.
     * Also, any vertex in L(unreachable) should have an incident matching edge. Let the set of them be M_L.
     * Since there is no path from a vertex in R to a vertex in L, M_R and M_L has an empty intersection, so
     * |L| + |R| = |M_L| + |M_R| <= |M| and completes the proof of Lemma 2.
     * <p/>
     * Combining Lemma 1, Lemma 2 and the fact that the size of a vertex cover is not less than the size of a matching,
     * we complete the proof that C is a minimum vertex cover.
     * <p/>
     * Ref: <a href="http://d.hatena.ne.jp/komiyam/20110208/1297112982"> http://d.hatena.ne.jp/komiyam/20110208/1297112982</a>
     */
    public int minimumVertexCover() {
        return maximumMatching();
    }
}
