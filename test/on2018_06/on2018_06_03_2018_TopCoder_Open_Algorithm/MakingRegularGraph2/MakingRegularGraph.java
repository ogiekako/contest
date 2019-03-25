package on2018_06.on2018_06_03_2018_TopCoder_Open_Algorithm.MakingRegularGraph2;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.UndirectedGraph;

import java.util.HashSet;
import java.util.Set;

public class MakingRegularGraph {

    Set<Integer> pair(int x, int y) {
        Set<Integer> res = new HashSet<>();
        res.add(x);
        res.add(y);
        return res;
    }

    HashSet<Set<Integer>> added = new HashSet<>();

    public int[] addEdges(int n, int[] x, int[] y) {
        Graph g = new UndirectedGraph(n);
        for (int i = 0; i < x.length; i++) {
            g.add(x[i], y[i]);
            added.add(pair(x[i], y[i]));
        }
        int[] res = new int[(n - x.length) * 2];
        for (int i = 0; i < res.length; i += 2) {
            for (int j = 0; j < n; j++) {
                if (g.edges(j).size() > 2) throw new AssertionError();
                if (g.edges(j).size() == 2) continue;

                int to = -1;
                for (int k = j + 1; k < n; k++) {
                    if (g.edges(k).size() > 2) throw new AssertionError();
                    if (g.edges(k).size() == 2) continue;
                    if (added.contains(pair(j, k))) continue;
                    Edge e = g.add(j, k);
                    if (i < res.length - 10 || possible(g)) {
                        to = k;
                        added.add(pair(j, k));
                        break;
                    }
                    g.remove(e);
                }
                if (to == -1) return new int[]{-1};
                res[i] = j;
                res[i + 1] = to;
                break;
            }
        }
        return res;
    }

    private boolean possible(Graph g) {
        int s0 = 0;
        int s1 = 0;
        for (int i = 0; i < g.size(); i++) {
            if (g.edges(i).size() > 2) throw new AssertionError();
            if (g.edges(i).size() == 1) s1++;
            if (g.edges(i).size() == 0) s0++;
        }
        if (s1 == 0 && s0 == 0) return true;
        if (s1 == 0 && s0 <= 2) return false;
        if (s0 == 0 && s1 == 2) {
            for (int i = 0; i < g.size(); i++) {
                if (g.edges(i).size() == 1) {
                    for (int j = i + 1; j < g.size(); j++) {
                        if (g.edges(j).size() == 1) {
                            if (added.contains(pair(i, j))) return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
