package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskF {
    long INF = (long) 1e18;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), s = in.nextInt() - 1;
        long[] l = new long[n], r = new long[n];
        long[][] w = new long[n][n];
        for (int i = 0; i < n; i++) {
            l[i] = in.nextLong(); r[i] = in.nextLong();
        }
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) w[i][j] = in.nextLong();
        for(int k=0;k<n;k++)for(int i=0;i<n;i++)for(int j=0;j<n;j++)w[i][j] = Math.min(w[i][j], w[i][k]+w[k][j]);
        long[] tmp = new long[n];
        for (int i = 0; i < n; i++) {
            tmp[i] = r[i] << 32 | i;
        }
        Arrays.sort(tmp);
        int[] order = new int[n];
        for (int i = 0; i < n; i++) order[i] = (int) (tmp[i] & Integer.MAX_VALUE);
        long[] dp = new long[n];
        Arrays.fill(dp, -INF);
        for (int i = 0; i < n; i++) {
            long ar = w[s][order[i]];
            long time = r[order[i]] - Math.max(ar, l[order[i]]);
            if (time >= 0) {
                dp[i] = time;
            }
        }
        for (int i = 0; i < n; i++)
            if (dp[i] >= 0) {
                int from = order[i];
                for (int j = i; j < n; j++) {
                    int to = order[j];
                    long ar = r[from] + w[from][to];
                    long time = r[to] - Math.max(ar, l[to]);
                    if (time >= 0) {
                        dp[j] = Math.max(dp[j], dp[i] + time);
                    }
                }
            }
        long res = 0;
        for (int i = 0; i < n; i++) res = Math.max(res, dp[i]);
        out.println(res);
    }
}
