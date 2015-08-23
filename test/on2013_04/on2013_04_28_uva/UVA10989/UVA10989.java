package on2013_04.on2013_04_28_uva.UVA10989;


import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.GlobalMinimumCut;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

// UVA 10989 - Bomb, Divide and Conquer
public class UVA10989 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        for (int testCase = 1; testCase <= N; testCase++) {
            int n = in.nextInt(), m = in.nextInt();
            Graph graph = new UndirectedGraph(n);
            for (int i = 0; i < m; i++) {
                int x = in.nextInt() - 1, y = in.nextInt() - 1, cost = in.nextInt();
                graph.addFlow(x, y, (double) cost);
            }
            double res = new GlobalMinimumCut((UndirectedGraph) graph).compute();
            out.printFormat("Case #%d: %d\n", testCase, Math.round(res));
        }
    }
}
