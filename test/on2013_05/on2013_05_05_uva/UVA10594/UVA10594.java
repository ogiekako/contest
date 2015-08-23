package on2013_05.on2013_05_05_uva.UVA10594;


import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.NoSuchElementException;

public class UVA10594 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N;
        try {
            N = in.nextInt();
        } catch (NoSuchElementException e) {
            throw new UnknownError();
        }
        int M = in.nextInt();
        int[] x = new int[M], y = new int[M], cost = new int[M];
        for (int i = 0; i < M; i++) {
            x[i] = in.nextInt() - 1;
            y[i] = in.nextInt() - 1;
            cost[i] = in.nextInt();
        }
        int D = in.nextInt(), K = in.nextInt();
        Graph graph = new UndirectedGraph(N);
        for (int i = 0; i < M; i++) graph.addFlow(x[i], y[i], (double) K, (double) cost[i]);
        double result = new MinimumCostFlow(graph).primalDual(0, N - 1, (long) D);
        if (result == Double.POSITIVE_INFINITY) out.printFormat("Impossible.\n");
        else out.printFormat("%d\n", (long)result);
    }
}
