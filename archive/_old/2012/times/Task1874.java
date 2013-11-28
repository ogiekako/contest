package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Algorithm;

import java.io.PrintWriter;

public class Task1874 {
    public void solve(int testNumber, MyScanner in, final PrintWriter out) {
        final double a = in.nextDouble();
        final double b = in.nextDouble();

        Algorithm.Function outer = new Algorithm.Function() {
            public double func(final double A) {
                Algorithm.Function inner = new Algorithm.Function() {
                    public double func(double B) {
                        return a * b * Math.sin(A) * Math.sin(B) + 0.5 * a * a * Math.sin(A) * Math.cos(A) + 0.5 * b * b * Math.sin(B) * Math.cos(B);
                    }
                };
                double B = Algorithm.goldenSearch(0, Math.PI, inner);
                return inner.func(B);
            }
        };
        double A = Algorithm.goldenSearch(0, Math.PI, outer);
        double res = outer.func(A);
        out.printf("%.9f\n", res);
    }

}
