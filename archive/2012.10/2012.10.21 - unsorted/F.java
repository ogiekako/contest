package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class F {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        double[] ds = new double[N];
        boolean positive = true;
        for (int i = 0; i < N; i++) {
            ds[i] = in.nextDouble();
            if (ds[i] < 0) positive ^= true;
        }
        boolean[] lower = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (positive ^ Math.abs(ds[i]) > 1) lower[i] = true;
        }
        lower[0] = false; lower[1] = true;
        int lc = 0;
        for (boolean l : lower) if (l) lc++;
        int large = N - lc;
        int small = 1;
        for (int i = 1; i < N; i++) {
            if (lower[i]) out.println(large++);
            else out.println(small++);
        }
    }
}
