package tmp;

import junit.framework.Assert;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class MAR {
    static int INF = (int) 1e9;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int K = in.nextInt();
        int N = 1 << K;
        int[][] A = new int[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) A[i][j] = in.nextInt();
        int res = solveStupid(A);
        out.println(res);
    }

    int solve(int[][] A) {
        int N = A.length;
        int[][] dp = new int[N][N];
        for (int i = 1; i < N; i++) Arrays.fill(dp[i], INF);
        for (int i = 1; i < N; i++) {
            int s = Integer.numberOfTrailingZeros(i);
            int bit = 1 << s;
            int or = bit - 1;
            int and = ~or;
            for (int j = 0; j < N; j++) {
                int nj = j ^ bit;
                int from = nj & and, to = nj | or;
                for (int k = from; k <= to; k++) {
                    dp[i][k] = Math.min(dp[i][k], dp[i - 1][j] + A[j][k]);
                }
            }
        }
        int res = INF;
        for (int a : dp[N - 1]) res = Math.min(res, a);
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(1240912L);
        for (; ; ) {
            int K = 3;
            int N = 1 << K;
            int[][] A = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < i; j++) {
                    A[i][j] = A[j][i] = rnd.nextInt((int) 1e6);
                }
            long start = System.currentTimeMillis();
            int res = new MAR().solve(A);
            System.out.println(System.currentTimeMillis() - start);
            System.out.println(res);
            int exp = solveStupid(A);
            Assert.assertEquals(exp, res);
        }
    }

    private static int solveStupid(int[][] A) {
        int N = A.length;
        int[] is = new int[N];
        for (int i = 0; i < N; i++) is[i] = i;
        int res = INF;
        int K = Integer.numberOfTrailingZeros(N);
        do {
            int[][][] as = new int[K][][];
            as[0] = new int[N][1];
            for (int i = 0; i < N; i++) as[0][i] = new int[]{is[i]};
            int M = N;
            boolean valid = true;
            for (int i = 1; i < K; i++) {
                M /= 2;
                as[i] = new int[M][];
                for (int j = 0; j < M; j++) {
                    as[i][j] = merge(as[i - 1][j * 2], as[i - 1][j * 2 + 1]);
                    if (!consecutive(as[i][j])) valid = false;
                }
            }
            if (valid) {
                int sum = 0;
                for (int i = 0; i < N - 1; i++) sum += A[is[i]][is[i + 1]];
                res = Math.min(res, sum);
            }
        } while (ArrayUtils.nextPermutation(is));
        return res;
    }

    private static boolean consecutive(int[] a) {
        int len = a.length;
        int from = a[0], to = a[len - 1]; return to - from + 1 == len;
    }

    private static int[] merge(int[] as, int[] bs) {
        int[] res = new int[as.length + bs.length];
        for (int i = 0; i < as.length; i++) res[i] = as[i];
        for (int i = 0; i < bs.length; i++) res[i + as.length] = bs[i];
        Arrays.sort(res);
        return res;
    }

}
