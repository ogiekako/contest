package net.ogiekako.algorithm.misc;

import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.util.Arrays;
import java.util.Random;

public class PropertyTesting {
    /**
     * A*B == C であるかを
     * 乱択で判定.
     * POJ3318.Matrix Multiplication.
     * <p/>
     * O(n^2)
     */
    public static boolean isMatrixMultiplication(long[][] A, long[][] B, long[][] C) {
        Random rnd = new Random();
        int m = B[0].length;
        boolean yes = true;
        for (int r = 0; r < 3 && yes; r++) {
            long[] v = new long[m];
            for (int i = 0; i < m; i++) {
                v[i] = rnd.nextInt(100);
            }
            long[] ABv = Matrix.mul(A, Matrix.mul(B, v));
            long[] Cv = Matrix.mul(C, v);
            yes = Arrays.equals(ABv, Cv);
        }
        return yes;
    }
}
