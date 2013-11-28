package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long l = in.nextLong(), r = in.nextLong();
        long res = f(r + 1) - f(l);
        out.println(res);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private long f(long l) {
        long res = 0;
        for (int i = 0; i < 10; i++) if (i < l) res++;
        long ten = 10;
        for (int d = 2; d <= 18; d++) {
            for (int i = 1; i < 10; i++) {
                long lb = ten * i + i;
                long ub = Math.min(l, ten * (i + 1) + i);
                if (ub > lb) debug(lb, ub);
                res += Math.max(0, (ub - lb) / 10);
            }
            ten *= 10;
        }
        return res;
    }
}
