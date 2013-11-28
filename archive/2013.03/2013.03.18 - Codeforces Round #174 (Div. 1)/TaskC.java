package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC {
    int MOD = 1000000007;

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int Q = in.nextInt();
        int t = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        int[] is = new int[n];
        int[] si = new int[n];
        Arrays.fill(is, -1);
        Arrays.fill(si, -1);
        for (int i = 0; i < Q; i++) {
            int b = in.nextInt() - 1, c = in.nextInt() - 1;
            si[b] = c;
            is[c] = b;
        }
        boolean[] nLast = new boolean[n];
        for (; ; ) {
            boolean ch = false;
            for (int i = 0; i < n; i++)
                if (is[i] == -1 && si[i] >= 0) {
                    a[si[i]] += a[i];
                    is[si[i]] = -1;
                    nLast[i] = true;
                    si[i] = -1;
                    ch = true;
                }
            if (!ch) break;
        }
        for (int i = 0; i < n; i++)
            if (is[i] != -1) {
                out.println(0); return;
            }
        long[] dp = new long[t + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            if (nLast[i]) {
                for (int j = t - a[i]; j >= 0; j--) dp[j + a[i]] = dp[j];
                for (int j = 0; j < a[i] && j <= t; j++) dp[j] = 0;
            }
            for (int j = 0; j <= t - a[i]; j++) {
                dp[j + a[i]] += dp[j];
                if (dp[j + a[i]] >= MOD) dp[j + a[i]] -= MOD;
            }
        }
        out.println(dp[t]);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
