package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class I {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        int F = Math.max(N, M) + 4;
        int Q = in.nextInt();
        int[] as = gen(in, Q, N), bs = gen(in, Q, M), ds = gen(in, Q, Math.max(N, M));
        int H = F + N + F, W = F + M + F;
        int[][] A = new int[H][W];
        int[][] col = new int[H][W];
        for (int i = 0; i < Q; i++) {
            int a = F + as[i] - 1, b = F + bs[i] - 1, d = ds[i];
            A[a][b - d + 1]++;
            A[a][b - d + 2]++;
            A[a][b + d + 1]--;
            A[a][b + d + 2]--;
            col[a - d][b + 1]--;
            col[a - d + 1][b + 1]--;
            col[a + d][b + 1]++;
            col[a + d + 1][b + 1]++;
        }
        for (int i = 0; i < H; i++) {
            for (int j = 1; j < W; j++) A[i][j] += A[i][j - 1];
        }
        for (int j = 0; j < W; j++) for (int i = 1; i < H; i++) col[i][j] += col[i - 1][j];
        for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) A[i][j] += col[i][j];
        for (int j = 0; j < W; j++) for (int i = 0; i < H; i++) if (i + 1 < H && j - 1 >= 0) A[i][j] += A[i + 1][j - 1];
        for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) if (i > 0 && j > 0) A[i][j] += A[i - 1][j - 1];

//        for(int[] a:A)debug(a);

        int P = (int) (1e9 + 7);
        int mod = (int) (1e9 + 9);
        long hash = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                hash = (hash * P + A[F + i][F + j]) % mod;
            }
        out.println(hash);
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int[] gen(MyScanner in, int Q, int M) {
        int a1 = in.nextInt(), mul = in.nextInt(), add = in.nextInt();
        int[] res = new int[Q];
        for (int i = 0; i < Q; i++) {
            res[i] = i == 0 ? a1 : (res[i - 1] * mul + add) % M + 1;
        }
        return res;
    }
}
