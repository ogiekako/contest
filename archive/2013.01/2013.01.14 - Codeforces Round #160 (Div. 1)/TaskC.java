package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskC {
    int T = 60;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long n = in.nextLong() + 1, t = in.nextLong();
        long[] count = solve(n, T);
        count[0]--;
        if (Long.bitCount(t) != 1) {
            out.println(0);
        } else {
            out.println(count[Long.numberOfTrailingZeros(t)]);
        }
    }

    private long[] solve(long N, int k) {// [1,N]
        if (k < 0) {
            long[] res = new long[T];
            return res;
        }
        if (N << 63 - k >= 0) return solve(N, k - 1);
        N ^= 1L << k;
        long[] res = calc(k);
        long[] a = solve(N, k - 1);
        for (int i = 0; i < T - 1; i++) res[i + 1] += a[i];
        res[0]++;
        return res;
    }

    long[][] memo = new long[T][];
    private long[] calc(int k) {// [1,1<<k )
        if (memo[k] != null) return memo[k];
        if (k == 0) return memo[k] = new long[T];
        if (k == 1) {
            memo[k] = new long[T];
            memo[k][0] = 1;
            return memo[k];
        }
        memo[k] = new long[T];
        long[] a = calc(k - 1);
        memo[k][0] = 1 + a[0];
        for (int i = 0; i < T - 1; i++) memo[k][i + 1] += a[i] + a[i + 1];
        return memo[k];
    }
}
