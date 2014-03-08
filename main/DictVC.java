import java.io.*;
import java.util.*;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author ogiekako
 */
public class DictVC {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		MyScanner in = new MyScanner(inputStream);
		MyPrintWriter out = new MyPrintWriter(outputStream);
		Solver solver = new Solver();
		solver.solve(1, in, out);
		out.close();
	}
}

class Solver {
    int n, m, s, t;
    int N;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        s = 0;
        t = n + n + 1;
        N = 1 + n + n + 1;
        Graph graph = createGraph(in);
        int[] comp = SCC.scc(graph);
//        debug("comp", comp);
//        GraphVis.output(graph, "vc");
        int numComp = 0;
        for (int dagIndex : comp) numComp = Math.max(numComp, dagIndex + 1);
        Vertex[] dag = new Vertex[numComp];
        for (int i = 0; i < numComp; i++) dag[i] = new Vertex();
        for (int i = 0; i < N; i++) {
            dag[comp[i]].indices.add(i);
            for (Edge e : graph.edges(i)) {
                int to = e.to();
                if (comp[to] != comp[i]) {
                    dag[comp[i]].es.add(dag[comp[to]]);
                    dag[comp[to]].rs.add(dag[comp[i]]);
                }
            }
        }
        dag[comp[s]].fix(Place.RIGHT);
        dag[comp[t]].fix(Place.LEFT);
        for (int i = 1; i <= n + n; i++) {
            int c = comp[i];
            if (dag[c].place == null) {
                dag[c].fix(i % 2 == 1 ? Place.LEFT : Place.RIGHT);
            }
        }
        List<Integer> res = new ArrayList<>();
        for (Vertex c : dag) {
            for (int v : c.indices) {
                if (1 <= v && v <= n + n) {
                    if (v % 2 == 1 && c.place == Place.LEFT || v % 2 == 0 && c.place == Place.RIGHT) {
                        res.add(v);
                    }
                }
            }
        }
        Collections.sort(res);
        out.println(res.size());
        for (int r : res) out.println(r);
    }

    enum Place {
        LEFT, RIGHT
    }

    class Vertex {
        Set<Vertex> es = new HashSet<>();
        Set<Vertex> rs = new HashSet<>();
        List<Integer> indices = new ArrayList<>();
        Place place = null;
        void fix(Place p) {
//            debug("fix", p, indices, es ,rs);
            if (place != null) {
                if (place != p) throw new AssertionError();
                return;
            }
            this.place = p;
            if (p == Place.RIGHT) {
                for (Vertex v : es) v.fix(Place.RIGHT);
            } else {
                for (Vertex v : rs) v.fix(Place.LEFT);
            }
        }
        public String toString() {
            return "Vertex{" +
                    "indices=" + indices +
                    '}';
        }
    }

    private Graph createGraph(MyScanner in) {
        Graph g = new Graph(n + n + 2);
        boolean[] matched = new boolean[n + n + 2];
        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt(), f = in.nextInt();
            g.add(u, v);
            if (f == 1) {
                g.add(v, u);
                matched[v] = matched[u] = true;
            }
        }
        for (int i = 1; i <= n + n; i += 2) {
            if (matched[i])
                g.add(i, s);
            else
                g.add(s, i);
        }
        for (int i = 2; i <= n + n; i += 2) {
            if (matched[i])
                g.add(t, i);
            else
                g.add(i, t);
        }
        return g;
    }
}

class MyScanner {
    private final InputStream in;

    public MyScanner(InputStream in) {
        this.in = in;
    }

    int bufLen;
    int bufPtr;
    byte[] buf = new byte[1024];

    public int read() {
        if (bufLen == -1)
            throw new InputMismatchException();
        if (bufPtr >= bufLen) {
            bufPtr = 0;
            try {
                bufLen = in.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (bufLen <= 0)
                return -1;
        }
        return buf[bufPtr++];
    }

    public int nextInt() {
        int c = read();
        if (c == -1) throw new NoSuchElementException();
        while (c != '-' && (c < '0' || '9' < c)) {
            c = read();
            if (c == -1) throw new NoSuchElementException();
        }
        if (c == '-') return -nextInt();
        int res = 0;
        do {
            res *= 10;
            res += c - '0';
            c = read();
        } while ('0' <= c && c <= '9');
        return res;
    }

    }

class MyPrintWriter {
    PrintWriter out;

    public MyPrintWriter(OutputStream outputStream) {
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public MyPrintWriter(Writer writer) {
        out = new PrintWriter(writer);
    }

    public void println(int a) {
        out.println(a);
    }

    public void close() {
        out.close();
    }

    }

class Graph {
    private int vertexCount;
    private EdgeList[] edges;

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

