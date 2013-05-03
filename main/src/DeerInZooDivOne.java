package src;

import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
public class DeerInZooDivOne {
    int n;
    int[][][][] dp;
    int[][][][] vis;
    Graph g;
    int p;
    public int getmax(int[] a, int[] b) {
        int res = 0;
        int n = a.length + 1;
        dp = new int[n + 1][n][n + 1][n];
        vis = new int[n + 1][n][n + 1][n];
        for (int p = 0; p < a.length; p++) {
            this.p = p + 1;
            boolean[][] same = new boolean[n][n];
            for (int j = 0; j < n; j++) {
                same[j][j] = true;
            }
            for (int j = 0; j < a.length; j++) {
                if (p != j) same[a[j]][b[j]] = true;
            }
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        same[i][j] |= same[i][k] && same[k][j];
                    }
                }
            }
            g = new BidirectionalGraph(n);
            for (int i = 0; i < a.length; i++) if (p != i) g.add(a[i], b[i]);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (!same[i][j]) {
                        res = Math.max(res, solve(n, i, n, j));
                    }
        }
        return res;
    }

    private int solve(int p1, int v1, int p2, int v2) {
        if (vis[p1][v1][p2][v2] == p) {
            return dp[p1][v1][p2][v2];
        }
        vis[p1][v1][p2][v2] = p;
        int c1 = 0, c2 = 0;
        for (Edge e : g.edges(v1)) if (e.to() != p1) c1++;
        for (Edge e : g.edges(v2)) if (e.to() != p2) c2++;
        int ptr1 = 0;
        Graph graph = new Graph(c1+c2+2);
        int s = c1 + c2, t = s + 1;
        for (Edge e : g.edges(v1))
            if (e.to() != p1) {
                int ptr2 = 0;
                for (Edge e2 : g.edges(v2))
                    if (e2.to() != p2) {
                        int cost = solve(v1, e.to(), v2, e2.to());
                        graph.add(new FlowEdge(ptr1, c1+ptr2, 1, -cost));
                        ptr2++;
                    }
                ptr1++;
            }

        for (Edge e : g.edges(v1)) if (e.to() != p1) c1++;

        return 0;
    }
}
