package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class BLOKOVI {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] ms = in.nextIntArray(n);
        double M = ms[n - 1];
        double len = 1;
        for (int i = n - 2; i >= 1; i--) {
            double m = ms[i];

            double x1 = m / (M + m);
            double len1 = Math.max(2 - x1, len - x1);

            double x2 = -m / (M + m);
            double len2 = len - x2;

            M += m;
            len = Math.max(len1, len2);
        }
        out.printf("%.6f\n", len);
    }
}
