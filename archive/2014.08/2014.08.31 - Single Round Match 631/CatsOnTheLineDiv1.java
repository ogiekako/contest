package src;

import java.util.Arrays;

public class CatsOnTheLineDiv1 {
    public int getNumber(int[] position, int[] count, int time) {
        int n = position.length;
        P[] ps = new P[n + 1];
        for (int i = 0; i < n; i++) {
            ps[i] = new P();
            ps[i].c = count[i];
            ps[i].p = position[i];
        }
        ps[n] = new P();
        ps[n].c = 0;
        ps[n].p = Integer.MIN_VALUE / 2;
        Arrays.sort(ps);
        n++;
        long[] dp = new long[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        long[][] right = new long[n][n];
        right[0][0] = ps[0].p;
        for (int i = 1; i < n; i++) {
            P p = ps[i];
            long[] nDp = new long[n + 1];
            Arrays.fill(nDp, Integer.MAX_VALUE);
            Arrays.fill(right[i], Integer.MAX_VALUE);
            right[i][i] = p.p + time;
            for (int j = 0; j < i; j++)
                if (dp[j] < Integer.MAX_VALUE) {
                    nDp[i] = Math.min(nDp[i], dp[j] + 1);
                    if (right[j][j] >= p.p - time) {
                        right[j][i] = right[j][j];
                        nDp[j] = Math.min(nDp[j], dp[j]);
                    } else {
                        long r = right[j][i - 1];
                        if (r >= Integer.MAX_VALUE) continue;
                        long a = p.p - time;
                        long b = r + 1;
                        long l = Math.max(a, b);
                        long r2 = l + p.c - 1;
                        if (r2 <= p.p + time) {
                            right[j][i] = r2;
                            nDp[j] = Math.min(nDp[j], dp[j]);
                        }
                    }
                }
            dp = nDp.clone();
        }
        long res = Integer.MAX_VALUE;
        for(int i=0;i<n;i++)res = Math.min(res,dp[i]);
        return (int) res;
    }

    class P implements Comparable<P> {
        long p, c;

        @Override
        public int compareTo(P o) {
            return Long.compare(p, o.p);
        }
    }
}
