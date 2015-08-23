package src;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class UVA10746 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        if (N == 0) throw new UnknownError();
        int source = N + M, sink = source + 1;
        Graph graph = new Graph(sink + 1);
        for (int i = 0; i < N; i++) graph.addFlow(source, i, 1, 0);
        for (int i = 0; i < M; i++) graph.addFlow(N + i, sink, 1, 0);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                long cost = Math.round(in.nextDouble() * 100);
                graph.addFlow(i, N + j, 1, cost);
            }
        long cost = new MinimumCostFlow(graph).primalDual(source, sink, (long) N);
        double res = cost / 100.0 / N + 1e-6;
        out.printFormat("%.2f\n", res);
    }
}
