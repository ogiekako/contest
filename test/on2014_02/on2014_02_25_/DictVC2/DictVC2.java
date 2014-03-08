package on2014_02.on2014_02_25_.DictVC2;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.SCC;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.*;

public class DictVC2 {
    int n, m, s, t;
    int N;
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }
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
        @Override
        public String toString() {
            return "Vertex{" +
                    "indices=" + indices +
                    '}';
        }
    }

    private boolean[] computeVC(Graph g) {
        Queue<Integer> que = new LinkedList<>();
        que.offer(s);
        boolean[] reachable = new boolean[n + n + 2];
        while (!que.isEmpty()) {
            int v = que.poll();
            reachable[v] = true;
            for (Edge e : g.edges(v))
                if (!reachable[e.to()]) {
                    que.offer(e.to());
                }
        }
        boolean[] res = new boolean[n + n];
        for (int i = 0; i < n + n; i++) {
            if (i % 2 == 0 && !reachable[i]) res[i] = true;
            if (i % 2 == 1 && reachable[i]) res[i] = true;
        }
        return res;
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
