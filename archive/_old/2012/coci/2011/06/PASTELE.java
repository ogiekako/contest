package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class PASTELE {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        int[] R = new int[N];
        int[] G = new int[N];
        int[] B = new int[N];
        for (int i = 0; i < N; i++) {
            R[i] = in.nextInt();
            G[i] = in.nextInt();
            B[i] = in.nextInt();
        }
        solve(N, K, R, G, B, out);
    }

    public void solve(int N, int K, int[] R, int[] G, int[] B, PrintWriter out) {
        int[][][] sum = new int[256 + 1][256 + 1][256 + 1];// I,J,K -> sum_{i<I, j<J, k<K} cnt[i][j][k].
        for (int i = 0; i < N; i++) {
            sum[R[i] + 1][G[i] + 1][B[i] + 1]++;
        }
        for (int i = 1; i < 256 + 1; i++)
            for (int j = 1; j < 256 + 1; j++)
                for (int k = 1; k < 256 + 1; k++) {
                    sum[i][j][k] +=
                            sum[i - 1][j][k] + sum[i][j - 1][k] + sum[i][j][k - 1]
                                    - (sum[i - 1][j - 1][k] + sum[i - 1][j][k - 1] + sum[i][j - 1][k - 1])
                                    + sum[i - 1][j - 1][k - 1];
                }
        int left = 0, right = 256;
        do {
            int M = left + right >> 1;
            boolean can = false;
            for (int i = 0; i < 256 - M + 1; i++)
                for (int j = 0; j < 256 - M + 1; j++)
                    for (int k = 0; k < 256 - M + 1; k++) {
                        int count =
                                sum[i + M][j + M][k + M]
                                        - (sum[i][j + M][k + M] + sum[i + M][j][k + M] + sum[i + M][j + M][k])
                                        + (sum[i][j][k + M] + sum[i][j + M][k] + sum[i + M][j][k])
                                        - sum[i][j][k];
                        if (count >= K) {
                            can = true;
                        }
                    }
            if (can) {
                right = M;
            } else {
                left = M;
            }
        } while (right - left > 1);
        out.println(left);
        int M = right;
        boolean found = false;
        for (int i = 0; i < 256 - M + 1; i++)
            for (int j = 0; j < 256 - M + 1; j++)
                for (int k = 0; k < 256 - M + 1; k++) {
                    int count =
                            sum[i + M][j + M][k + M]
                                    - (sum[i][j + M][k + M] + sum[i + M][j][k + M] + sum[i + M][j + M][k])
                                    + (sum[i][j][k + M] + sum[i][j + M][k] + sum[i + M][j][k])
                                    - sum[i][j][k];
                    if (!found && count >= K) {
                        found = true;
                        for (int id = 0, used = 0; id < N && used < K; id++) {
                            if (i <= R[id] && R[id] < i + M
                                    && j <= G[id] && G[id] < j + M
                                    && k <= B[id] && B[id] < k + M) {
                                out.printf("%d %d %d\n", R[id], G[id], B[id]);
                                used++;
                            }
                        }
                    }
                }
    }
}
