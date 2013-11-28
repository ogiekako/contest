package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class Dom2012D {
    int INF = (int) 1e9;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (; ; ) {
            int n = in.nextInt(), m = in.nextInt(), c = in.nextInt(), s = in.nextInt(), g = in.nextInt();
            if ((n | m | c | s | g) == 0) return;
            s--; g--;
            int[][][] dist = new int[c][n][n];
            for (int i = 0; i < c; i++)
                for (int j = 0; j < n; j++) for (int k = 0; k < n; k++) if (j != k) dist[i][j][k] = INF;
            for (int i = 0; i < m; i++) {
                int x = in.nextInt() - 1, y = in.nextInt() - 1, d = in.nextInt(), cc = in.nextInt() - 1;
                dist[cc][x][y] = dist[cc][y][x] = Math.min(dist[cc][x][y], d);
            }
            for (int cc = 0; cc < c; cc++)
                for (int k = 0; k < n; k++)
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            dist[cc][i][j] = Math.min(dist[cc][i][j], dist[cc][i][k] + dist[cc][k][j]);
            int[][] dist2Cost = new int[c][20100];
            int[] p = new int[c];
            for (int i = 0; i < c; i++) p[i] = in.nextInt();
            for (int i = 0; i < c; i++) {
                int[] q = new int[p[i] + 1];
                for (int j = 1; j < p[i]; j++) q[j] = in.nextInt();
                q[p[i]] = INF;
                int[] r = new int[p[i]];
                for (int j = 0; j < p[i]; j++) r[j] = in.nextInt();
                int cur = 0;
                for (int d = 0, k = 0; d < 20100; d++) {
                    dist2Cost[i][d] = cur;
                    if (d == q[k + 1]) k++;
                    cur += r[k];
                }
            }
            int[][] cost = new int[n][n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (i != j) cost[i][j] = INF;
            for (int i = 0; i < c; i++)
                for (int j = 0; j < n; j++)
                    for (int k = 0; k < n; k++) {
                        if (dist[i][j][k] < INF) cost[j][k] = Math.min(cost[j][k], dist2Cost[i][dist[i][j][k]]);
                    }
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k][j]);
            if (cost[s][g] == INF) out.println(-1);
            else out.println(cost[s][g]);
        }
    }

    private void debug(Object... dist2Cost) {
        System.err.println(Arrays.deepToString(dist2Cost));
    }
}
