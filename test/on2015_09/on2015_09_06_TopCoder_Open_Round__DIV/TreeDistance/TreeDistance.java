package on2015_09.on2015_09_06_TopCoder_Open_Round__DIV.TreeDistance;



import net.ogiekako.algorithm.MOD;
import net.ogiekako.algorithm.math.algebra.Mint;
import net.ogiekako.algorithm.math.linearAlgebra.LinearSystem;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.math.linearAlgebra.Polynomial;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class TreeDistance {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    static {
        MOD.set((int) (1e9 + 7));
    }

    public int countTrees(int[] p, int K) {
        int n = p.length + 1;
        boolean[][] tree = new boolean[n][n];
        for (int i = 0; i < p.length; i++) {
            tree[i + 1][p[i]] = tree[p[i]][i + 1] = true;
        }
        Mint[] y = new Mint[n + 1];
        for (int x = 0; x < n + 1; x++) {
            long[][] G = new long[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;
                    if (!tree[i][j]) {
                        G[i][j] = 1;
                    } else {
                        G[i][j] = x;
                    }
                }
            }
            y[x] = LinearSystem.numberOfSpanningTrees(G);
        }
        Polynomial P = LinearSystem.interpolate(y);
        Mint res = Mint.ZERO;
        for (int i = 0; i < n && i <= K; i++) {
            res = res.add(P.getCoefficient(n - 1 - i));
        }
        return res.get();
    }

}
