package on2017_09.on2017_09_27_Single_Round_Match_721.Apocalypse;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.Arrays;

public class Apocalypse {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maximalSurvival(int[] p, int[] position, int t) {
        int n = p.length + 1;
        int[][] from = new int[t][n];
        int[][] to = new int[t][n];
        int q = 0;
        int source = q++;
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < n; j++) {
                from[i][j] = q++;
            }
            for (int j = 0; j < n; j++) {
                to[i][j] = q++;
            }
        }
        int sink = q++;

        Graph graph = new Graph(q);

        for (int pos : position) {
            graph.addFlow(source, from[0][pos], 1);
        }
        Arrays.sort(position);
        for (int i = 0; i < n; i++) {
            if (Arrays.binarySearch(position, i) < 0) {
                graph.addFlow(to[t-1][i], sink, 1);
            }
        }

        for (int i = 0; i < t; i++) {
            for (int j = 0; j < n; j++) {
                if (i < t - 1)
                    graph.addFlow(to[i][j], from[i+1][j], 1);
                if (j < n - 1) {
                    graph.addFlow(from[i][j + 1], to[i][p[j]], 1);
                    graph.addFlow(from[i][p[j]], to[i][j + 1], 1);
                }
                graph.addFlow(from[i][j], to[i][j], 1);
            }
        }

        double res = new MaxFlow(graph).maxFlow(source, sink);
        return (int) (Math.round(res));
    }
}
