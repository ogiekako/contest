package on2013_07.on2013_07_14_kupc2012.TaskJ;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskJ {
    long[][] X = new long[4010][4010];
    int[][] K = new int[4010][4010];
    long[] w = new long[4010];
    long[] sumW = new long[4010];
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        for (int i = 0; i < n; i++) w[i] = in.nextLong();
        for (int i = 0; i < n; i++) sumW[i + 1] = sumW[i] + w[i];
        for (int i = 0; i < n; i++) K[i][i] = i;
        for (int i = 0; i < n; i++) X[i][i] = 0;
        for (int d = 1; d < n; d++) {
            for (int i = 0, j; (j = i + d) < n; i++) {
                // K[i][j-1] ≦ K[i][j] ≦ K[i+1][j]
                X[i][j] = Long.MAX_VALUE;
                for (int s = K[i][j - 1]; s <= Math.min(j - 1, K[i + 1][j]); s++) {
                    long value = X[i][s] + X[s + 1][j] + (sumW[j + 1] - sumW[i]);
                    if (X[i][j] >= value) {
                        X[i][j] = value;
                        K[i][j] = s;
                    }
                }
            }
        }
        out.println(X[0][n - 1]);
    }
}
