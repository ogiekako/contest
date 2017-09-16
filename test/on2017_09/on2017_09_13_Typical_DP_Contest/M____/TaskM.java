package on2017_09.on2017_09_13_Typical_DP_Contest.M____;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class TaskM {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int H = in.nextInt(), R = in.nextInt();
        boolean[][] g = new boolean[R][R];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < R; j++) {
                g[i][j] = in.nextInt() == 1;
            }
        }
        Mint.set1e9_7();
        int[][][] dp = new int[R][R][1 << R];
        for (int i = 0; i < R; i++) {
            dp[i][i][1 << i] = 1;
        }
        for (int i = 0; i < R; i++) {
            for (int k = 0; k < 1 << R; k++) {
                for (int j = 0; j < R; j++) {
                    for (int l = 0; l < R; l++) {
                        if (g[j][l] && k << 31 - l >= 0) {
                            dp[i][l][k | 1 << l] += dp[i][j][k];
                            if (dp[i][l][k | 1 << l] >= Mint.getMod()) dp[i][l][k | 1 << l] -= Mint.getMod();
                        }
                    }
                }
            }
        }
        Mint[][] A = new Mint[R][R];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < R; j++) {
                A[i][j] = Mint.ZERO;
                for (int k = 0; k < 1 << R; k++) {
                    A[i][j] = A[i][j].add(dp[i][j][k]);
                }
            }
        }
        Mint[] x = new Mint[R];
        Arrays.fill(x, Mint.ZERO);
        x[0] = Mint.ONE;

        Mint res = Matrix.powered(A, H, x)[0];
        out.println(res);
    }
}
