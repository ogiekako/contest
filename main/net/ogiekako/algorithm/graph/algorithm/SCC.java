package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Pair;

import java.util.List;

public class SCC {
    /**
     * <p>Compute Strongly Connected Component(SCC) for the given graph.
     * At the same time, this method calculate sorted order of the connected components.<br>
     * </p>
     * - the i-th vertex belongs to the component res[i]. <br>
     * - there is no edge from component j to component i if i < j.<br>
     * <h3>Example:</h3>
     * <ul>Calculate the acyclicity of a graph:
     * <pre>{@code
     * int[] comp = SCC.scc(graph);
     * boolean acyclic = graph.length == 0;
     * for (int c : comp) if (c == graph.length - 1) acyclic = true;
     * for (int i = 0; i < graph.length; i++) for (int j : graph[i]) if (i == j) acyclic = false;
     * return acyclic;
     * }</pre></ul>
     * <ul>Calculate longest path of an acyclic graph using dynamic programming:
     * <pre>{@code
     * int[] comp = scc(graph);
     * int[] vertex = new int[graph.length];
     * for(int i=0;i<graph.length;i++)vertex[comp[i]] = i; // vertex[0] < vertex[1] < ...
     * int[] dp = new int[graph.length];
     * int res=0;
     * for(int i=0;i<graph.length;i++){
     *     res = Math.max(res, dp[vertex[i]]);
     *     for(int u:graph[vertex[i]])dp[u]=Math.max(dp[u],dp[vertex[i]] + 1);
     * }
     * return res;
     * }</pre>
     * </ul>
     * <p/>
     * <h3>Reference:</h3>
     * http://spinda2.blog48.fc2.com/blog-entry-492.html
     *
     * @param graph the given graph
     * @return - res[v] = id of the connected component containing v.
     *         <p/>
     *         Verified: SRM499 1000, TCO13R2 500.
     */
    public static int[] scc(List<Integer>[] graph) {
        return scc(Graph.of(graph));
    }

    public static int[] scc(Graph graph) {
        int[] postorder = createPostorder(graph);// KAERIGAKE
        // if there is a path from u to v:
        // 1. postorder[u] > postorder[v] or
        // 2. postorder[u] < postorder[v] and there is a path from v to u.

        // if component B is reachable from component A,
        // for any vertex v in A and u in B postorder[v] is larger than postorder[u].

        Graph rGraph = GraphUtils.transposed(graph);

        return new DFS<int[]>(rGraph) {
            int group = -1;
            int[] result = new int[n];

            // the root is the unvisited vertex that has the largest postorder.
            @Override
            protected void enterRoot(int root) {
                group++;
            }

            // Thm. visited vertices are connected component that ths root vertex belongs to.
            // ->) if u and v are in the same connected component, there are a path from u to v.
            // Hence u is visited in this dfs.
            // <-) if u is visited in this dfs, there is a path from u to v and postorder[u] < postorder[v].
            // Thus there is a path from v to u.
            @Override
            protected boolean enter(Edge e) {
                if (visited[e.to()]) return false;
                result[e.to()] = group;
                return true;
            }

            @Override
            protected int[] result() {
                return result;
            }
        }.run(ArrayUtils.reversed(postorder));
    }

    private static int[] createPostorder(Graph graph) {
        DFS<int[]> dfs = new DFS<int[]>(graph) {
            int ptr = 0;
            int[] result = new int[n];

            @Override
            protected void exit(Edge e) {
                result[ptr++] = e.to();
            }

            @Override
            protected int[] result() {
                return result;
            }

            @Override
            protected boolean enter(Edge e) {
                return !visited[e.to()];
            }
        };
        return dfs.run();
    }

    /**
     * res.second[i] := vertices that belongs to component i.
     */
    public static Pair<int[], int[][]> sccWithComponents(Graph graph) {
        int[] comp = scc(graph);
        int[][] res = ArrayUtils.classify(comp);
        return new Pair<int[], int[][]>(comp, res);
    }
}
