package on2013_05.on2013_05_03_Single_Round_Match_578.DeerInZooDivOne;


import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;

import java.util.Arrays;
public class DeerInZooDivOne {
    int n;
    int[][][][] dp;
    int[][][][] vis;
    Graph tree;
    int flag;
    long totalMatchingTime = 0;
    int matchingCount = 0;
    public int getmax(int[] a, int[] b) {
        int res = 0;
        int n = a.length + 1;
        dp = new int[n + 1][n][n + 1][n];
        vis = new int[n + 1][n][n + 1][n];
        for (int delete = 0; delete < a.length; delete++) {
            this.flag = delete + 1;
            boolean[][] same = new boolean[n][n];
            for (int i = 0; i < n; i++) same[i][i] = true;
            for (int i = 0; i < a.length; i++) if (delete != i) same[a[i]][b[i]] = same[b[i]][a[i]] = true;
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) same[i][j] |= same[i][k] && same[k][j];
            tree = new BidirectionalGraph(n);
            for (int i = 0; i < a.length; i++) if (delete != i) tree.add(a[i], b[i]);
            int size = 0;
            for (int i = 0; i < n; i++) if (same[0][i]) size++;
            size = Math.min(size, n - size);
            if (size <= res) continue;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) if (same[0][i] && !same[i][j]) res = Math.max(res, solve(n, i, n, j));
        }
        debug("count", matchingCount);
        debug("time", totalMatchingTime);
        return res;
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    private int solve(int p1, int v1, int p2, int v2) {
        if (vis[p1][v1][p2][v2] == flag) {
            return dp[p1][v1][p2][v2];
        }
        vis[p1][v1][p2][v2] = flag;
        int c1 = 0, c2 = 0;
        for (Edge e : tree.edges(v1)) if (e.to() != p1) c1++;
        for (Edge e : tree.edges(v2)) if (e.to() != p2) c2++;
        long[][] graph = new long[c1][c2];
        c1 = 0;
        for (Edge e : tree.edges(v1))
            if (e.to() != p1) {
                c2 = 0;
                for (Edge e2 : tree.edges(v2))
                    if (e2.to() != p2) {
                        int cost = solve(v1, e.to(), v2, e2.to());
                        graph[c1][c2++] = cost;
                    }
                c1++;
            }
        long start = System.currentTimeMillis();
        long best = BipartiteGraphAlgorithm.maximumCostMatching(graph);
        matchingCount++;
        long time = System.currentTimeMillis() - start;
        totalMatchingTime += time;
        return dp[p1][v1][p2][v2] = (int) (best + 1);
    }
}
