package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class EKO {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long M = in.nextLong();
        long[] as = in.nextLongArray(n);
        int lb = 0, ub = 1000000010;
        do {
            int mb = (lb + ub) / 2;
            long val = 0;
            for (int i = 0; i < n; i++) {
                if (as[i] > mb) {
                    val += as[i] - mb;
                }
            }
            if (val >= M) lb = mb;
            else ub = mb;
        } while (ub - lb > 1);
        out.println(lb);
    }
}
