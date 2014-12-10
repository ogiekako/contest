package src;

public class ChristmasTreeDecoration {
    public int solve(int[] col, int[] x, int[] y) {
        int n = col.length;
        for (int i = 0; i < x.length; i++) {
            x[i]--;
            y[i]--;
        }
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            graph[i][i] = true;
        }
        for (int i = 0; i < x.length; i++) {
            if (col[x[i]] != col[y[i]]) {
                graph[x[i]][y[i]] = graph[y[i]][x[i]] = true;
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    graph[i][j] |= graph[i][k] && graph[k][j];
                }
            }
        }
        boolean[] vis = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                res++;
                for (int j = 0; j < n; j++) {
                    if (graph[i][j]) vis[j] = true;
                }
            }
        }
        return res - 1;
    }
}
