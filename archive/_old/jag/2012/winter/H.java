package tmp;

import net.ogiekako.algorithm.graph.problems.SunnyGraph;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class H {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            int s = in.nextInt() - 1, t = in.nextInt() - 1;
            graph[s][t] = graph[t][s] = true;
        }
        boolean res = SunnyGraph.isSunnyGraph(graph, 0);
        out.println(res ? "Yes" : "No");
    }
}
