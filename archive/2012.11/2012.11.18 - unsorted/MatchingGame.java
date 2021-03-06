package tmp;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class MatchingGame {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt(), d = in.nextInt();
        int source = 0, store = 1, key = store + d, box = key + m, sink = box + n;
        Graph graph = new Graph(sink + 1);
        for (int i = 0; i < n; i++) graph.addFlow(box + i, sink, 1, 0);
        for (int i = 0; i < m; i++) {
            int c = in.nextInt();
            int s = in.nextInt() - 1;
            graph.addFlow(store + s, key + i, 1, c);
            int k = in.nextInt();
            for (int j = 0; j < k; j++) {
                int a = in.nextInt() - 1;
                graph.addFlow(key + i, box + a, 1, 0);
            }
        }
        for (int i = 0; i < d; i++) graph.addFlow(source, store + i, in.nextInt(), 0);
        long cost = new MinimumCostFlow(graph).primalDual(source, sink, (long) n);
        int res = cost == Long.MAX_VALUE ? -1 : (int) cost;
        out.println(res);
    }
}
