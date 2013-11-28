package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

public class MegaFactorial {
    int MOD = (int) (1e9 + 9);
    public int countTrailingZeros(int N, int K, int B) {
        int[] ps = {2, 3, 5, 7};
        int P = -1;
        for (int p : ps) if (B % p == 0) P = p;
        int D = 0;
        while (B % P == 0) {
            B /= P;
            D++;
        }
        long res = solve(N, K, P, MOD);
        if (D == 1) return (int) res;
        int remainder = solve(N, K, P, D);
        res -= remainder;
        if (res < 0) res += MOD;
        return (int) (res * MathUtils.inverse(D, MOD) % MOD);
    }

    private int solve(int N, int K, int p, int MOD) {
        int res = 0;
        for (long P = p; P <= N; P *= p) {
            res += g(N, K - 1, (int) P, MOD);
            if (res >= MOD) res -= MOD;
        }
        return res;
    }

    private int g(int n, int k, int P, int MOD) {
        int m = n / P;
        int N = n - m * P + k;
        return f(N, m, P, k, MOD);
    }

    // \sum_{0\leq a\lt m}{N+aP\choose K}
    private int f(int N, int m, int P, int k, int MOD) {
        int[][] A = new int[k + 1][k + 1];
        for (int i = 0; i < k + 1; i++) {
            A[i][i] = 1;
            if (i > 0) A[i][i - 1] = 1;
        }
        int[] x = new int[k + 1];
        x[0] = 1;
        int[][] AP = Matrix.powered(A, P, MOD);
        return Matrix.powered(A, N, Matrix.sumPowered(AP, m, x, MOD), MOD)[k];
    }
}
