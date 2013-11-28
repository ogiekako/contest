package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskA {
    String S = "RPS";
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int A = 0, B = 0;
        int N = in.nextInt();
        String a = in.next(), b = in.next();
        for (int i = 0; i < N; i++) {
            int ai = i % a.length();
            int bi = i % b.length();
            if (i > 0 && ai == 0 && bi == 0) {
                int k = N / i;
                A = A * k;
                B = B * k;
                i = i * k;
            }
            if (i == N) break;

            if (a.charAt(ai) == b.charAt(bi)) ;
            else if (S.indexOf(a.charAt(ai)) == (S.indexOf(b.charAt(bi)) + 1) % 3) {
                B++;
            } else {
                A++;
            }
        }
        out.println(A + " " + B);
    }
}