package tmp;

import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler093 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int[] res = null;
        int mx = 0;
        for (int aa = 1; aa <= 9; aa++)
            for (int bb = aa + 1; bb <= 9; bb++)
                for (int cc = bb + 1; cc <= 9; cc++)
                    for (int dd = cc + 1; dd <= 9; dd++) {
                        boolean[] bs = new boolean[9 * 8 * 7 * 6 + 1];
                        int[] is = new int[]{aa, bb, cc, dd};
                        do {
                            int a = is[0], b = is[1], c = is[2], d = is[3];
                            for (int d1 = 0; d1 <= 3; d1++)
                                for (int d2 = 0; d2 <= 3; d2++)
                                    for (int d3 = 0; d3 <= 3; d3++) {
                                        double[] val = new double[5];
                                        val[0] = f(f(f(a, b, d1), c, d2), d, d3);
                                        val[1] = f(f(a, b, d1), f(c, d, d2), d3);
                                        val[2] = f(f(a, f(b, c, d1), d2), d, d3);
                                        val[3] = f(a, f(f(b, c, d1), d, d3), d2);
                                        val[4] = f(a, f(b, f(c, d, d3), d2), d1);
                                        for (double v : val) {
                                            int iv = (int) Math.round(v);
                                            if (Math.abs(iv - v) < 1e-9 && v >= 0) {
                                                bs[iv] = true;
                                            }
                                        }
                                    }
                        } while (Permutation.nextPermutation(is));
                        int n = 1;
                        while (bs[n]) n++;
                        if (n > mx) {
                            mx = n;
                            res = is;
                        }
                    }
        System.err.println(mx);
        for (int i : res) out.print(i);
        out.println();
    }

    private double f(double a, double b, int d) {
        if (d == 0) return a + b;
        if (d == 1) return a - b;
        if (d == 2) return a * b;
        return a / b;
    }
}
