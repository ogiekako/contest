package tmp;

import net.ogiekako.algorithm.graph.problems.Minimize;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KampanjaTask2 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        boolean[][] nei = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            int source = in.nextInt() - 1, destination = in.nextInt() - 1;
            nei[source][destination] = true;
        }
        int res = Minimize.minVertexToTrip010(nei);
        out.println(res);
    }

}
