package tmp;

import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class POJ1459_MaxFlow_PowerNetwork {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        if (n == -1) throw new UnknownError();
        int np = in.nextInt(), nc = in.nextInt();
        int m = in.nextInt();
        Graph graph = new Graph(1 + n + 1);
        for (int i = 0; i < m; i++) {
            String[] ss = in.next().split("[(,)]");
            int from = Integer.valueOf(ss[1]);
            int to = Integer.valueOf(ss[2]);
            int cap = Integer.valueOf(ss[3]);
            graph.add(new FlowEdge(1 + from, 1 + to, cap));
        }
        for (int i = 0; i < np; i++) {
            String[] ss = in.next().split("[()]");
            int to = Integer.valueOf(ss[1]);
            int cap = Integer.valueOf(ss[2]);
            graph.add(new FlowEdge(0, 1 + to, cap));
        }
        for (int i = 0; i < nc; i++) {
            String[] ss = in.next().split("[()]");
            int from = Integer.valueOf(ss[1]);
            int cap = Integer.valueOf(ss[2]);
            graph.add(new FlowEdge(1 + from, 1 + n, cap));
        }
        long res = MinimumCutTree.maxFlow(graph, 0, 1 + n);
        out.println(res);
    }
}
