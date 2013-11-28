package src;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class UVA11594 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d:\n", testNumber);
        int n = in.nextInt();
        long[][] cap = new long[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) cap[i][j] = in.nextInt();
        Graph graph = MinimumCutTree.minCutTree(cap);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                long flow = MaxFlow.maxFlow(graph, i, j);
                MaxFlow.maxFlow(graph, j, i, flow);
                if (flow == Long.MAX_VALUE) flow = 0;
                out.printFormat("%d%c", flow, j == n - 1 ? '\n' : ' ');
            }
    }
}
