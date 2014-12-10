package src;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class POJ3281 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        int F = in.nextInt();
        int D = in.nextInt();
        Graph graph = new Graph(1 + F + N + N + D + 1);
        for (int i = 0; i < N; i++) graph.addFlow(1 + F + i, 1 + F + N + i, 1);
        for (int i = 0; i < F; i++) graph.addFlow(0, 1 + i, 1);
        for (int i = 0; i < D; i++) graph.addFlow(1 + F + N + N + i, 1 + F + N + N + D, 1);
        for (int i = 0; i < N; i++) {
            int Fi = in.nextInt();
            int Di = in.nextInt();
            for (int j = 0; j < Fi; j++) {
                int f = in.nextInt() - 1;
                graph.addFlow(1 + f, 1 + F + i, 1);
            }
            for (int j = 0; j < Di; j++) {
                int d = in.nextInt() - 1;
                graph.addFlow(1 + F + N + i, 1 + F + N + N + d, 1);
            }
        }
        // POJ3281 でテストしたところ、
        // Long: 1469MS
        // Double: 1313MS だったので、double で統一して良さそう。
        double res = MaxFlow.maxFlow(graph, 0, 1 + F + N + N + D);
        out.println((long)Math.round(res));
    }
}
