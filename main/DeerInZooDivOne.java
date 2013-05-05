import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
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
        int ptr1 = 0;
        Graph graph = new Graph(c1 + c2 + 2);
        int s = c1 + c2, t = s + 1;
        for (Edge e : g.edges(v1))
            if (e.to() != p1) {
                int ptr2 = 0;
                for (Edge e2 : g.edges(v2))
                    if (e2.to() != p2) {
                        int cost = solve(v1, e.to(), v2, e2.to());
                        graph.add(new FlowEdge(ptr1, c1 + ptr2, 1, -cost));
                        ptr2++;
                    }
                ptr1++;
            }

        for (Edge e : g.edges(v1)) if (e.to() != p1) c1++;

        return 0;
    }
}

class Graph {
    private int vertexCount;
    private EdgeList[] edges;

    public void remove(Edge edge) {
        edges[edge.from()].remove(edge);
        Edge rev = edge.reversed();
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
        Edge rev = edge.reversed();
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

class BidirectionalGraph extends Graph {
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
    Edge reversed();
}

class FlowEdge extends SimpleEdge {
    // capacity = residue + flow.
    long residue;
    long flow = 0;
    Edge reversed;
    Edge transposed;

    public FlowEdge(int from, int to, long capacity) {
        super(from, to);
        this.residue = capacity;
    }

    public FlowEdge(int from, int to, long capacity, long cost) {
        this(from, to, capacity);
        this.cost = cost;
    }

    public long residue() {
        return residue;
    }

    public long flow() {
        return flow;
    }

    public Edge transposed() {
        if (transposed == null) {
            transposed = new FlowEdge(to, from, residue + flow) {
                @Override
                public Edge transposed() {
                    return FlowEdge.this;
                }
            };
        }
        return transposed;
    }

    public Edge reversed() {
        if (reversed == null) reversed = new ReverseEdge();
        return reversed;
    }
    public String toString() {
        return String.format("%d->%d(%d/%d)", from(), to(), flow(), flow() + residue());
    }

    private class ReverseEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public long residue() {
            return flow;
        }

        public long flow() {
            return residue;
        }

        public Edge transposed() {
            return FlowEdge.this.transposed().reversed();
        }

        public Edge reversed() {
            return FlowEdge.this;
        }
        public String toString() {
            return String.format("%d->%d(%d/%d)", from(), to(), flow(), flow() + residue());
        }
    }
}

class SimpleEdge implements Edge {
    final int from;
    final int to;
    private Edge transposed;
    long cost = 1;

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

    public Edge reversed() {
        return null;
    }

    private class TransposedEdge implements Edge {
        public int from() {
            return to;
        }

        public int to() {
            return from;
        }

        public Edge reversed() {
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

