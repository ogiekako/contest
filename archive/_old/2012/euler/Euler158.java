package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class Euler158 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long res = 0;
        int f = in.nextInt(), t = in.nextInt();
        int MX = 26;
        long[][] C = MathUtils.genCombTable(MX);
        for (int n = f; n <= t; n++) {
            long val = 0;
            for (int A = 0; A < MX; A++)
                for (int B = A + 1; B < MX; B++) {
                    for (int k = 0; k < n; k++)
                        for (int l = 0; l < n; l++) {
                            int m = n - 2 - k - l;
                            if (m < 0) continue;
                            if (B - l - 1 >= 0) val += C[25 - B][k] * C[B - A - 1][l] * C[B - l - 1][m];
                        }
                }
            res = Math.max(res, val);
        }
        out.println(res);
    }
}
