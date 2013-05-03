package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PowerOperation;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Cast;
import net.ogiekako.algorithm.utils.interfaces.NoCojacInstrumentation;

import java.util.Arrays;

/*
- [[A 0][E E]]^n の左下に E+A+A^2+... + A^n-1 がでてくる
*/
@NoCojacInstrumentation
public class Matrix {
    // Av % MOD
    // MOD * MOD < Long.MAX_VALUE
    // 0 <= A[i][j], B[i][j] < MOD
    public static long[] mul(long[][] A, long[] v, int MOD) {
        int n = A.length, m = A[0].length;
        long[] res = new long[n];
        long sub = Long.MAX_VALUE / MOD * MOD;
        for (int j = 0; j < m; j++)
            if (v[j] != 0) for (int i = 0; i < n; i++) {
                res[i] = res[i] + A[i][j] * v[j];
                if (res[i] < 0) res[i] -= sub;
            }
        for (int i = 0; i < n; i++) res[i] %= MOD;
        return res;
    }

    // AB % MOD
    // MOD * MOD < Long.MAX_VALUE
    // 0 <= A[i][j], B[i][j] < MOD
    public static long[][] mul(long[][] A, long[][] B, int MOD) {
        int n = A.length, m = B[0].length, s = B.length;
        long[][] res = new long[n][m];
        long sub = Long.MAX_VALUE / MOD * MOD;
        for (int i = 0; i < n; i++)
            for (int k = 0; k < s; k++)
                if (A[i][k] > 0) for (int j = 0; j < m; j++) {
                    res[i][j] += A[i][k] * B[k][j];
                    if (res[i][j] < 0) res[i][j] -= sub;
                }
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) res[i][j] %= MOD;
        return res;
    }

    public static long[][] identity(int n) {
        long[][] res = new long[n][n];
        for (int i = 0; i < n; i++) res[i][i] = 1;
        return res;
    }

    /**
     * Compute A^p % mod.
     *
     * If you want to compute A^p x % mod for some vector x, use
     * powered(A,p,x,mod). The method is faster than this method.
     *
     * Order: O(n^3 log_p)
     */
    public static long[][] powered(long[][] A, long power, final int mod) {
        if (power == 0) return Matrix.identity(A.length);
        return new PowerOperation<long[][]>() {
            @Override
            protected long[][] associativeOperation(long[][] a, long[][] b) {
                return Matrix.mul(a, b, mod);
            }
        }.power(A, power);
    }

    public static int[][] powered(int[][] A, long p, int MOD) {
        return Cast.toInt(powered(Cast.toLong(A), p, MOD));
    }

    private static long[][] add(long[][] A, long[][] B, int MOD) {
        int n = A.length, m = A[0].length;
        long[][] C = new long[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                C[i][j] = A[i][j] + B[i][j];
                if (C[i][j] >= MOD) C[i][j] -= MOD;
            }
        return C;
    }

    // (A-B) % MOD (>=0) 0(n^2)
    public static long[][] sub(long[][] A, long[][] B, int MOD) {
        int n = A.length, m = A[0].length;
        long[][] res = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = A[i][j] - B[i][j];
                if (res[i][j] < 0)
                    res[i][j] += MOD;
            }
        }
        return res;
    }

    /**
     * 破壊的
     *
     * @param mat
     * @return
     */

    public static double determinantDestructive(double[][] mat) {
        double EPS = 1e-9;
        int n = mat.length;
        double res = 1;
        for (int i = 0; i < n; i++) {
            int pivot = i;
            for (int j = i; j < n; j++) {
                if (Math.abs(mat[j][i]) > Math.abs(mat[pivot][i])) {
                    pivot = j;
                }
            }
            // rank < n
            if (Math.abs(mat[pivot][i]) < EPS) return 0;
            if (i != pivot) {
                res *= -1;
                ArrayUtils.swap(mat, i, pivot);
            }
            res *= mat[i][i];
            for (int j = i + 1; j < n; j++) {
                double mul = mat[j][i] / mat[i][i];
                for (int k = i; k < n; k++) {
                    mat[j][k] -= mat[i][k] * mul;
                }
            }
        }
        return res;
    }

    public static long[] mul(long[][] A, long[] v) {
        int n = A.length, m = A[0].length;
        if (v.length != m) throw new IllegalArgumentException();
        long[] Av = new long[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) Av[i] += A[i][j] * v[j];
        return Av;
    }

    /**
     * POJ 3318
     * n=512 -> 6688MS
     * strassenのアルゴリズム.
     *
     * @param A
     * @param B
     * @return
     */
    public static int[][] strassen(int[][] A, int[][] B) {
        int n = A.length;
        if (Integer.bitCount(n) != 1) throw new IllegalArgumentException("n must be power of 2.");
        if (A[0].length != n || B.length != n || B[0].length != n) throw new IllegalArgumentException();
        return strassen0(A, B);
    }

    private static int[][] strassen0(int[][] A, int[][] B) {
        int n = A.length;
        if (n <= 64) return mul(A, B);
        int n2 = n >> 1;
        int[][] A11 = ArrayUtils.part(A, 0, 0, n2, n2);
        int[][] A12 = ArrayUtils.part(A, 0, n2, n2, n);
        int[][] A21 = ArrayUtils.part(A, n2, 0, n, n2);
        int[][] A22 = ArrayUtils.part(A, n2, n2, n, n);
        int[][] B11 = ArrayUtils.part(B, 0, 0, n2, n2);
        int[][] B12 = ArrayUtils.part(B, 0, n2, n2, n);
        int[][] B21 = ArrayUtils.part(B, n2, 0, n, n2);
        int[][] B22 = ArrayUtils.part(B, n2, n2, n, n);

        int[][] P1 = strassen0(add(A11, A22), add(B11, B22));
        int[][] P2 = strassen0(add(A21, A22), B11);
        int[][] P3 = strassen0(A11, sub(B12, B22));
        int[][] P4 = strassen0(A22, sub(B21, B11));
        int[][] P5 = strassen0(add(A11, A12), B22);
        int[][] P6 = strassen0(sub(A21, A11), add(B11, B12));
        int[][] P7 = strassen0(sub(A12, A22), add(B21, B22));

        int[][] C11 = add(sub(add(P1, P4), P5), P7);
        int[][] C12 = add(P3, P5);
        int[][] C21 = add(P2, P4);
        int[][] C22 = add(sub(add(P1, P3), P2), P6);

        int[][] C = new int[n][n];
        ArrayUtils.arraycopy(C11, C, 0, 0);
        ArrayUtils.arraycopy(C12, C, 0, n2);
        ArrayUtils.arraycopy(C21, C, n2, 0);
        ArrayUtils.arraycopy(C22, C, n2, n2);
        return C;
    }

    private static int[][] sub(int[][] A, int[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) throw new IllegalArgumentException();
        int n = A.length, m = A[0].length;
        int[][] C = new int[n][m];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    private static int[][] add(int[][] A, int[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) throw new IllegalArgumentException();
        int n = A.length, m = A[0].length;
        int[][] C = new int[n][m];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    public static int[][] mul(int[][] A, int[][] B) {
        int n = A.length, r = A[0].length, m = B[0].length;
        int[][] C = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < r; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
        return C;
    }

    /**
     * res = A * v.
     * 1 + 1 = 0.
     *
     * @param A
     * @param v
     * @return
     */
    public static boolean[] mul(boolean[][] A, boolean[] v) {
        boolean[] res = new boolean[A.length];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[0].length; j++) res[i] ^= A[i][j] && v[j];
        return res;
    }

    public static boolean[][] mul(boolean[][] A, boolean[][] B) {
        int n = A.length, r = A[0].length, m = B[0].length;
        boolean[][] C = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < r; k++) {
                    C[i][j] ^= A[i][k] && B[k][j];
                }
        return C;
    }

    public static int determinantDestructive(long[][] A, int modPrime) {
        int n = A.length;
        long res = 1;
        for (int i = 0; i < n; i++) {
            int pivot = i;
            for (int j = i; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[pivot][i])) {
                    pivot = j;
                }
            }
            // rank < n
            if (A[pivot][i] == 0) return 0;
            if (i != pivot) {
                ArrayUtils.swap(A, i, pivot);
                res = -res;
                if (res < 0) res += modPrime;
            }
            res = res * A[i][i] % modPrime;
            long inv = MathUtils.inverse(A[i][i], modPrime);
            for (int j = i + 1; j < n; j++) {
                long mul = A[j][i] * inv % modPrime;
                for (int k = i; k < n; k++) {
                    A[j][k] -= A[i][k] * mul % modPrime;
                    if (A[j][k] < 0) A[j][k] += modPrime;
                }
            }
        }
        return (int) res;
    }

    /**
     * @param A   - n x n matrix.
     * @param p   - non negative number
     * @param x   - initial values
     * @param MOD - modulo
     * @return \sum_{i=0}^{p-1} A^i x % MOD.
     * @verified
     */
    public static long[] sumPowered(long[][] A, long p, long[] x, int MOD) {
        int n = A.length;
        long[][] A2 = new long[n + 1][n + 1];
        for (int i = 0; i < n; i++) System.arraycopy(A[i], 0, A2[i], 0, n);
        for (int i = 0; i < n; i++) A2[i][n] = x[i];
        A2[n][n] = 1;
        long[] x2 = new long[n + 1];
        x2[n] = 1;
        x2 = powered(A2, p, x2, MOD);
        long[] res = new long[n];
        System.arraycopy(x2, 0, res, 0, res.length);
        return res;
    }

    /**
     * @param A   - n x n matrix.
     * @param p   - non negative number
     * @param x   - initial values
     * @param MOD - modulo
     * @return \sum_{i=0}^{p-1} A^i x % MOD.
     * @verified
     */

    public static int[] sumPowered(int[][] A, long p, int[] x, int MOD) {
        int n = A.length;
        int[][] A2 = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) System.arraycopy(A[i], 0, A2[i], 0, n);
        for (int i = 0; i < n; i++) A2[i][n] = x[i];
        A2[n][n] = 1;
        int[] x2 = new int[n + 1];
        x2[n] = 1;
        x2 = powered(A2, p, x2, MOD);
        int[] res = new int[n];
        System.arraycopy(x2, 0, res, 0, res.length);
        return res;
    }

    //  A^k x % MOD.
    // O(p^2 n + p n^2 + n^2 log k).
    public static long[] powered(final long[][] A, long k, long[] x, final int MOD) {
        return powered(new LongLinearTransform() {
            public long[] map(long[] x) {
                return mul(A, x, MOD);
            }
        }, k, x, MOD);
    }

    //  A^k x % MOD.
    // O(p^2 n + p n^2 + n^2 log k).
    public static int[] powered(final int[][] A, long k, int[] x, final int MOD) {
        return Cast.toInt(powered(Cast.toLong(A), k, Cast.toLong(x), MOD));
    }

    // f^k(x).
    // O(p^2 n + p O(f) + n^2 log k).
    public static long[] powered(LongLinearTransform f, long k, long[] x, int modPrime) {
        // O(p(pn + O(f.map))
        long[] R = characteristicPolynomial(f, x, modPrime);
        // O(n^2 log k)
        long[] P = Polynomial.mod(k, R, modPrime);
        // compute P(f) x.
        int n = x.length;
        long[] res = new long[n];
        long sub = Long.MAX_VALUE / modPrime * modPrime;
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < n; j++) {
                res[j] += x[j] * P[i];
                if (res[j] < 0) res[j] -= sub;
            }
            x = f.map(x);
        }
        for (int i = 0; i < n; i++) res[i] %= modPrime;
        return res;
    }

    // (r[0]A^0 + r[1]A^1 + ... + r[p]A^p)x = 0
    // O(p(log MOD + pn + O(f.map)) : p = deg(f) <= n.
    static long[] characteristicPolynomial(LongLinearTransform f, long[] x, int MOD) {
        long sub = Long.MAX_VALUE / MOD * MOD;// largest multiple of MOD.
        int n = x.length;
        long[][] B = new long[n + 1][n];// gauss
        long[][] C = new long[n + 1][n + 1];// B = C mat
        int[] pivot = new int[n + 1];
        Arrays.fill(pivot, -1);
        for (int i = 0; ; i++) {
            // B[i] is modified, hence x must be cloned.
            B[i] = x.clone();
            C[i][i] = 1;
            // pivot[j] is the pivot of the j-th row. B[j][pivot[j]] == 1.
            for (int j = 0; j < i; j++)
                if (B[i][pivot[j]] > 0) {// eliminate
                    long mul = MOD - B[i][pivot[j]] % MOD;// B[i] is modified during this loop. so B[i][pivot[j]] may be no longer smaller than MOD.
                    // Do B[i] += B[j] * mul.
                    // B[j] is represented by C[j], hence C[i] += C[j] * mul.
                    for (int k = 0; k < n; k++) {
                        B[i][k] += B[j][k] * mul;
                        if (B[i][k] < 0) B[i][k] -= sub;
                        C[i][k] += C[j][k] * mul;
                        if (C[i][k] < 0) C[i][k] -= sub;
                    }
                }
            for (int k = 0; k < n; k++) B[i][k] %= MOD;
            for (int k = 0; k < n; k++) C[i][k] %= MOD;
            for (int k = 0; k < n; k++)
                if (B[i][k] > 0) {
                    pivot[i] = k;
                    // log MOD
                    long mul = MathUtils.inverse(B[i][k], MOD);
                    for (int l = 0; l < n; l++) if (B[i][l] > 0) B[i][l] = B[i][l] * mul % MOD;
                    for (int l = 0; l < n; l++) C[i][l] = C[i][l] * mul % MOD;
                    break;
                }
            if (pivot[i] == -1) {
                long[] r = new long[i + 1];
                for (int j = 0; j < i + 1; j++) r[j] = C[i][j];
                return r;
            }
            x = f.map(x);
        }
    }

    public static int rank(long[] bitVector, int n) {
        int mx = 64;
        int rank = 0;
        for (int i = mx - 1; i >= 0 && rank < n; i--) {
            for (int j = rank; j < n; j++) {
                if ((bitVector[j] >>> i & 1) == 1) {
                    ArrayUtils.swap(bitVector, rank, j);
                    break;
                }
            }
            if ((bitVector[rank] >>> i & 1) == 0) continue;
            for (int j = rank + 1; j < n; j++) {
                if ((bitVector[j] >>> i & 1) == 1) {
                    bitVector[j] ^= bitVector[rank];
                }
            }
            rank++;
        }
        return rank;
    }

    public static int rank(boolean[][] matrix) {
        int n = matrix.length;
        int rank = 0;
        for (int i = 0; i < n && rank < n; i++) {
            for (int j = rank; j < n; j++) {
                if (matrix[j][i]) {
                    ArrayUtils.swap(matrix, rank, j);
                    break;
                }
            }
            if (!matrix[rank][i]) continue;
            for (int j = rank + 1; j < n; j++) {
                if (matrix[j][i]) {
                    xorEq(matrix[j], matrix[rank]);
                }
            }
            rank++;
        }
        return rank;
    }

    private static void xorEq(boolean[] modified, boolean[] a) {
        for (int i = 0; i < a.length; i++) modified[i] ^= a[i];
    }

    /////////////////////////////
    ///// ↓ no longer used /////
    /////////////////////////////
        // A^p  O(n^2 log_p)
    private static long[][] poweredStrassen(long[][] _A, long p, int MOD) {
        int n = 1;
        while (n < _A.length) n <<= 1;
        long[][] A = new long[n][n];
        for (int i = 0; i < _A.length; i++) for (int j = 0; j < _A[0].length; j++) A[i][j] = _A[i][j];
        long[][] res = identity(n);
        for (; p > 0; p >>= 1) {
            if ((p & 1) == 1) res = strassen(res, A, MOD);
            A = strassen(A, A, MOD);
        }
        long[][] R = new long[_A.length][_A.length];
        for (int i = 0; i < _A.length; i++) for (int j = 0; j < _A[0].length; j++) R[i][j] = res[i][j];
        return R;
    }

    private static long[][] strassen(long[][] A, long[][] B, int MOD) {
        int n = A.length;
        if (n <= 64) return mul(A, B, MOD);
        int n2 = n >> 1;
        long[][] A11 = ArrayUtils.part(A, 0, 0, n2, n2);
        long[][] A12 = ArrayUtils.part(A, 0, n2, n2, n);
        long[][] A21 = ArrayUtils.part(A, n2, 0, n, n2);
        long[][] A22 = ArrayUtils.part(A, n2, n2, n, n);
        long[][] B11 = ArrayUtils.part(B, 0, 0, n2, n2);
        long[][] B12 = ArrayUtils.part(B, 0, n2, n2, n);
        long[][] B21 = ArrayUtils.part(B, n2, 0, n, n2);
        long[][] B22 = ArrayUtils.part(B, n2, n2, n, n);

        long[][] P1 = strassen(add(A11, A22, MOD), add(B11, B22, MOD), MOD);
        long[][] P2 = strassen(add(A21, A22, MOD), B11, MOD);
        long[][] P3 = strassen(A11, sub(B12, B22, MOD), MOD);
        long[][] P4 = strassen(A22, sub(B21, B11, MOD), MOD);
        long[][] P5 = strassen(add(A11, A12, MOD), B22, MOD);
        long[][] P6 = strassen(sub(A21, A11, MOD), add(B11, B12, MOD), MOD);
        long[][] P7 = strassen(sub(A12, A22, MOD), add(B21, B22, MOD), MOD);

        long[][] C11 = add(sub(add(P1, P4, MOD), P5, MOD), P7, MOD);
        long[][] C12 = add(P3, P5, MOD);
        long[][] C21 = add(P2, P4, MOD);
        long[][] C22 = add(sub(add(P1, P3, MOD), P2, MOD), P6, MOD);

        long[][] C = new long[n][n];
        ArrayUtils.arraycopy(C11, C, 0, 0);
        ArrayUtils.arraycopy(C12, C, 0, n2);
        ArrayUtils.arraycopy(C21, C, n2, 0);
        ArrayUtils.arraycopy(C22, C, n2, n2);
        return C;
    }

}
