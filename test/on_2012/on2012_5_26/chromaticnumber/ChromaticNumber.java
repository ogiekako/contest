package on_2012.on2012_5_26.chromaticnumber;


// Paste me into the FileEdit configuration dialog

import java.util.Random;

public class ChromaticNumber {
    public int minColors(String[] _graph) {
        int n = _graph.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (_graph[i].charAt(j) == 'N') graph[i][j] = true;
        boolean[][] graph2 = new boolean[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph2[i][j] = graph[i][j];
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph2[i][j] |= graph2[i][k] && graph2[k][j];
        boolean[] vs = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++)
            if (!vs[i]) {
                int c = 0;
                int A = -1, B = -1, C = -1;
                for (int j = 0; j < n; j++)
                    if (graph2[i][j]) {
                        c++; vs[j] = true;
                        if (A == -1) A = j;
                        else if (B == -1) B = j;
                        else if (C == -1) C = j;
                    }
                if (c == 3 && graph[A][B] && graph[B][C] && graph[C][A]) res += 1;
                else res += (c + 1) / 2;
            }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(12049812L);
        int n = 15;
        for (; ; ) {
            int d = rnd.nextInt(n);
            System.err.print(".");
            boolean[][] graph = new boolean[n][n];
            int[] deg = new int[n];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (i != j) graph[i][j] = true;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < i; j++) {
                    if (deg[i] < 2 && deg[j] < 2) {
                        if (rnd.nextInt(n) < d) {
                            deg[i]++; deg[j]++;
                            graph[i][j] = graph[j][i] = false;
                        }
                    }
                }
            String[] _graph = new String[n];
            for (int i = 0; i < n; i++) {
                _graph[i] = "";
                for (int j = 0; j < n; j++) _graph[i] += graph[i][j] ? 'Y' : 'N';
            }
            System.err.println("");
            for (String s : _graph) System.err.println(s);
            int res = new ChromaticNumber().minColors(_graph);
            int exp = new ChromaticNumber().solve(_graph);
            if (res != exp) {
                System.err.println("");
                for (String s : _graph) System.err.println(s);
                System.err.println(res + " " + exp);
                throw new AssertionError();
            }
        }
    }

    private int solve(String[] graph) {
        int[] color = new int[graph.length];
        return dfs(color, graph, 0, 0);
    }

    private int dfs(int[] color, String[] graph, int p, int mx) {
        if (p == graph.length) return mx;
        int res = graph.length;
        for (int i = 0; i <= mx; i++) {
            color[p] = i;
            boolean ok = true;
            for (int j = 0; j < p; j++) if (graph[p].charAt(j) == 'Y' && color[p] == color[j]) ok = false;
            if (ok) {
                res = Math.min(res, dfs(color, graph, p + 1, Math.max(mx, i + 1)));
            }
        }
        return res;
    }
}

