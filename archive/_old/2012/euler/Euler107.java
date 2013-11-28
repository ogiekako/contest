package tmp;

import net.ogiekako.algorithm.graph.denseGraph.DenseGraphUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler107 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long[][] graph = new long[n][n];
        for (int i = 0; i < n; i++) {
            String[] ss = in.next().split(",");
            for (int j = 0; j < n; j++) {
                if (ss[j].equals("-")) {
                    graph[i][j] = Integer.MAX_VALUE;
                } else {
                    graph[i][j] = Integer.valueOf(ss[j]);
                }
            }
        }
        long res = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j] < Integer.MAX_VALUE) res += graph[i][j];
            }
        res -= DenseGraphUtils.minimumSpanningTree(graph);
        out.println(res);
    }
}