    public int size() {
        return vertexCount;
    }

    public String toString() {
        return "Graph{" +
                "edges=" + (edges == null ? null : Arrays.asList(edges)) +
                '}';
    }
}

class SCC {

    public static int[] scc(Graph graph) {
        int[] postorder = createPostorder(graph);// KAERIGAKE
        // if there is a path from u to v:
        // 1. postorder[u] > postorder[v] or
        // 2. postorder[u] < postorder[v] and there is a path from v to u.

        // if component B is reachable from component A,
        // for any vertex v in A and u in B postorder[v] is larger than postorder[u].

        Graph rGraph = GraphUtils.transposed(graph);

        return new DFS<int[]>(rGraph) {
            int group = -1;
            int[] result = new int[n];

            // the root is the unvisited vertex that has the largest postorder.
            @Override
            protected void enterRoot(int root) {
                group++;
            }

            // Thm. visited vertices are connected component that ths root vertex belongs to.
            // ->) if u and v are in the same connected component, there are a path from u to v.
            // Hence u is visited in this dfs.
            // <-) if u is visited in this dfs, there is a path from u to v and postorder[u] < postorder[v].
            // Thus there is a path from v to u.
            @Override
            protected boolean enter(Edge e) {
                if (visited[e.to()]) return false;
                result[e.to()] = group;
                return true;
            }

            @Override
            protected int[] result() {
                return result;
            }
        }.run(ArrayUtils.reversed(postorder));
    }

    private static int[] createPostorder(Graph graph) {
        DFS<int[]> dfs = new DFS<int[]>(graph) {
            int ptr = 0;
            int[] result = new int[n];

            @Override
            protected void exit(Edge e) {
                result[ptr++] = e.to();
            }

            @Override
            protected int[] result() {
                return result;
            }

            @Override
            protected boolean enter(Edge e) {
                return !visited[e.to()];
            }
        };
        return dfs.run();
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
     * @return The reverse edge if this edge is a flow edge.
     *         It returns null otherwise.
     */
    Edge reversed();
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

class GraphUtils {
    public static Graph transposed(Graph graph) {
        Graph res = new Graph(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            for (Edge e : graph.edges(i)) {
                res.add(e.transposed());
            }
        }
        return res;
    }
    }

abstract class DFS<V> {
    protected final int n;
    protected final Graph graph;
    protected final boolean[] visited;
    private final ListIterator<Edge>[] is;

    public DFS(Graph graph) {
        this.graph = graph;
        n = graph.size();
        visited = new boolean[n];
        //noinspection unchecked
        is = new ListIterator[n];
        for (int i = 0; i < n; i++) is[i] = graph.edges(i).listIterator();
    }

    public V run() {
        return run(ArrayUtils.createOrder(n));
    }

    public V run(int[] order) {
        Arrays.fill(visited, false);
        for (int vertex : order)
            if (!visited[vertex]) {
                enterRoot(vertex);
                run(vertex);
            }
        return result();
    }

    private void run(int vertex) {
        Stack<Edge> path = new Stack<Edge>();
        Edge parent = new SimpleEdge(-1, vertex);
        path.push(null);
        enter(parent);
        while (!path.isEmpty()) {
            visited[parent.to()] = true;
            boolean found = false;
            for (; is[parent.to()].hasNext();
                 is[parent.to()].next()
                    ) {
                Edge e = is[parent.to()].next(); is[parent.to()].previous();
                if (e.transposed() != parent) {
                    if (enter(e)) {
                        found = true;
                        path.push(parent);
                        is[parent.to()].next();
                        parent = e;
                        break;
                    }
                }
            }
            if (!found) {
                exit(parent);
                if (parent.from() >= 0)
                    process(parent);
                parent = path.pop();
            }
        }
    }

    protected V result() {return null;}

    /**
     * enter a new root vertex of a dfs tree.
     */
    protected void enterRoot(int root) {}

    /**
     * enter the vertex e.to() using the edge e.
     * return true if continue dfs from e.to().
     */
    protected boolean enter(Edge e) {return true;}

    protected void exit(Edge e) {}

    protected void process(Edge e) {}
}

class ArrayUtils {


    /**
     * @param n - size of the array
     * @return res[i] = i
     */
    public static int[] createOrder(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = i;
        return res;
    }

    public static void reverse(int[] is) {
        int n = is.length;
        for (int i = 0; i * 2 < n; i++) {
            int tmp = is[i]; is[i] = is[n - 1 - i]; is[n - 1 - i] = tmp;
        }
    }

    public static int[] reversed(int[] is) {
        is = is.clone();
        reverse(is);
        return is;
    }

    }

