import java.util.List;
import java.util.Arrays;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author ogiekako
 */
public class DeerInZooDivOne {
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
        int[][] graph = new int[c1][c2];
        c1 = 0;
        for (Edge e : g.edges(v1))
            if (e.to() != p1) {
                c2 = 0;
                for (Edge e2 : g.edges(v2))
                    if (e2.to() != p2) {
                        graph[c1][c2++] = solve(v1, e.to(), v2, e2.to());
                    }
                c1++;
            }
        return dp[p1][v1][p2][v2] = 1 + hungarian(graph);
    }

    public static int hungarian(int[][] bipartiteGraph) {// PKU2195
        int[][] g = bipartiteGraph;
        int n = g.length;
        int[] fx = new int[n], fy = new int[n];
        Arrays.fill(fx, Integer.MAX_VALUE / 2);
        int[] x = new int[n], y = new int[n];
        Arrays.fill(x, -1);
        Arrays.fill(y, -1);
        for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) fx[i] = Math.max(fx[i], g[i][j]);
        for (int i = 0; i < n; ) {
            int[] t = new int[n], s = new int[n + 1];
            Arrays.fill(t, -1);
            Arrays.fill(s, i);
            int q = 0;
            for (int p = 0; p <= q && x[i] < 0; ++p)
                for (int k = s[p], j = 0; j < n && x[i] < 0; ++j)
                    if (fx[k] + fy[j] == g[k][j] && t[j] < 0) {
                        s[++q] = y[j];
                        t[j] = k;
                        if (s[q] < 0) for (p = j; p >= 0; j = p) {
                            y[j] = k = t[j];
                            p = x[k];
                            x[k] = j;
                        }
                    }
            if (x[i] < 0) {
                int d = Integer.MAX_VALUE / 2;
                for (int k = 0; k <= q; ++k)
                    for (int j = 0; j < n; ++j) if (t[j] < 0) d = Math.min(d, fx[s[k]] + fy[j] - g[s[k]][j]);
                for (int j = 0; j < n; ++j) fy[j] += (t[j] < 0 ? 0 : d);
                for (int k = 0; k <= q; ++k) fx[s[k]] -= d;
            } else ++i;
        }
        int ret = 0;
        for (int i = 0; i < n; ++i) ret += g[i][x[i]];
        return ret;
    }
}

class Graph {
    private int vertexCount;
    private EdgeList[] edges;

    public void remove(Edge edge) {
        edges[edge.from()].remove(edge);
        Edge rev = edge.getReversed();
        if (rev != null) {
            edges[rev.from()].remove(rev);
        }
    }

    private static class EdgeList extends ArrayList<Edge> {
        EdgeList() {super(0);}
    }

    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        edges = new EdgeList[vertexCount];
        for (int i = 0; i < vertexCount; i++) edges[i] = new EdgeList();
    }

    public void add(Edge edge) {
        edges[edge.from()].add(edge);
        Edge rev = edge.getReversed();
        if (rev != null) {
            edges[rev.from()].add(rev);
        }
    }

    public void add(int from, int to) {
        add(new SimpleEdge(from, to));
    }

    public List<Edge> edges(int vertex) {
        return edges[vertex];
    }

    public String toString() {
        return "Graph{" +
                "edges=" + (edges == null ? null : Arrays.asList(edges)) +
                '}';
    }
}

class BidirectionalGraph extends Graph{
    public BidirectionalGraph(int vertexCount) {
        super(vertexCount);
    }

    public void remove(Edge edge) {
        super.remove(edge);
        super.remove(edge.transposed());
    }

    public void add(Edge edge) {
        super.add(edge);
        super.add(edge.transposed());
    }
}

interface Edge {
    int from();

    int to();

    /**
     * @return The edge simply its from and to are transposed.
     *         It is not nullable.
     */
    Edge transposed();

    /**
     * @return The reverse edge in case this edge is a flow edge.
     *         It may throw UnsupportedOperationException if this edge is not a flow edge.
     */
    Edge getReversed();
}

class SimpleEdge implements Edge {
    final int from;
    final int to;
    private Edge transposed;

    public String toString() {
        return from() + " -> " + to();
    }

    public SimpleEdge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public Edge transposed() {
        if (transposed == null)
            return transposed = new TransposedEdge();
        return transposed;
    }

    public Edge getReversed() {
        return null;
    }

    private class TransposedEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public Edge getReversed() {
            return null;
        }

        public Edge transposed() {
            return SimpleEdge.this;
        }

        public String toString() {
            return from() + " -> " + to();
        }
    }
}

