package on2017_09.on2017_09_24_ARC_083.TaskF;



import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.algorithm.TopologicalSort;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;

import java.util.*;

public class TaskF {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        int N = in.nextInt();
        Graph graph = new Graph(N * 2);
        for (int i = 0; i < N * 2; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1 + N;
            graph.add(x, y);
            graph.add(y, x);
        }
        List<Graph> comps = comp(graph);

        Mint res = Mint.ZERO;
        int size = 0;
        for (Graph g : comps) {
            size += g.size();
            Mint ways = ways(g);
            res = res.mul(ways).mul(MathUtils.comb(size, g.size()));
        }
        out.println(res);
    }

    private Mint ways(Graph graph) {
        int n = graph.size();
        int m = 0;
        for (int i = 0; i < graph.size(); i++) {
            m += graph.edges(i).size();
        }
        if (m != n * 2) return Mint.ZERO;

        boolean[] inTree = new boolean[n];
        int[] deg = new int[n];
        for (int i = 0; i < n; i++) {
            deg[i] = graph.edges(i).size();
        }
        Graph tree = new Graph(n);
        for (int i = 0; i < n; i++) {
            mark(graph, i, deg, inTree, tree);
        }
        Graph revTree = GraphUtils.transposed(tree);

        int c = -1;
        int cycleSize = 0;
        for (int i = 0; i < n; i++) {
            if (!inTree[i]) {
                if (deg[i] != 2) throw new AssertionError();
                c = i;
                cycleSize++;
            }
        }
        if (c == -1) throw new AssertionError();

        List<Integer> cycle = new ArrayList<>();
        markCycle(graph, c, inTree, new boolean[n], cycle);

        if (cycle.size() != cycleSize) throw new AssertionError();

        Mint res = Mint.ZERO;
        for (int d = -1; d <= 1; d+=2) {
            Graph dirGraph = new Graph(n);
            for (int i = 0; i < n; i++) {
                for (Edge e : revTree.edges(i)) {
                    dirGraph.add(e.from(), e.to());
                }
            }
            for (int i = 0; i < cycle.size(); i++) {
                int j = (i + cycle.size() + d) % cycle.size();
                dirGraph.add(i, j);
            }
            res = res.add(ways2(dirGraph));
        }
        return res;
    }

    private Mint ways2(Graph graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) {
            if (graph.edges(i).size() != 1) throw new AssertionError();
        }
        Graph rev = GraphUtils.transposed(graph);
        Graph order = new Graph(n);
        for (int i = 0; i < n; i++) {
            int to = graph.edges(i).get(0).to();
            for (Edge e : rev.edges(i)) {
                int from = e.to();
                if (from < to) {
                    order.add(from, i);
                }
            }
        }

        int[] id = new TopologicalSort(order).sortedOrder();
        return Mint.ZERO;
    }

    private void markCycle(Graph graph, int i, boolean[] inTree, boolean[] visited, List<Integer> cycle) {
        if (inTree[i] || visited[i]) throw new AssertionError();
        visited[i] = true;
        cycle.add(i);
        for (Edge e : graph.edges(i)) {
            if (!visited[e.to()] && !inTree[e.to()]) {
                markCycle(graph, e.to(), inTree, visited, cycle);
                return;
            }
        }
    }

    private void mark(Graph graph, int i, int[] deg, boolean[] inTree, Graph tree) {
        if (deg[i] < 1) throw new AssertionError();
        if (deg[i] > 1) return;
        inTree[i] = true;
        int j = -1;
        for (Edge e : graph.edges(i)) {
            if (!inTree[e.to()]) {
                if (j != -1) throw new AssertionError();
                j = e.to();
            }
        }
        deg[j]--;
        tree.add(j, i);
        mark(graph, j, deg, inTree, tree);
    }

    private List<Graph> comp(Graph graph) {
        int n = graph.size();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (Edge e : graph.edges(i)) {
                uf.union(i, e.to());
            }
        }
        HashMap<Integer, List<Integer>> nodes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int r = uf.root(i);
            if (!nodes.containsKey(r)) {
                nodes.put(r, new ArrayList<>());
            }
            nodes.get(r).add(i);
        }
        List<Graph> res = new ArrayList<>();
        for (List<Integer> vs : nodes.values()) {
            Graph g = new Graph(vs.size());
            for (int i = 0; i < vs.size(); i++) {
                for (Edge e : graph.edges(vs.get(i))) {
                    int j = Collections.binarySearch(vs, e.to());
                    g.add(i, j);
                }
            }
            res.add(g);
        }
        return res;
    }
}
