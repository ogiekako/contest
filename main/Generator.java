import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.*;
import java.util.*;
public class Generator {
    public static void main(String[] args) throws FileNotFoundException {
        new Generator().run();
    }
    int MAX = 1000000;
    Random rnd = new Random(1029312908L);
    private void run() throws FileNotFoundException {
        for (int n = 10; n <= MAX; n *= 10) {
            long maxEdge = Math.min(MAX, (long) n * n);
            for (int m = 1; m <= maxEdge; m *= 10) {
                for (int t = 0; t < 4; t++) {
                    String fileName = String.format("random_%d_%d_%d.in", n, m, t);
                    System.out.println(fileName);
                    random(n, m, createPrintWriter(fileName));
                }
            }
        }

    }

    PrintWriter createPrintWriter(String fileName) {
        try {
            OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream(fileName));
            PrintWriter out = new PrintWriter(new BufferedWriter(outStream, 131072));
            return out;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("error in createPrintWriter");
        }
    }

    private void random(int n, int m, PrintWriter out) {
        Graph graph = new Graph(n + n + 2);
        List<Long> edges = createRandomEdges(n, m);
        if (edges.size() != m) throw new AssertionError();
        for (long e : edges) {
            int u = (int) (e >> 32);
            int v = (int) e;
            graph.add(new FlowEdge(u, v, 1));
        }
        int s = 0, t = n + n + 1;
        for (int i = 0; i < n; i++) graph.add(new FlowEdge(s, i * 2 + 1, 1));
        for (int i = 0; i < n; i++) graph.add(new FlowEdge(i * 2 + 2, t, 1));
        int solutionSize = (int) MaxFlow.maxFlow(graph, s, t);
        int[] us = new int[m], vs = new int[m], fs = new int[m];
        m = 0;
        for (int u = 1; u <= n + n; u += 2) {
            for (Edge e : graph.edges(u)) {
                int v = e.to();
                if (v != s) {
                    us[m] = u;
                    vs[m] = v;
                    fs[m++] = (int) e.flow();
                }
            }
        }
        if (m != us.length) throw new AssertionError();

        shuffle(n, m, us, vs);

        System.err.println("created");
        System.err.println("size = " + solutionSize);
        out.printf("%d %d\n", n, m);
        for (int i = 0; i < m; i++) {
            out.printf("%d %d %d\n", us[i], vs[i], fs[i]);
        }
        out.flush();
    }
    private void shuffle(int n, int m, int[] us, int[] vs) {
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) perm[i] = i;
        ArrayUtils.shuffle(perm, rnd);
        for (int i = 0; i < m; i++) us[i] = perm[us[i] / 2] * 2 + 1;
        ArrayUtils.shuffle(perm, rnd);
        for (int i = 0; i < m; i++) vs[i] = perm[vs[i] / 2 - 1] * 2 + 2;
    }
    private List<Long> createRandomEdges(int n, int m) {
        long all = (long) n * n;
        if (all < m) throw new AssertionError();
        if (m >= all - m) {
            HashSet<Long> notExists = new HashSet<>(createRandomEdges(n, (int) (all - m)));
            List<Long> edges = new ArrayList<>();
            for (int i = 1; i <= n + n; i += 2)
                for (int j = 2; j <= n + n; j += 2) {
                    long e = (long) i << 32 | j;
                    if (!notExists.contains(e)) edges.add(e);
                }
            Collections.shuffle(edges, rnd);
            return edges;
        }
        HashSet<Long> dejavu = new HashSet<>();
        List<Long> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int u = rnd.nextInt(n) * 2 + 1;
            int v = rnd.nextInt(n) * 2 + 2;
            long e = (long) u << 32 | v;
            if (dejavu.contains(e)) {
                i--; continue;
            }
            dejavu.add(e);
            edges.add(e);
        }
        return edges;
    }

    class E {
        int to;
        int index;
        public E(int to, int index) {
            this.to = to; this.index = index;
        }
    }
}
