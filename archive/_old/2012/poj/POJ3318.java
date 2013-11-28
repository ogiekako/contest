package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.misc.PropertyTesting;

import java.io.PrintWriter;

public class POJ3318 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long[][] A = in.nextLongArray(n, n);
        long[][] B = in.nextLongArray(n, n);
        long[][] C = in.nextLongArray(n, n);
        boolean res = PropertyTesting.isMatrixMultiplication(A, B, C);
        out.println(res ? "YES" : "NO");
    }
}
