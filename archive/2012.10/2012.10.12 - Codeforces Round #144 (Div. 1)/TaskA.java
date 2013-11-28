package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskA {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = 100;
        boolean[][] res = new boolean[n][n];
        int k = in.nextInt();
        for (int m = 0; m < n; m++) {
            for (int i = 0; i < m; i++) {
                if (k >= i) {
                    res[i][m] = res[m][i] = true;
                    k -= i;
                }
            }
        }
        out.println(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) out.printf("%d", res[i][j] ? 1 : 0);
            out.println();
        }
    }
}
