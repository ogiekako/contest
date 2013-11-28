package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
// overflow
public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        long C = 0;
        int T = 0;
        long res = 0;
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            int c = in.nextInt();
            int d = t - T;
            C = Math.max(0, C - d) + c;
            res = Math.max(res, C);
            T = t;
        }
        out.printFormat("%d %d\n", T + C, res);
    }
}
