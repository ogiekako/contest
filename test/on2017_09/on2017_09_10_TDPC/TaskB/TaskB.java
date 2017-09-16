package on2017_09.on2017_09_10_TDPC.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int A = in.nextInt(), B = in.nextInt();
        a = new int[A];
        b = new int[B];
        for (int i = 0; i < A; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < B; i++) {
            b[i] = in.nextInt();
        }
        dp = new int[A + 1][B + 1];
        ArrayUtils.fill(dp, -1);
        int res = recur(0,0);
        out.println(res);
    }

    int[][] dp;
    int[] a, b;
    private int recur(int i, int j) {
        if (dp[i][j] >= 0) return dp[i][j];
        if (i == a.length && j == b.length) return 0;
        boolean first = (i + j) % 2 == 0;
        int res = 0;
        if (first) {
            if (i < a.length) {
                res = Math.max(res, a[i] + recur(i + 1, j));
            }
            if (j < b.length) {
                res = Math.max(res, b[j] + recur(i, j + 1));
            }
        } else {
            res = Integer.MAX_VALUE;
            if (i < a.length) {
                res = Math.min(res, recur(i + 1, j));
            }
            if (j < b.length) {
                res = Math.min(res, recur(i, j + 1));
            }
        }
        return dp[i][j] = res;
    }

}
