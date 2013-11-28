package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Random;

public class GoldenTrees {
    static int MOD = (int) (1e8 + 7);

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int initTax = in.nextInt();
        int s1 = in.nextInt();
        int s2 = in.nextInt();
        int K = in.nextInt();
        int N = in.nextInt() - 1;
        out.println(solve(initTax, s1, s2, K, N));
    }

    long solve(int initTax, int s1, int s2, int K, int N) {
        long[] a = new long[s1 + s2 + 1];
        for (int i = 0; i <= s1 + s2; i++) {
            if (i == 0) a[i] = initTax;
            else if (i <= s1) a[i] = a[i - 1] + 1;
            else {
                a[i] = a[i - 1] + a[i - 1]; if (a[i] >= MOD) a[i] -= MOD;
            }
        }
        if (N < a.length) {
            return a[N];
        }
        N -= s1 + s2;
        int[][] A = new int[K][K];
        for (int i = 0; i < K - 1; i++) A[i][i + 1] = 1;
        for (int i = 0; i < K; i++) A[K - 1][i] = 1;

        int[][] B = new int[K][K];
        for (int i = 0; i < K; i++) B[i][i] = 1;
        while (N > 0) {
            if ((N & 1) == 1) {
                B = mul(A, B);
            }
            A = mul(A, A);
            N >>= 1;
        }
        long res = 1;
        for (int i = 0; i < K; i++) {
            long pow = a[s1 + s2 - K + 1 + i] % MOD;
            long b = B[K - 1][i];
            while (b > 0) {
                if ((b & 1) == 1) {
                    res *= pow;
                    if (res >= MOD) res %= MOD;
                }
                b >>= 1;
                pow = pow * pow;
                if (pow >= MOD) pow %= MOD;
            }
        }
        return res % MOD;
    }

    private long[][] mul(long[][] A, long[][] B) {
        int n = A.length;
        long[][] C = new long[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (B[k][j] > 0 && A[i][k] > Long.MAX_VALUE / 120 / B[k][j]) {
                        if (A[i][k] > MOD) A[i][k] %= MOD - 1;
                        if (B[k][j] > MOD) B[k][j] %= MOD - 1;
                    }
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        return C;
    }

    int[][] mul(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int o = B[0].length;
        int[][] C = new int[m][o];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < o; j++) {
                long sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += (long) A[i][k] * B[k][j];
                }
                C[i][j] = (int) (sum % (MOD - 1));
            }
        }
        return C;
    }

    public static void main(String[] args) {
        Random rnd = new Random(124098124L);
        for (; ; ) {
            System.err.print(".");
            int iT = rnd.nextInt(50) + 1;
            int s1 = rnd.nextInt(50) + 1;
            int s2 = rnd.nextInt(50) + 1;
            int K = rnd.nextInt(s1 + s2 + 1) + 1;
            int N = rnd.nextInt((int) 1e5) + 1;
            long[] a = new long[N + 1];
            for (int i = 0; i <= N; i++) {
                if (i == 0) a[i] = iT;
                else if (i <= s1) a[i] = a[i - 1] + 1;
                else if (i <= s1 + s2) a[i] = a[i - 1] * 2 % MOD;
                else {
                    a[i] = 1;
                    for (int j = 0; j < K; j++) {
                        a[i] = a[i] * a[i - 1 - j] % MOD;
                    }
                }
            }
            if (a[N] != new GoldenTrees().solve(iT, s1, s2, K, N)) {
                System.err.println(iT + " " + s1 + " " + s2 + " " + K + " " + N);
                System.err.println(a[N]);
                System.err.println(new GoldenTrees().solve(iT, s1, s2, K, N));
                throw new AssertionError();
            }
        }
    }
}
