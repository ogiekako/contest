package on2017_09.on2017_09_16_Typical_DP_Contest.R______;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.SCC;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.Pair;

public class TaskR {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        Graph graph = new Graph(N + 1);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (in.nextInt() == 1) graph.add(i, j);
            }
        }
        for (int i = 0; i < N; i++) {
            graph.add(N, i);
        }
        N++;

        Pair<int[], int[][]> scc = SCC.sccWithComponents(graph);
        int m = scc.second.length;
        if (m == 1) {
            out.println(N);
            return;
        }
        boolean[][] graph2 = new boolean[m][m];
        for (int i = 0; i < N; i++) {
            for (Edge e : graph.edges(i)) {
                int j = e.to();
                graph2[scc.first[i]][scc.first[j]] = true;
            }
        }
        for (int k = 0; k < m; k++) {
            for (int i = 0; i < m; i++) {
                if (graph2[i][k] && k < i) throw new AssertionError();
                for (int j = 0; j < m; j++) {
                    graph2[i][j] |= graph2[i][k] && graph2[k][j];
                }
            }
        }
        int[][] dp = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                dp[i][j] = Math.max(dp[i][j], scc.second[i].length + scc.second[j].length);
                for (int k = j + 1; k < m; k++) {
                    if (graph2[j][k]) {
                        dp[i][k] = Math.max(dp[i][k], dp[i][j] + scc.second[k].length);
                    }
                    if (graph2[i][k]) {
                        dp[j][k] = Math.max(dp[j][k], dp[i][j] + scc.second[k].length);
                    }
                }
            }
        }
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                res = Math.max(res, dp[i][j]);
            }
        }
        out.println(res - 1);
    }
}
