package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.io.PrintWriter;

public class MyFairCoins {
    // a_i : first should always face heads.
    // b_i : first not always face heads.

    // a_0 = 1, a_1 = 1.
    // a_{i+2} = b_{i+1} + b_i.
    // b_0 = 1, b_1 = 2
    // b_{i+2} = 2 * b_{i+1} + 2 * b_i.
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int MOD = (int) (1e9 + 7);
        long[][] A = new long[][]{{2, 2}, {1, 0}};
        long b = Matrix.powered(A, N, MOD)[0][0];
        b *= MathUtils.inverse(2, MOD);
        out.println(b % MOD);
    }
}
