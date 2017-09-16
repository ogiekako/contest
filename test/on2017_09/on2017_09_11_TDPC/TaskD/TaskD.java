package on2017_09.on2017_09_11_TDPC.TaskD;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        long D = in.nextLong();
        double res = solve(N, D);
        out.println(res);
    }

    private double solve(int N, long D) {
        int[] d = new int[3];
        int[] p = {2, 3, 5};
        for (int i = 0; i < p.length; i++) {
            while (D % p[i] == 0) {
                d[i]++;
                D /= p[i];
            }
        }
        if (D > 1) {
            return 0;
        }
        double[][][][] dp = new double[2][d[0] + 1][d[1] + 1][d[2] + 1];
        int cur = 0, nxt = 1;
        dp[0][0][0][0] = 1;
        int[][] dd = {
                {0, 0, 0}, // 1
                {1, 0, 0}, // 2
                {0, 1, 0}, // 3
                {2, 0, 0}, // 4
                {0, 0, 1}, // 5
                {1, 1, 0}, // 6
        };
        for (int i = 0; i < N; i++) {
            ArrayUtils.fill(dp[nxt], 0);

            for (int j = 0; j < d[0] + 1; j++) {
                for (int k = 0; k < d[1] + 1; k++) {
                    for (int l = 0; l < d[2] + 1; l++) {
                        for (int m = 0; m < 6; m++) {
                            int[] nd = {
                                    Math.min(j + dd[m][0], d[0]),
                                    Math.min(k + dd[m][1], d[1]),
                                    Math.min(l + dd[m][2], d[2]),
                            };
                            dp[nxt][nd[0]][nd[1]][nd[2]] += dp[cur][j][k][l] / 6;
                        }
                    }
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        return dp[cur][d[0]][d[1]][d[2]];
    }
}
