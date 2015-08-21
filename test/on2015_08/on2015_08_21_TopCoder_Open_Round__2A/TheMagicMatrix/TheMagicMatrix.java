package on2015_08.on2015_08_21_TopCoder_Open_Round__2A.TheMagicMatrix;



import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.LinearSystem;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.util.Arrays;

public class TheMagicMatrix {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = 1234567891;
    int k;
    int side;
    int[] rows, cols, values;

    public int find(int side, int[] rows, int[] cols, int[] values) {
        this.side = side;
        this.rows = rows;
        this.cols = cols;
        this.values = values;
        k = rows.length;
        if (side > k) {
            int free = side * side - k - (2 * side - 2);
            return (int) MathUtils.powMod(10, free, MOD);
        }
        long res = 0;// 488281250
        for (int tot = 0; tot < 10; tot++) {
            res = (res + solve(2, tot) * solve(5, tot)) % MOD;
        }
        return (int) res;
    }

    private long solve(int mod, int total) {
        total %= mod;
        int[][] id = new int[side][side];
        int[][] val = new int[side][side];
        for (int i = 0; i < rows.length; i++) {
            id[rows[i]][cols[i]] = -1;
            val[rows[i]][cols[i]] = values[i] % mod;
        }
        int m = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (id[i][j] < 0) continue;
                id[i][j] = m++;
            }
        }
        int n = 2 * side;
        int[][] A = new int[n][m];
        int[] b = new int[n];
        n = 0;
        for (int i = 0; i < side; i++) {
            b[n] = total;
            for (int j = 0; j < side; j++) {
                if (id[i][j] < 0) {
                    b[n] -= val[i][j];
                    if (b[n] < 0) b[n] += mod;
                } else {
                    A[n][id[i][j]] = 1;
                }
            }
            n++;
        }
        for (int j = 0; j < side; j++) {
            b[n] = total;
            for (int i = 0; i < side; i++) {
                if (id[i][j] < 0) {
                    b[n] -= val[i][j];
                    if (b[n] < 0) b[n] += mod;
                } else {
                    A[n][id[i][j]] = 1;
                }
            }
            n++;
        }
        int[][] X = LinearSystem.solutionSpace(A, b, mod);
        if (X == null) return 0;

        if (!Arrays.equals(Matrix.mul(A, X[0], mod), b)) throw new AssertionError();
        for (int i = 1; i < X.length; i++) {
            if (!Arrays.equals(Matrix.mul(A, X[i], mod), new int[n])) throw new AssertionError();
        }

        return MathUtils.powMod(mod, X.length - 1, MOD);
    }
}
