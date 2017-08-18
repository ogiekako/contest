package on2017_08.on2017_08_05_TopCoder_Open_Round__2.TreeSorter;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;

import java.util.Arrays;

public class TreeSorter {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minimalCosts(int[] p) {
        int n = p.length;
        int s = 0, t = 2 * n + 2, r = 2 * n + 1;
        Graph graph = new Graph(2 * n + 3);
        for (int i = 0; i < n; i++) {
            graph.addFlow(s, 1 + i, 1, 1);
        }
        for (int i = 0; i < n; i++) {
            graph.addFlow(1 + i, r, 1, 0);

            for (int j = 0; j < n; j++) {
                if (i < j && p[i] > p[j]) {
                    graph.addFlow(1 + i, n + 1 + j, 1, 0);
                }
            }

            for (int j = 0; j < n; j++) {
                graph.addFlow(n + 1 + i, t, 1, j * 2 + 3);
            }
            graph.addFlow(r, t, 1, i * 2 + 1);
        }

        double cost = new MinimumCostFlow(graph).primalDual(0, t, n);
        return (int)(cost);
    }
}
