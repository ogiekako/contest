package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        int k = in.nextInt();
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) a[i][j] = in.nextInt();
        int F = k + 3;
        long[][] A = new long[F * 2 + n][F * 2 + m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                int x = i + F + 1, y = j + F;
                A[x - k][y] += a[i][j]; A[x][y + k] += a[i][j];
                A[x + k][y] -= a[i][j]; A[x][y - k] -= a[i][j];
            }
        for (int i = 0; i < A.length - 1; i++) for (int j = 1; j < A[i].length; j++) A[i + 1][j - 1] += A[i][j];
        long[][] B = new long[F * 2 + n][F * 2 + m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                int x = i + F + 1, y = j + F + 1;
                B[x - k][y] -= a[i][j]; B[x][y - k] -= a[i][j];
                B[x + k][y] += a[i][j]; B[x][y + k] += a[i][j];
            }
        for (int i = 0; i < A.length - 1; i++) for (int j = 0; j < A[i].length - 1; j++) B[i + 1][j + 1] += B[i][j];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[i].length; j++) A[i][j] += B[i][j];
        for (int i = 0; i < A.length - 1; i++) for (int j = 0; j < A[i].length; j++) A[i + 1][j] += A[i][j];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[i].length - 1; j++) A[i][j + 1] += A[i][j];
        long max = -1;
        int resA = -1, resB = -1;
        for (int i = k - 1; i < n - k + 1; i++)
            for (int j = k - 1; j < m - k + 1; j++) {
                long val = A[F + i][F + j];
                if (max < val) {
                    max = val;
                    resA = i + 1;
                    resB = j + 1;
                }
            }
        out.printFormat("%d %d\n", resA, resB);
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
