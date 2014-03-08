package src;

import java.util.Arrays;
import java.util.TreeSet;
public class PilingRectsDiv1 {
    public long getmax(int N, int[] XS, int[] YS, int XA, int XB, int XC, int YA, int YB, int YC) {
        long[] X = new long[N * 2];
        long[] Y = new long[N * 2];
        for (int i = 0; i < XS.length; i++) {
            X[i] = XS[i];
            Y[i] = YS[i];
        }
        for (int i = XS.length; i < 2 * N; i++) {
            X[i] = (X[i - 1] * XA + XB) % XC + 1;
            Y[i] = (Y[i - 1] * YA + YB) % YC + 1;
        }
        return solve(X, Y);
    }
    private long solve(long[] x, long[] y) {
        int n = x.length / 2;
        for (int i = 0; i < x.length; i++) {
            if (x[i] > y[i]) {
                long tmp = x[i]; x[i] = y[i]; y[i] = tmp;
            }
        }
        P[] ps = new P[x.length];
        for (int i = 0; i < x.length; i++) {
            ps[i] = new P(x[i], y[i]);
        }
        Arrays.sort(y);
        long atMost = y[n];
        Arrays.sort(x);
        Arrays.sort(ps);
        long[] ym1 = new long[x.length + 1];
        ym1[x.length] = Integer.MAX_VALUE;
        for (int i = x.length - 1; i >= 0; i--) {
            ym1[i] = Math.min(ym1[i + 1], ps[i].y);
        }
        long minY = Integer.MAX_VALUE;
        long res = 0;
        for (int i = 0; i < n + 1; i++) {
            long S1 = ps[i].x * ym1[i];
            minY = Math.min(minY, ps[i].y);
            long S2 = Math.min(atMost, minY) * ps[0].x;
            res = Math.max(res, S1 + S2);
        }
        TreeSet<Long> set = new TreeSet<Long>();
        for(int i=n*2-1;i>n;i--)set.add(ps[i].y);
        for(int i=n;i>=0;i--){
            set.add(ps[i].y);
            res = Math.max(res, ps[0].x * y[0] + ps[i].x * set.first());
            set.pollFirst();
        }
        return res;
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    class P implements Comparable<P> {
        long x, y;
        public P(long x, long y) {
            this.x = x; this.y = y;
        }
        @Override
        public int compareTo(P o) {
            if (x != o.x) return Long.compare(x, o.x);
            if (y != o.y) return Long.compare(y, o.y);
            return this == o ? 0 : 1;
        }
    }
}
