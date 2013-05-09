package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.utils.ArrayUtils;

import static java.lang.Math.abs;
import static java.util.Arrays.fill;

public class LinearSystem {

    /**
     * Ax = b を満たすxの解空間 = { X[0] + \sum{c_i X[i]} を求める.
     * 解なしのとき,nullを返す.
     * <p/>
     * O(n m rank).
     *
     * @param A
     * @param b
     * @param EPS
     * @return
     */
    public static double[][] solutionSpace(double[][] A, double[] b, double EPS) {
        int n = A.length, m = A[0].length;
        if (b.length != n) throw new IllegalArgumentException();
        double[][] a = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) a[i][j] = A[i][j];
            a[i][m] = b[i];
        }
        int[] id = new int[n + 1];
        fill(id, -1);
        int rank = 0;
        for (int j = 0; rank < n && j < m; j++) {
            for (int i = rank; i < n; i++) {
                if (abs(a[i][j]) > abs(a[rank][j])) {
                    double[] tmp = a[i];
                    a[i] = a[rank];
                    a[rank] = tmp;
                }
            }
            if (abs(a[rank][j]) < EPS) continue;
            double inv = 1.0 / a[rank][j];
            for (int k = j; k <= m; k++) a[rank][k] *= inv;
            for (int i = 0; i < n; i++)
                if (i != rank) {
                    double d = a[i][j];
                    for (int k = j; k <= m; k++) a[i][k] -= d * a[rank][k];
                }
            id[rank++] = j;
        }
        for (int i = rank; i < n; i++) if (abs(a[i][m]) > EPS) return null;
        double[][] X = new double[m - rank + 1][m];
        for (int j = 0, k = 0; j < m; j++) {
            if (id[k] == j) {
                X[0][j] = a[k++][m];
            } else {
                X[j - k + 1][j] = 1;
                for (int i = 0; i < k; i++) X[j - k + 1][id[i]] = -a[i][j];
            }
        }
        return X;
    }

    /**
     * Aを同じくする複数の連立一次方程式を同時に解く.
     * k = b.length として,
     * Ax = b[i] は, X[i]がnullの時,解なし.そうでないとき,
     * Ax = b[i] を満たすxの解空間 = { X[i] + \sum_{j=k} X[j]) } である.
     * O(n (m+k) rank).
     * Aが正則なとき,bを単位行列とすれば,逆行列の転置が返る.
     * ※ AX=b なるXを求めるわけではないので注意.
     *
     * @param A
     * @param b
     * @param EPS
     * @return X
     */
    public static double[][] solutionSpaces(double[][] A, double[][] b, double EPS) {
        int n = A.length, m = A[0].length;
        int k = b.length;
        for (int i = 0; i < k; i++) if (b[i].length != n) throw new IllegalArgumentException();
        double[][] a = new double[n][m + k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) a[i][j] = A[i][j];
            for (int j = 0; j < k; j++) a[i][m + j] = b[j][i];
        }
        int[] id = new int[n + 1];
        fill(id, -1);
        int rank = 0;
        for (int j = 0; rank < n && j < m; j++) {
            for (int i = rank; i < n; i++) {
                if (abs(a[i][j]) > abs(a[rank][j])) {
                    double[] tmp = a[i];
                    a[i] = a[rank];
                    a[rank] = tmp;
                }
            }
            if (abs(a[rank][j]) < EPS) continue;
            double inv = 1.0 / a[rank][j];
            for (int j2 = j; j2 < m + k; j2++) a[rank][j2] *= inv;
            for (int i = 0; i < n; i++)
                if (i != rank) {
                    double d = a[i][j];
                    for (int j2 = j; j2 < m + k; j2++) a[i][j2] -= d * a[rank][j2];
                }
            id[rank++] = j;
        }
        double[][] X = new double[k + m - rank][m];
        for (int i = rank; i < n; i++) for (int j = 0; j < k; j++) if (abs(a[i][m + j]) > EPS) X[j] = null;
        for (int j = 0, i = 0; j < m; j++) {
            if (id[i] == j) {
                for (int l = 0; l < k; l++) if (X[l] != null) X[l][j] = a[i][m + l];
                i++;
            } else {
                X[j - i + k][j] = 1;
                for (int i2 = 0; i2 < i; i2++) X[j - i + k][id[i2]] = -a[i2][j];
            }
        }
        return X;
    }

    /**
     * O( mx )
     *
     * @param bitVector - bit vector. It should be already normalized.
     * @param rank      - #vector used for calculation
     * @param newRow    - the row which is judged independence
     * @return - true iff newRow is independent
     */
    public static boolean isIndependentFromHighest(long[] bitVector, int rank, long newRow) {
        int mx = 64;
        for (int i = mx - 1, j = 0; j < rank && i >= 0; j++) {
            while (bitVector[j] << 63 - i >= 0) i--;
            if (newRow << 63 - i < 0) newRow ^= bitVector[j];
        }
        return newRow != 0;
    }

    /**
     * 1 + 1 = 0.
     * A * X[0] = b.
     * A * X[i] = 0 (i>0)
     * 体上ならできる.
     * O(n m rank).
     *
     * @param A
     * @param b
     * @return
     */
    public static boolean[][] solutionSpace(boolean[][] A, boolean[] b) {
        int n = A.length, m = A[0].length;
        boolean[][] a = new boolean[n][m + 1];
        ArrayUtils.arraycopy(A, a, 0, 0);
        for (int i = 0; i < n; i++) a[i][m] = b[i];
        int rank = 0;
        int[] id = new int[n];
        for (int j = 0; rank < n && j < m; j++) {
            // choose pivot
            for (int i = rank; i < n; i++) {
                if (a[i][j]) {
                    ArrayUtils.swap(a, rank, i);
                    break;
                }
            }
            if (!a[rank][j]) {// all zero
                continue;
            }
            // elimination
            for (int i = 0; i < n; i++)
                if (i != rank) {
                    if (a[i][j]) {
                        for (int k = j; k <= m; k++) {
                            a[i][k] ^= a[rank][k];
                        }
                    }
                }
            id[rank++] = j;
        }
        for (int i = rank; i < n; i++) {
            // no answer
            if (a[i][m]) return null;
        }

        boolean[][] X = new boolean[n - rank + 1][m];
        for (int j = 0, k = 0; j < m; j++) {
            if (a[k][j]) {
                X[0][j] = a[k++][m];
            } else {
                // identity
                X[j - k + 1][j] = true;
                for (int i = 0; i < k; i++) {
                    X[j - k + 1][id[i]] = a[i][j];
                }
            }
        }
        return X;
    }

    /**
     * @param bitVector - bit vector representing rows of boolean matrix
     * @param n         - #vector used for gauss elimination
     * @param order     - the order of bits used for gauss elimination
     * @return vector is modified. result vector[i] = \sum_j res[i][j] * (given vector[j])
     */
    public static long[] gauss(long[] bitVector, int n, int[] order) {
        int mx = order.length;
        int rank = 0;
        long[] res = new long[n];
        for (int i = 0; i < n; i++) res[i] = 1L << i;
        for (int i = 0; i < mx && rank < n; i++) {
            for (int j = rank; j < n; j++) {
                if ((bitVector[j] >>> order[i] & 1) == 1) {
                    ArrayUtils.swap(bitVector, rank, j);
                    ArrayUtils.swap(res, rank, j);
                    break;
                }
            }
            if ((bitVector[rank] >>> order[i] & 1) == 0) continue;
            for (int j = rank + 1; j < n; j++) {
                if ((bitVector[j] >>> order[i] & 1) == 1) {
                    bitVector[j] ^= bitVector[rank];
                    res[j] ^= res[rank];
                }
            }
            rank++;
        }
        return res;
    }

    public static long[] gaussFromHighest(long[] bitVector, int n) {
        return gauss(bitVector, n, ArrayUtils.createReversedOrder(n));
    }
    public static long[] gaussFromLowest(long[] bitVector, int n) {
        return gauss(bitVector, n, ArrayUtils.createOrder(n));
    }
}
