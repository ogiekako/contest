package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Tenka_A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long N = in.nextLong();
        long[] F = new long[60];
        int i;
        for (i = 0; i < 60; i++) {
            F[i] = i < 2 ? 1 : F[i - 1] + F[i - 2];
            if (F[i] > N) break;
        }
        int res = 0;
        while (i >= 0) {
            if (F[i] <= N) {
                res++;
                N -= F[i];
            }
            i--;
        }
        out.println(res);
    }
}
