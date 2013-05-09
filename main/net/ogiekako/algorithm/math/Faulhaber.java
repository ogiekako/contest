package net.ogiekako.algorithm.math;
/**
 * ファウルハーバーの公式を使って,
 * S(n,k) = sum_{i=0}^n i^k をmod M で求める. ただし,0^0 = 1.
 * 前計算に O(MAX_K^2).
 * クエリに対して O(k).
 * <p/>
 * Mは素数であること.
 * http://ja.wikipedia.org/wiki/ファウルハーバーの公式
 *
 * @author ogiekako
 */
public class Faulhaber {
    public final int M = 1000000007;
    final int MAX_K = 2500 + 10;
    public int S(long n, int k) {
        n %= M;
        if (INV == null) init();
        long res = 0;
        long[] pow = new long[k + 2];
        for (int i = 0; i < k + 2; i++) pow[i] = i == 0 ? 1 : pow[i - 1] * (n + 1) % M;
        for (int j = 0; j < k + 1; j++) {
            res = (res + C[k + 1][j] * B[j] % M * pow[k - j + 1]) % M;
        }
        res = res * INV[k + 1] % M;
        return (int) res;
    }

    long[] INV;
    long[][] C;
    long[] B;// Bernoulli number
    private void init() {
        C = new long[MAX_K][MAX_K];
        for (int i = 0; i < MAX_K; i++)
            for (int j = 0; j < i + 1; j++) {
                C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (C[i][j] >= M) C[i][j] -= M;
            }
        INV = new long[MAX_K];
        for (int i = 1; i < MAX_K; i++) {
            INV[i] = pow(i, M - 2);
        }
        B = new long[MAX_K];
        B[0] = 1;
        for (int n = 1; n < MAX_K - 1; n++) {
            for (int i = 0; i < n; i++) {
                B[n] += C[n + 1][i] * B[i] % M;
                if (B[n] >= M) B[n] -= M;
            }
            B[n] = B[n] * (M - INV[n + 1]) % M;
        }
    }
    long pow(long n, long p) {
        if (p == 0) return 1;
        long n2 = pow(n, p >> 1);
        long res = (p & 1) == 0 ? n2 * n2 : n2 * n2 % M * n % M;
        return res < M ? res : res % M;
    }
}
