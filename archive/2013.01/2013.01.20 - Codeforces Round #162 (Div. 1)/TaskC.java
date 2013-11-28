package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC {
    int T = (int) 1e5 + 1;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int q = in.nextInt();
        int[] v = new int[n];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) v[i] = in.nextInt();
        for (int i = 0; i < n; i++) c[i] = in.nextInt();
        long[] g = new long[T];
        for (int i = 0; i < q; i++) {
            Arrays.fill(g, Long.MIN_VALUE / 2);
            long a = in.nextInt(), b = in.nextInt();
            long best1 = 0, col1 = -1;
            long best2 = 0;
            for (int j = 0; j < n; j++) {
                long cur = g[c[j]] + v[j] * a;
                if (col1 != c[j]) {
                    cur = Math.max(cur, best1 + v[j] * b);
                    if (best1 < cur) {
                        best2 = best1;
                        best1 = cur;
                        col1 = c[j];
                    } else if (best2 < cur) {
                        best2 = cur;
                    }
                } else {
                    cur = Math.max(cur, best2 + v[j] * b);
                    if (cur > best1) {
                        best1 = cur;
                    }
                }
                g[c[j]] = Math.max(g[c[j]], cur);
            }
            out.println(best1);
        }
    }
}
