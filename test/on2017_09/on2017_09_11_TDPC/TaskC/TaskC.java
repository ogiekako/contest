package on2017_09.on2017_09_11_TDPC.TaskC;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int K = in.nextInt();
        int[] R = new int[1 << K];
        for (int i = 0; i < 1 << K; i++) {
            R[i] = in.nextInt();
        }
        int N = 1 << K;
        double[][] p = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                p[i][j] = 1.0 / (1 + Math.pow(10, (R[j] - R[i]) / 400.0));
            }
        }
        double[] dp = new double[N];
        Arrays.fill(dp, 1);
        for (int i = 0; i < K; i++) {
            double[] nDp = new double[N];
            for (int j = 0; j < N; j += 1 << i + 1) {
                for (int k = 0; k < 1 << i; k++) {
                    int x = j + k;
                    for (int l = 0; l < 1 << i; l++) {
                        int y = j + (1 << i) + l;
                        nDp[x] += dp[x] * dp[y] * p[x][y];
                        nDp[y] += dp[x] * dp[y] * p[y][x];
                    }
                }
            }
            dp = nDp;
        }
        for (int i = 0; i < N; i++) {
            out.printFormat("%.9f\n", dp[i]);
        }
    }
}
