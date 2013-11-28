package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class C {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += in.nextInt();
        }
        double res = sum / N * (N - 1);
        out.printf("%.12f\n", res);
    }
}
