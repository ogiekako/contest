package on2015_08.on2015_08_29_TopCoder_Open_Round__2C.CliqueGraph;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.algorithm.Dijkstra;

import java.util.Arrays;

public class CliqueGraph {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long calcSum(int N, int[] V, int[] sizes) {
        int C = sizes.length;
        Graph G = new UndirectedGraph(N + C);
        for (int i = 0, j = 0; i < C; i++) {
            for (int k = 0; k < sizes[i]; k++) {
                G.add(V[j++], N + i);
            }
        }
        long res = 0;
        for (int i = 0; i < N; i++) {
            double[] dist = new Dijkstra(G).sssp(i);
            for (int j = 0; j < N; j++) {
                res += (long)dist[j];
            }
        }
        return res / 4;
    }
}
