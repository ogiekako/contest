package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class E {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[] A = new long[N];
        long max = 0;
        for (int i = 0; i < N; i++) {
            A[i] = Math.abs(in.nextLong()) + Math.abs(in.nextLong());
            if (A[i] % 2 != A[0] % 2) {
                out.println(-1); return;
            }
            max = Math.max(max, A[i]);
        }
        long sum = 0;
        for (int x = 1; ; x++) {
            sum += x;
            if (sum >= max && sum % 2 == A[0] % 2) {
                out.println(x); return;
            }
        }
    }
}
