package on2015_08.on2015_08_21_TopCoder_Open_Round__2B.ScotlandYard;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.TopologicalSort;

import java.util.Arrays;

public class ScotlandYard {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maxMoves(String[] A, String[] B, String[] C) {
        int n = A.length;
        int[][] id = new int[n][n];
        int N = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= j) {
                    id[i][j] = -1;
                } else {
                    id[i][j] = N++;
                }
            }
        }
        Graph G = new Graph(N + 1);
        int terminal = N;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        boolean ok = false;
                        for (String[] g : new String[][]{A, B, C}) {
                            if ((g[i].charAt(k) == 'Y') || ((g[j].charAt(k) == 'Y'))) {
                                if (g[i].charAt(l) == 'Y' || (g[j].charAt(l) == 'Y')) {
                                    ok = true;
                                }
                            }
                        }
                        if (ok) {
                            G.add(id[i][j], id[k][l]);
                        }
                    }
                }
                boolean canGo = false;
                for (int k = 0; k < n; k++)
                    for (String[] g : new String[][]{A, B, C})
                        if ((g[i].charAt(k) == 'Y') || ((g[j].charAt(k) == 'Y')))
                            canGo = true;
                if (canGo) {
                    G.add(id[i][j], terminal);
                }
            }
        }
        int[] order = new TopologicalSort(G).sortedOrder();
        if (order == null) {
            return -1;
        }
        int[] dp = new int[order.length];
        for (int v : order) {
            for (Edge e : G.edges(v)) {
                int u = e.to();
                dp[u] = Math.max(dp[u], dp[v] + 1);
            }
        }
        int res = 0;
        for (int d : dp) res = Math.max(res, d);
        return res;
    }
}
