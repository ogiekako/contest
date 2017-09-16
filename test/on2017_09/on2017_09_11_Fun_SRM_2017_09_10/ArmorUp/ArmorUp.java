package on2017_09.on2017_09_11_Fun_SRM_2017_09_10.ArmorUp;



import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.util.Arrays;

public class ArmorUp {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double minimalDamage(final int maxHP, final int currentHP, final int k) {
        double l = 0, r = maxHP;
        for (int i = 0; i < 50; i++) {
            double D = (l + r) / 2;

            double[][] A = new double[][]{
                    {1 - D / (2.0 * maxHP), 1},
                    {0, 1}
            };
            A = Matrix.powered(A, k);

            double result = A[0][0] * currentHP - A[0][1] * D / 2;
            if (result < 0 || Math.abs(result) < 1e-9) {
                r = D;
            } else {
                l = D;
            }
        }
        return r;
    }
}
