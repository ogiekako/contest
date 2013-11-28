package tmp;

import net.ogiekako.algorithm.graph.denseGraph.Edge_;
import net.ogiekako.algorithm.graph.denseGraph.SparseGraph;
import net.ogiekako.algorithm.graph.denseGraph.Vertex;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskD2 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        Vertex[] vs = new Vertex[n];
        for (int i = 0; i < n; i++) vs[i] = new Vertex();
        for (int i = 0; i < n - 1; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1;
            vs[from].add(new Edge_(vs[to]));
            vs[to].add(new Edge_(vs[from]));
        }
        long res = SparseGraph.countPairs(vs, k);
        out.println(res);
    }
}
