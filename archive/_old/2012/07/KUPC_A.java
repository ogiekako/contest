package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KUPC_A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int T = in.nextInt();
        int E = in.nextInt();
        for (int i = 0; i < N; i++) {
            int x = in.nextInt();
            int s = 0;
            while (s < 3000) {
                if (Math.abs(s - T) <= E) {
                    out.println(i + 1); return;
                }
                s += x;
            }
        }
        out.println(-1);
    }
}
