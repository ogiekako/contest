package src;

import java.util.Arrays;
import java.util.List;
public class FoxTheLinguist {
    int INF = (int) 1e9;
    public int minimalHours(int n, String[] courseInfo) {
        StringBuilder b = new StringBuilder();
        for (String s : courseInfo) b.append(s);
        String[] ss = b.toString().split(" ");
        int m = ss.length;
        int N = n + m + 1;
        char[] fromC = new char[m], toC = new char[m];
        int[] fromI = new int[m], toI = new int[m];
        int[] time = new int[m];
        for (int i = 0; i < m; i++) {
            fromC[i] = ss[i].charAt(0);
            fromI[i] = ss[i].charAt(1) - '0';
            toC[i] = ss[i].charAt(4);
            toI[i] = ss[i].charAt(5) - '0';
            time[i] = Integer.parseInt(ss[i].substring(7));
        }
        int[][] graph = new int[N][N];
        for (int i = 0; i < N; i++) Arrays.fill(graph[i], INF);
        for (int i = 0; i < N; i++) graph[i][i] = 0;
        int root = N - 1;
        for (int i = 0; i < m; i++) {
            if (fromI[i] == 0) {
                graph[root][n + i] = time[i];
            }
            if (toI[i] == 9) {
                graph[n + i][toC[i] - 'A'] = 0;
            }
        }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < m; j++)
                if (i != j) {
                    if (toC[i] == fromC[j] && toI[i] >= fromI[j]) {
                        graph[n + i][n + j] = time[j];
                    }
                }

        for (int k = 0; k < N; k++)
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
        int[][] dp = new int[1 << n][N];
        for (int i = 0; i < 1 << n; i++) Arrays.fill(dp[i], INF);
        for (int i = 0; i < n; i++) for (int j = 0; j < N; j++) dp[1 << i][j] = graph[j][i];
        for (int S = 1; S < 1 << n; S++) {
            for (int v = 0; v < N; v++) {
                for (int T = S; T > 0; T = (T - 1) & S)
                    if (T != S) {
                        dp[S][v] = Math.min(dp[S][v], dp[T][v] + dp[T ^ S][v]);
                    }
            }
            for (int v = 0; v < N; v++) {
                for (int u = 0; u < N; u++) {
                    dp[S][u] = Math.min(dp[S][u], dp[S][v] + graph[u][v]);
                }
            }
        }

        int res = dp[(1 << n) - 1][root];
        return res >= INF ? -1 : res;
    }

    class Edge {
        int to;
        int time;
        Edge(int to, int time) {
            this.to = to; this.time = time;
        }
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
