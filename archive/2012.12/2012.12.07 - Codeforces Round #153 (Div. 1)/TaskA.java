package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), d = in.nextInt();
        long[] x = new long[n];
        for (int i = 0; i < n; i++) x[i] = in.nextInt();
        long res = 0;
        for (int i = 0, j = 0; i < n; i++) {
            while (j < n && x[i] - x[j] > d) j++;
            res += (long) (i - j) * (i - j - 1) / 2;
        }
        out.println(res);
    }
}
