package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    long[] D;
    long[][] last;
    long[][] first;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int t = in.nextInt(), n = in.nextInt();
        n = Math.min(n, 80);
        D = new long[n + 1];
        first = new long[n + 1][3];
        last = new long[n + 1][3];
        for (int i = 0; i <= n; i++) D[i] = i <= 1 ? i + 1 : D[i - 1] + D[i - 2];
        for (int i = 0; i < t; i++) {
            out.println(solve(in.nextLong(), in.nextLong(), n));
        }
    }

    private long solve(long x, long y, int n) {
        if (x > y) return solve(y, x, n);
        for (int i = 0; i < n + 1; i++) Arrays.fill(first[i], -1);
        for (int i = 0; i < n + 1; i++) Arrays.fill(last[i], -1);
        return dfs(x, y, n);
    }

    private long dfs(long x, long y, int n) {
        if (n == 0) return 0;
        if (n == 1) return x == y ? 0 : 1;
        long f = D[n - 1];
        if (y <= f) {
            long d1 = dfs(x, y, n - 1);
            long d2 = first(0, x, n - 1) + last(1, y, n - 1) + 2;
            long d3 = last(0, x, n - 1) + first(1, y, n - 1) + 2;
            return Math.min(d1, Math.min(d2, d3));
        } else if (x > f) {
            return dfs(x - f, y - f, n - 2);
        } else {
            long d1 = first(1, y - f, n - 2) + first(0, x, n - 1) + 1;
            long d2 = first(1, y - f, n - 2) + last(0, x, n - 1) + 1;
            return Math.min(d1, d2);
        }
    }

    private long last(int type, long x, int n) {
        if (n == 0) return 0;
        if (n == 1) return x == 2 ? 0 : 1;
        if (last[n][type] >= 0) return last[n][type];
        long f = D[n - 1];
        long res;
        if (x <= f) {
            long d1 = first(type, x, n - 1) + last(2, 1, n - 2) + 1;
            long d2 = last(type, x, n - 1) + last(2, 1, n - 2) + 1;
            res = Math.min(d1, d2);
        } else {
            res = last(type, x - f, n - 2);
        }
        return last[n][type] = res;
    }

    private long first(int type, long x, int n) {
        if (n == 0) return 0;
        if (n == 1) return x == 1 ? 0 : 1;
        if (first[n][type] >= 0) return first[n][type];
        long f = D[n - 1];
        long res;
        if (x <= f) {
            long d1 = first(type, x, n - 1);
            long d2 = last(type, x, n - 1) + 2;
            res = Math.min(d1, d2);
        } else {
            res = first(type, x - f, n - 2) + 1;
        }
        return first[n][type] = res;
    }
}
