package on_2012.nim;


// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

public class Nim {
    int MOD = (int) (1e9 + 7);

    public int count(int K, int L) {
        int[] a = new int[65536];
        final int[] id = new int[a.length];
        boolean[] isPrime = MathUtils.generatePrimaryTable(L + 1);
        for (int i = 0; i <= L; i++) if (isPrime[i]) a[i] = 1;
        id[0] = 1;
        return new PowerOperation<int[]>() {
            @Override
            int[] associativeOperation(int[] a, int[] b) {
                return xorConvolution(a, b);
            }

            @Override
            int[] identity() {
                return id;
            }
        }.power(a, K)[0];
    }

    private int[] xorConvolution(int[] x, int[] y) {
        int[][] X = new int[17][65536], Y = new int[17][65536], Z = new int[17][65536];
        X[0] = x; Y[0] = y;
        xorConvolution(X, Y, Z, 0, 0, 65536);
        return Z[0];
    }

    private void xorConvolution(int[][] x, int[][] y, int[][] z, int h, int from, int to) {
        if (to - from == 1) {
            z[h][from] = mul(x[h][from], y[h][from]);
            return;
        }
        int d = (to - from) / 2;
        int mid = (from + to) / 2;
        int[] curX = x[h], nxtX = x[h + 1], curY = y[h], nxtY = y[h + 1];
        for (int i = 0; i < d; i++) nxtX[from + i] = curX[from + i] - curX[mid + i] + MOD;
        for (int i = 0; i < d; i++) nxtX[mid + i] = curX[from + i] + curX[mid + i];
        for (int i = 0; i < d; i++) nxtY[from + i] = curY[from + i] - curY[mid + i] + MOD;
        for (int i = 0; i < d; i++) nxtY[mid + i] = curY[from + i] + curY[mid + i];
        for (int i = from; i < to; i++) if (nxtX[i] >= MOD) nxtX[i] -= MOD;
        for (int i = from; i < to; i++) if (nxtY[i] >= MOD) nxtY[i] -= MOD;
        xorConvolution(x, y, z, h + 1, from, mid);
        xorConvolution(x, y, z, h + 1, mid, to);
        for (int i = 0; i < d; i++) z[h][from + i] = z[h + 1][from + i] + z[h + 1][mid + i];
        for (int i = 0; i < d; i++) z[h][mid + i] = -z[h + 1][from + i] + z[h + 1][mid + i] + MOD;
        for (int i = from; i < to; i++) z[h][i] = div2(z[h][i]);
    }

    private int div2(int i) {
        if (i >= MOD) i -= MOD;
        if ((i & 1) == 0) return i / 2;
        return (i + MOD) / 2;
    }

    private int mul(int a, int b) {
        return (int) ((long) a * b % MOD);
    }

    abstract class PowerOperation<T> {
        abstract T associativeOperation(T a, T b);

        abstract T identity();

        public T power(T original, long K) {
            T cur = identity();
            for (; ; ) {
                if ((K & 1) == 1) {
                    cur = associativeOperation(cur, original);
                }
                K >>= 1;
                if (K == 0) break;
                original = associativeOperation(original, original);
            }
            return cur;
        }
    }
}

