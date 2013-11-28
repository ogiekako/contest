package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class DNA {
    final int INF = 268435456;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        String s = in.next();
        int A = 0;
        int B = INF;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == 'A') {
                int nA = Math.min(A, B + 1);
                int nB = B + 1;
                A = nA;
                B = nB;
            } else {
                int nB = Math.min(B, A + 1);
                int nA = A + 1;
                A = nA;
                B = nB;
            }
        }
        out.println(Math.min(A, B));
    }
}
