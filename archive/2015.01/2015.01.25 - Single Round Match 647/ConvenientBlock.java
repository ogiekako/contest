package src;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

public class ConvenientBlock {
    long INF = (long) 1e14;
    public long minCost(int N, int[] from, int[] to, int[] cost) {
        boolean[][] g = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            g[i][i] = true;
        }
        for (int i = 0; i < from.length; i++) {
            g[from[i]][to[i]] = true;
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    g[i][j] |= g[i][k] && g[k][j];
                }
            }
        }
        Graph graph = new Graph(N);
        for (int i = 0; i < from.length; i++) {
            if (g[0][from[i]] && g[from[i]][N-1] && g[0][to[i]] && g[to[i]][N-1]) {
                graph.addFlow(from[i], to[i], cost[i]);
                graph.addFlow(to[i], from[i], INF);
            }
        }
        long res = (long) MaxFlow.maxFlow(graph, 0, N-1);
        return res >= INF ? -1 : res;
    }
}
