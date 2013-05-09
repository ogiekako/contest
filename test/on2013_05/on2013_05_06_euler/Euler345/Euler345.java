package on2013_05.on2013_05_06_euler.Euler345;


import net.ogiekako.algorithm.graph.denseGraph.Hungarian;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class Euler345 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[][] mat = new int[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) mat[i][j] = in.nextInt();
        int res = Hungarian.maximumPerfectMatching(mat);
        out.println(res);
    }
}
