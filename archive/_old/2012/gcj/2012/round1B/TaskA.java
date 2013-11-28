package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        out.printf("Case #%d:", testNumber);
        double[] xs = new double[n];
        for (int i = 0; i < n; i++) xs[i] = in.nextDouble();
        double sum = 0;
        for (double x : xs) sum += x;
        for (int i = 0; i < n; i++) {
            double lb = 0, ub = 1;
            for (int j = 0; j < 100; j++) {
                double md = (lb + ub) / 2;
                double myScore = xs[i] + sum * md;
                double Y = md;
                for (int k = 0; k < n; k++)
                    if (i != k) {
                        double y = Math.max((myScore - xs[k]) / sum, 0);
                        Y += y;
                    }
                if (Y <= 1) { // lose
                    lb = md;
                } else {
                    ub = md;
                }
            }
            out.printf(" %.12f", ub * 100);
        }
        out.println();
    }
}
