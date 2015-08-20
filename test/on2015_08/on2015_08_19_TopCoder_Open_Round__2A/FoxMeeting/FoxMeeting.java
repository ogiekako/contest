package on2015_08.on2015_08_19_TopCoder_Open_Round__2A.FoxMeeting;



import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class FoxMeeting {

    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    int[] foxes;
    int[][] graph;
    int[][] dist;
    int INF = Integer.MAX_VALUE / 2;

    public int maxDistance(int[] A, int[] B, int[] L, int[] foxes) {
        this.foxes = foxes;
        ArrayUtils.decreaseByOne(foxes, A, B);
        n = A.length + 1;
        graph = new int[n][n];
        dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], INF);
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for (int i = 0; i < n - 1; i++) {
            graph[A[i]][B[i]] = graph[B[i]][A[i]] = L[i];
            dist[A[i]][B[i]] = dist[B[i]][A[i]] = L[i];
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int b = Integer.highestOneBit(res); b > 0; b >>= 1) {
            if (possible(res - b)) res -= b;
        }
        return res;
    }

    private boolean possible(int D) {
        for (int r = 0; r < n; r++) {
            int[] parents = new int[n];
            Arrays.fill(parents, -1);
            fill(parents, r, -1);
            boolean[] must = new boolean[n];
            for (int f : foxes) {
                int nearest = f;
                for (int v = 0; v < n; v++) if (dist[f][v] <= D && dist[r][v] < dist[r][nearest]) nearest = v;
                for (int v = nearest; v >= 0; v = parents[v]) {
                    must[v] = true;
                }
            }
            boolean[][] match = new boolean[n][foxes.length];
            int numMust = 0;
            for (int i = 0; i < n; i++)
                if (must[i]) {
                    for (int j = 0; j < foxes.length; j++) {
                        match[i][j] = dist[i][foxes[j]] <= D;
                    }
                    numMust++;
                }
            if (numMust <= BipartiteGraphAlgorithm.maximumMatching(match)) return true;
        }
        return false;
    }

    private void fill(int[] parents, int v, int p) {
        parents[v] = p;
        for (int u = 0; u < n; u++) if (u != p && graph[v][u] < INF) fill(parents, u, v);
    }
}
