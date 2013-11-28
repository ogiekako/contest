package tmp;

import net.ogiekako.algorithm.graph.denseGraph.Hungarian;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler345 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int[][] mat = new int[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) mat[i][j] = in.nextInt();
        int res = Hungarian.maximumPerfectMatching(mat);
        out.println(res);
    }
}
