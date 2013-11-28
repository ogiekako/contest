package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Resistance {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int M = in.nextInt();
        int f0 = 1, f1 = 1;
        for (int i = 2; i < 2 * N; i++) {
            int tmp = f1;
            f1 += f0;
            if (f1 >= M) f1 -= M;
            f0 = tmp;
        }
        out.printf("%d/%d\n", f0, f1);
    }
}
