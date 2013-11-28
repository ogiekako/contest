package tmp;

import net.ogiekako.algorithm.utils.IntegerUtils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Euler156 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int from = in.nextInt(), to = in.nextInt();
        long res = 0;
        for (int i = from; i <= to; i++) {
            res += s(i);
        }
        out.println(res);
    }

    private long s(int d) {
        int M = 1000000;
        long[] c = new long[M];
        long[] f = new long[M + 1];
        for (int i = 0; i < M; i++) {
            c[i] = IntegerUtils.digitCount(i, d);
            f[i + 1] = f[i] + c[i];
        }
        @SuppressWarnings("unchecked")
        HashMap<Long, Integer>[] map = new HashMap[7];
        for (int ci = 0; ci < 7; ci++) {
            map[ci] = new HashMap<Long, Integer>();
            for (int K2 = 0; K2 < M; K2++) {
                long val = f[K2] + (ci - 1) * K2;
                if (!map[ci].containsKey(val)) map[ci].put(val, 1);
                else map[ci].put(val, map[ci].get(val) + 1);
            }
        }
        long res = 0;
        for (int K1 = 0; K1 < M; K1++) {
            long left = K1 * (M - f[M]) - f[K1] * M - 1;
            int ci = (int) c[K1];
            if (map[ci].containsKey(left)) {
                for (int K2 = 0; K2 < M; K2++) {
                    if (-K2 + f[K2] + c[K1] * K2 == left) {
                        res += (long) K1 * M + K2 - 1;
                    }
                }
            }
        }
        return res;
    }
}
