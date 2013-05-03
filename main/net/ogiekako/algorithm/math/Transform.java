package net.ogiekako.algorithm.math;

public class Transform {
    /*
    n = x.length は2のべき乗でないといけない．
    逆変換は，transformした後，n で割る．

    x $ y = f^{-1} (f(x) * f(y))
    (x $ y)_k = \sum_j ( x_j * y_{j^k} ) .
    SRM 518 Nim

    O(n log n).
     */
    public static int[] hadamardTransform(int[] x, int MOD) {
        if(Integer.bitCount(x.length)!=1)throw new IllegalArgumentException("x.length must be a power of 2.");
        int[] z = new int[x.length];
        hadamardTransform(x, z, 0, x.length, MOD);
        return z;
    }

    public static void hadamardTransform(int[] x, int[] z, int from, int to, int MOD) {
        int d = (to - from) / 2;
        if (d == 0) {
            z[from] = x[from]; return;
        }
        hadamardTransform(x, z, from, from + d, MOD);
        hadamardTransform(x, z, from + d, to, MOD);
        for (int i = 0; i < d; i++) {
            z[from + i] += z[from + d + i];
            if (z[from + i] >= MOD) z[from + i] -= MOD;
        }
        for (int i = 0; i < d; i++) {
            z[from + d + i] = z[from + i] - 2 * z[from + d + i];
            if (z[from + d + i] < 0) z[from + d + i] += MOD;
            if (z[from + d + i] < 0) z[from + d + i] += MOD;
        }
    }
}
