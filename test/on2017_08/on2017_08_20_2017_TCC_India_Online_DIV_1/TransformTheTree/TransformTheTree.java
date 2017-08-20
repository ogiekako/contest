package on2017_08.on2017_08_20_2017_TCC_India_Online_DIV_1.TransformTheTree;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;

public class TransformTheTree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    int[] deg;
    Graph graph;
    public int countCuts(int[] parents) {
        n = parents.length + 1;
        deg = new int[n];
        graph = new Graph(n);
        for (int i = 0; i < parents.length; i++) {
            graph.add(parents[i], i + 1);
            graph.add(i + 1, parents[i]);
        }
        for (int i = 0; i < n; i++) {
            deg[i] = graph.edges(i).size();
        }
        return recur(0, -1);
    }

    private int recur(int v, int p) {
        int res = 0;
        for (Edge e : graph.edges(v)) {
            if (e.to() == p) continue;
            res += recur(e.to(), v);
        }
        if (deg[v] > 2) {
            res += deg[v] - 2;
            if (p >= 0) {
                deg[p]--;
            }
        }
        return res;
    }
}
