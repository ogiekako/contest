package on2015_08.on2015_08_21_AOJ_GRL.CycleDetection;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.TopologicalSort;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class CycleDetection {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        Graph G = new Graph(n);
        for (int i = 0; i < m; i++) {
            G.add(in.nextInt(), in.nextInt());
        }
        out.println(new TopologicalSort(G).sortedOrder() == null ? 1 : 0);
    }
}
