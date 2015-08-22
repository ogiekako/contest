package on2015_08.on2015_08_22_PKU.PKU3155;




import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.*;

/**
 * Densest subgraph problem.
 * Reference: Finding a Maximum Density Subgraph http://www.eecs.berkeley.edu/Pubs/TechRpts/1984/CSD-84-171.pdf
 */
public class PKU3155 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        Graph G = new UndirectedGraph(n);
        for (int i = 0; i < m; i++) {
            G.add(in.nextInt() - 1, in.nextInt() - 1);
        }
        double left = 0, right = 100;
        List<Integer> res = new ArrayList<Integer>();
        for (int _ = 0; _ < 20; _++) {
            double g = (left + right) / 2;
            Graph H = new Graph(n + 2);
            int s = n, t = n + 1;
            for (int i = 0; i < n; i++) {
                H.addFlow(s, i, m);
                H.addFlow(i, t, m + 2 * g - G.edges(i).size());

                for (Edge e : G.edges(i)) {
                    H.addFlow(i, e.to(), 1);
                }
            }
            MaxFlow maxFlow = new MaxFlow(H);
            double flow = maxFlow.maxFlow(s, t);
            // Impossible
            if (flow >= n * m) {
                right = g;
                continue;
            }
            left = g;

            res = maxFlow.getReachableVerticesFrom(s);
            res.remove((Integer) s);
        }
        if (res.isEmpty()) {
            out.println(1);
            out.println(1);
            return;
        }
        out.println(res.size());
        for (int i : res) {
            out.println(i + 1);
        }
    }
}
