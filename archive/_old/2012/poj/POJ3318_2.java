package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;

public class POJ3318_2 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int nn = Integer.highestOneBit(n);
        if (nn < n) nn <<= 1;
        int[][] A = read(nn, n, in);
        int[][] B = read(nn, n, in);
        int[][] C = read(nn, n, in);

        int[][] exp = Matrix.strassen(A, B);
        boolean res = ArrayUtils.equals(exp, C);
        out.println(res ? "YES" : "NO");
    }

    private int[][] read(int nn, int n, MyScanner in) {
        int[][] res = new int[nn][nn];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) res[i][j] = in.nextInt();
        return res;
    }
}
