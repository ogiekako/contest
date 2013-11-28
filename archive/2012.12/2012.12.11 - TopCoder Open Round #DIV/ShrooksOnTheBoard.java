package tmp;

import net.ogiekako.algorithm.math.Lucas;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.util.Arrays;

public class ShrooksOnTheBoard {
    private static final int MOD = (int) (1e5 + 3);

    public int count(int K, int H, int W) {
        int a = count(K, W);
        long res = MathUtils.powMod(a, H, MOD) - 1;
        if (res < 0) res += MOD;
        return (int) res;
    }

    private int count(int K, int W) {
        if (K < 300) return small(K, W);
        else return large(K, W);
    }

    private int large(int K, int W) {
        int res = 1;
        Lucas lucas = new Lucas(MOD);
        for (int m = 1; W - (m - 1) * K >= 0; m++) {
            res += lucas.lucas(W - (m - 1) * K, m);
            if (res >= MOD) res -= MOD;
        }
        return res;
    }

    private int small(int K, int W) {
        long[][] A = new long[K + 1][K + 1];
        A[0][0] = A[0][K] = 1;
        for (int i = 0; i < K; i++) A[i + 1][i] = 1;
        long[] x = new long[K + 1];
        Arrays.fill(x, 1);
        return (int) Matrix.powered(A, W, x, MOD)[0];
    }
}
