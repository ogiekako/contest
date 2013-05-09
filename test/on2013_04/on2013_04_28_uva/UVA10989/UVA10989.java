package on2013_04.on2013_04_28_uva.UVA10989;


import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.flow.GlobalMinimumCut;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
// UVA 10989 - Bomb, Divide and Conquer
public class UVA10989 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        for (int testCase = 1; testCase <= N; testCase++) {
            int n = in.nextInt(), m = in.nextInt();
            Graph graph = new BidirectionalGraph(n);
            for (int i = 0; i < m; i++) {
                int x = in.nextInt() - 1, y = in.nextInt() - 1, cost = in.nextInt();
                graph.addFlow(x, y, cost);
            }
            long res = GlobalMinimumCut.globalMinCut((BidirectionalGraph) graph);
            out.printFormat("Case #%d: %d\n", testCase, res);
        }
    }
}
