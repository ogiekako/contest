package on2013_05.on2013_05_05_pku.PKU2195;


import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;

public class PKU2195 {
    public void solve(int testNumber, MyScanner sc, MyPrintWriter out) {
        int h = sc.nextInt(), w = sc.nextInt();
        if ((h | w) == 0) throw new UnknownError();
        char[][] cs = new char[h][w];
        for (int i = 0; i < h; i++) cs[i] = sc.next().toCharArray();
        ArrayList<Integer> xh = new ArrayList<Integer>(), yh = new ArrayList<Integer>();
        ArrayList<Integer> xm = new ArrayList<Integer>(), ym = new ArrayList<Integer>();
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                if (cs[i][j] == 'H') {
                    xh.add(i);
                    yh.add(j);
                }
                if (cs[i][j] == 'm') {
                    xm.add(i);
                    ym.add(j);
                }
            }
        int n = xh.size();
        Graph graph = new Graph(n * 2 + 2);
        int source = n * 2, sink = source + 1;
        for (int i = 0; i < n; i++) {
            graph.addFlow(source, i, 1., 0.);
            graph.addFlow(n + i, sink, 1., 0.);
            for (int j = 0; j < n; j++) {
                long distance = Math.abs(xh.get(i) - xm.get(j)) + Math.abs(yh.get(i) - ym.get(j));
                graph.addFlow(i, n + j, 1, distance);
            }
        }
        out.println(MinimumCostFlow.minimumCostFlow(graph, source, sink, n));
    }
}
