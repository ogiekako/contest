package net.ogiekako.algorithm.math.linearAlgebra;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class LinearSystem {

    /**
     * In O(n^3) time,
     * compute the number of spanning trees in G.
     * Given G should represent a undirected graph.
     * G[i][j] is the number of edges between i and j.
     *
     * See Programming Contest Book for a proof.
     * <p>Tested.
     * Verified: SRM</p>
     */
    public static Mint numberOfSpanningTrees(long[][] G) {
        int n = G.length;
        if (n == 0) return Mint.ZERO;
        if (n == 1) return Mint.ONE;
        // D - G
        Mint[][] L = new Mint[n][n];
        ArrayUtils.fill(L, Mint.ZERO);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                L[i][i] = L[i][i].add(G[i][j]);
                L[i][j] = L[i][j].minus(G[i][j]);
            }
        }

        Mint[][] L0 = new Mint[n - 1][n - 1];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                L0[i][j] = L[i + 1][j + 1];
            }
        }
        return Mint.of(Matrix.determinant(L0));
    }

    /**
     * In O(n^2) time,
     * compute the unique polynomial P such that the degree of P is n-1 and
     * P(i) = y_i for i = 0, ..., n-1.
     *
     * <p>Verified: TCO R3B 1000</p>
     */
    public static Polynomial interpolate(Mint[] y) {
        Mint[] x = new Mint[y.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = Mint.of(i);
        }
        return interpolate(x, y);
    }

    /**
     * f(x_0) = y_0  ...  f(x_n) = y_n
     * となるような n 次多項式 を、ラグランジュ補間によって求める。
     */
    public static Polynomial interpolate(Mint[] x, Mint[] y) {
        int n = y.length;
        // (x-1)(x-2)(x-3)
        Polynomial P = Polynomial.of(1);
        for (int i = 0; i < n; i++) {
            P = P.mul(x[i].addInv(), Mint.ONE);
        }
        Polynomial f = Polynomial.ZERO;
        for (int i = 0; i < n; i++) {
            Mint a = Mint.ONE;
            for (int j = 0; j < n; j++) if(i!=j) {
                a = a.mul(x[i].minus(x[j]));
            }
            // y / (1-2)(1-3)
            a = a.mulInv().mul(y[i]);
            // (x-2)(x-3)
            Polynomial Q = P.divMod(Polynomial.of(x[i].addInv(), Mint.ONE)).first;

            f = f.add(Q.mul(a));
        }
        return f;
    }

    /**
     * f(x_0) = y_0  ...  f(x_n) = y_n
     * となるような n 次多項式 を、ニュートン補間によって求める。
     * interpolate の方が高速。
     */
    public static Polynomial interpolateNewton(Mint[] x, Mint[] y) {
        int n = y.length;
        // Newton interpolation.
        Mint[] f = new Mint[n];
        // P(x) = \sum_{0 <= k < n}[x_0, ..., x_k](x - x_0)...(x - x_{k-1})
        Polynomial P = Polynomial.ZERO;
        Polynomial g = Polynomial.of(1);
        for (int k = 0; k < n; k++) {
            for (int i = 0; i + k < n; i++) {// [x_i, ..., x_{i+k}]
                int j = i + k;
                // [x_i] = y_i
                // [x_i, ..., x_j] = ([x_{i+1}, ..., x_j] - [x_i, ... ,x_{j-1}]) / (x_j - x_i)
                f[i] = i == j ? y[i] : f[i+1].minus(f[i]).div(x[j].minus(x[i]));
            }
            // [x_0, ..., x_k](x - x_0)...(x - x_{k-1})
            P = P.add(g.mul(f[0]));
            g = g.mul(x[k].addInv(), Mint.ONE); // x - x_k
        }
        return P;
    }

    /**
     * Ax = b を満たすxの解空間 = { X[0] + \sum{c_i X[i]} を求める.
     * 解なしのとき,nullを返す.
     * <p/>
     * O(n m rank).
     *
     * <p>
     *     Verified: AOJ1095 KND Factory
     * </p>
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
        Arrays.fill(id, -1);
        int rank = 0;
        for (int j = 0; rank < n && j < m; j++) {
            for (int i = rank; i < n; i++) {
                if (Math.abs(a[i][j]) > Math.abs(a[rank][j])) {
                    double[] tmp = a[i];
                    a[i] = a[rank];
                    a[rank] = tmp;
                }
            }
            if (Math.abs(a[rank][j]) < EPS) continue;
            double inv = 1.0 / a[rank][j];
            for (int k = j; k <= m; k++) a[rank][k] *= inv;
            for (int i = 0; i < n; i++)
                if (i != rank) {
                    double d = a[i][j];
                    for (int k = j; k <= m; k++) a[i][k] -= d * a[rank][k];
                }
            id[rank++] = j;
        }
        for (int i = rank; i < n; i++) if (Math.abs(a[i][m]) > EPS) return null;
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
     * ※ AX=b と なるXを求めるわけではないので注意.
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
        Arrays.fill(id, -1);
        int rank = 0;
        for (int j = 0; rank < n && j < m; j++) {
            for (int i = rank; i < n; i++) {
                if (Math.abs(a[i][j]) > Math.abs(a[rank][j])) {
                    double[] tmp = a[i];
                    a[i] = a[rank];
                    a[rank] = tmp;
                }
            }
            if (Math.abs(a[rank][j]) < EPS) continue;
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
        for (int i = rank; i < n; i++) for (int j = 0; j < k; j++) if (Math.abs(a[i][m + j]) > EPS) X[j] = null;
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
     * Solve Ax = b.
     * <p/>
     * Returns null if there is no solution.
     * Otherwise returns X such that x = X[0] + sum_{i>0} ci X[i] is a solution for any ci.
     * <p/>
     * O(n m rank(A)), where n = |A| (the number of the constraints) and m = |A[0]| (the number of the variables).
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
     * Solve Ax = b (mod `modPrime').
     * <p/>
     * Returns null if there is no solution.
     * Otherwise returns X such that x = X[0] + sum_{i>0} ci X[i] is a solution for any ci.
     * <p/>
     * O(n m rank(A)), where n = |A| (the number of the constraints) and m = |A[0]| (the number of the variables).
     * <p/>
     * <p>
     * Verified: TCO13 R2A 600
     * </p>
     */
    public static int[][] solutionSpace(int[][] A, int[] b, int modPrime) {
        int n = A.length, m = A[0].length;
        if (b.length != n) throw new IllegalArgumentException();
        long[][] a = new long[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) a[i][j] = A[i][j];
            a[i][m] = b[i];
        }
        int[] id = new int[n + 1];
        Arrays.fill(id, -1);
        int rank = 0;
        for (int j = 0; rank < n && j < m; j++) {
            // Avoid / by zero.
            for (int i = rank; i < n; i++) {
                if (a[i][j] > a[rank][j]) {
                    long[] tmp = a[i];
                    a[i] = a[rank];
                    a[rank] = tmp;
                }
            }
            if (a[rank][j] == 0) continue;
            long inv = MathUtils.inverse(a[rank][j], modPrime);
            for (int k = j; k <= m; k++) a[rank][k] = a[rank][k] * inv % modPrime;
            for (int i = 0; i < n; i++) {
                if (i == rank) continue;
                long d = a[i][j];
                for (int k = j; k <= m; k++) {
                    a[i][k] -= d * a[rank][k] % modPrime;
                    if (a[i][k] < 0) a[i][k] += modPrime;
                }
            }
            id[rank++] = j;
        }
        for (int i = rank; i < n; i++) if (a[i][m] > 0) return null;
        int[][] X = new int[m - rank + 1][m];
        for (int j = 0, k = 0; j < m; j++) {
            if (id[k] == j) {
                X[0][j] = (int) a[k++][m];
            } else {
                X[j - k + 1][j] = 1;
                for (int i = 0; i < k; i++) {
                    long v = a[i][j] == 0 ? 0 : modPrime - a[i][j];
                    X[j - k + 1][id[i]] = (int) v;
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
