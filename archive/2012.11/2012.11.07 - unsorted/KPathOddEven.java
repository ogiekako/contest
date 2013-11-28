package utpc;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class KPathOddEven {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();// m <= 100
        int K = in.nextInt();
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            graph[a][b] = graph[b][a] = true;
        }
        int res = solve(graph, K);
        out.println(res == 0 ? "even" : "odd");
    }

    private int solve(boolean[][] graph, int K) {
        int n = graph.length;
        int[][] id = new int[n][n];
        int m = 0;
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (graph[i][j]) id[i][j] = m++;
        if (m == 0) return 0;
        long[][] mat = new long[m][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (graph[i][j]) {
                    for (int k = 0; k < n; k++) if (graph[j][k] && i != k) mat[id[i][j]][id[j][k]] = 1;
                }
        mat = Matrix.powered(mat, K - 1, 4);
        int res = 0;
        for (int i = 0; i < m; i++) for (int j = 0; j < m; j++) res = (int) ((res + mat[i][j]) % 4);
        return res / 2;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        int n = 3;
        Random rnd = new Random(1298203L);
        for (; ; ) {
            int m = rnd.nextInt(n * 2) + 1;
            boolean[][] graph = new boolean[n][n];
            for (int i = 0; i < m; i++) {
                int a = rnd.nextInt(n), b = rnd.nextInt(n);
                if (a != b) graph[a][b] = graph[b][a] = true;
            }
            int K = rnd.nextInt(n / 2 + 1) * 2 + 2;
            int res = new KPathOddEven().solve(graph, K);
            int exp = new KPathOddEven().solveStupid(graph, K);
            if (res != exp) {
                debug("K = ", K);
                for (boolean[] bs : graph) {
                    for (boolean b : bs) System.err.print(b ? 1 : 0);
                    System.err.println("");
                }
            }
            if (exp != res) throw new AssertionError();
        }
    }

    private int solveStupid(boolean[][] graph, int K) {
        int n = graph.length;
        int[][] dp = new int[1 << n][n];
        for (int i = 0; i < n; i++) dp[1 << i][i] = 1;
        for (int i = 0; i < 1 << n; i++)
            for (int j = 0; j < n; j++)
                if ((i >> j & 1) == 1) {
                    for (int k = 0; k < n; k++) if (graph[j][k] && (i >> k & 1) == 0) dp[i | 1 << k][k] += dp[i][j];
                }
        int res = 0;
        for (int i = 0; i < 1 << n; i++)
            if (Integer.bitCount(i) == K + 1) for (int j = 0; j < n; j++) if ((i >> j & 1) == 1) res += dp[i][j];
        debug("A", res);
        return (res / 2) % 2;
    }
}
