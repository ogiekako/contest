package on2017_09.on2017_09_14_Typical_DP_Contest.T_________;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.math.linearAlgebra.MintLinearTransform;
import net.ogiekako.algorithm.math.linearAlgebra.Polynomial;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class TaskT {
    int MOD = (int) (1e9+7);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        int K = in.nextInt(), N = in.nextInt();
        long[] p = new long[K + 1];
        Arrays.fill(p, 1);
        p[K] = MOD - 1;
        long[] mod = Polynomial.mod(N - 1, p, MOD);
        long res = 0;
        for (int i = 0; i < K; i++) {
            res += mod[i];
        }
        out.println(res % MOD);
    }
}
