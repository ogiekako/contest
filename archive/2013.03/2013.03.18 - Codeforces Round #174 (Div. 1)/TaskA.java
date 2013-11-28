package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int Q = in.nextInt();
        long[] A = new long[Q + 10];
        long[] B = new long[Q + 10];
        long sum = 0;
        int n = 1;
        while (Q-- > 0) {
            int t = in.nextInt();
            if (t == 1) {
                int a = in.nextInt();
                long x = in.nextInt();
                B[a] += x;
                sum += a * x;
            } else if (t == 2) {
                int x = in.nextInt();
                A[n++] = x;
                sum += x;
            } else {
                A[n - 1] += B[n];
                B[n - 1] += B[n];
                B[n] = 0;
                sum -= A[n - 1];
                n--;
            }
            out.printFormat("%.9f\n", (double) sum / n);
        }
    }
}
