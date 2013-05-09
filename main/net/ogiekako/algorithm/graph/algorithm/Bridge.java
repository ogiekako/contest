package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;

import java.util.HashSet;
import java.util.Set;

public class Bridge {
    BidirectionalGraph graph;
    public int[] cut;
    public Set<Edge> bridges;
    public int[] comp;
    public int numComp;

    public Bridge(BidirectionalGraph graph) {
        this.graph = graph;
    }

    /**
     * <p/>
     * Compute bridges, articulation points and biconnected components
     * of the given graph.
     * Vertex u and v are in a same biconnected component iff the max-flow from u to v
     * is more than or equals to 2.
     * After the calculation, following conditions are satisfied:
     * <ul>
     * <li> comp[v] equals to comp[u] iff u and v are biconnected </li>
     * <li> numComp = the number of biconnected components. max(comp) + 1 == numComp.</li>
     * <li> cut[v] is the increase of connected components when v is removed from the graph.
     * if cut[v] > 0, v is an articulation point.</li>
     * <li> bridges are a set of bridge edges. Bridge is an edge whose removal increases the number of connected components.</li>
     * </ul>
     * <p/>
     * Complexity: O(E).
     * Reference: http://en.wikipedia.org/wiki/Biconnected_component
     *
     * @return the number of biconnected components
     */

    public int compute() {
        int n = graph.size();
        cut = new int[n];
        bridges = new HashSet<Edge>();
        comp = new int[n];
        numComp = -1;

        new DFS<Void>(graph) {
            // the depth of the lowest vertex reachable from v by a path for which all but the last edge stays within the subtree rooted at v.
            final int[] lows = new int[n];
            final int[] depth = new int[n];

            @Override
            protected void enterRoot(int root) {
                if (graph.edges(root).size() > 0)
                    cut[root]--;
            }

            @Override
            protected boolean enter(Edge e) {
                if (!visited[e.to()]) {
                    lows[e.to()] = depth[e.to()] = e.from() < 0 ? 0 : depth[e.from()] + 1;
                    return true;
                } else {
                    int v = e.from(), u = e.to();
                    lows[v] = Math.min(lows[v], depth[u]);
                    return false;
                }
            }

            @Override
            protected void process(Edge e) {
                int v = e.from();
                int t = lows[e.to()];
                lows[v] = Math.min(lows[v], t);
                // there is no path from u to an upper vertex that does not go through v.
                if (t >= depth[v]) cut[v]++;
                // there is no path from u to v that does not go through e.
                if (t > depth[v]) {
                    bridges.add(e);
                    bridges.add(e.transposed());
                }
            }
        }.run();

        new DFS<Integer>(graph) {

            @Override
            protected void enterRoot(int root) {
                numComp++;
            }

            @Override
            protected boolean enter(Edge e) {
                if (bridges.contains(e)) return false;
                if (visited[e.to()]) return false;
                comp[e.to()] = numComp;
                return true;
            }
        }.run();
        return ++numComp;
    }
}
