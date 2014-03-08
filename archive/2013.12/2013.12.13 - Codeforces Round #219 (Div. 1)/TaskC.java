package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt(), d = in.nextInt();
        int[] a = new int[m];
        int[] t = new int[m];
        long res = 0;
        for (int i = 0; i < m; i++) {
            a[i] = in.nextInt() - 1;
            int baseHappiness = in.nextInt();
            res += baseHappiness;
            t[i] = in.nextInt();
        }

//        RMQ[] rmq = new RMQ[2];
//        for (int i = 0; i < 2; i++) rmq[i] = new RMQ(n);
        long[] dp = new long[n];
        for (int i = 0; i < m; i++) {
//            int cur = i % 2, nxt = (i + 1) % 2;
            long tt = i==0 ? 0 : t[i] - t[i-1];
            int[] as = new int[n], bs = new int[n];
            for (int j = 0; j < n; j++) {
//                int w = Math.abs(j - a[i]);
                long iv = i == 0 ? 0 : tt * d;
                as[j] = (int) Math.max(0,j-iv);
                bs[j] = (int) Math.min(j+iv+1,n);
//                int val = w + rmq[cur].query((int) Math.max(0, j - iv), (int) Math.min(j + iv + 1, n));
//                rmq[nxt].update(j,val);
            }
            dp  = slidingRMQ(dp,as,bs);
            for(int j=0;j<n;j++){
                dp[j] += Math.abs(j-a[i]);
            }
        }
        long min = Long.MAX_VALUE;
        for(long dd:dp)min=Math.min(min,dd);
//        long min = r[m % 2].query(0, n);
        out.println(res - min);
    }
    public static long[] slidingRMQ(long[] is, int[] as, int[] bs) {
        if (as.length != bs.length) throw new RuntimeException();
        for (int i = 0; i < as.length - 1; i++) if (as[i] > as[i + 1]) throw new RuntimeException();
        for (int i = 0; i < bs.length - 1; i++) if (bs[i] > bs[i + 1]) throw new RuntimeException();
        long[] res = new long[as.length];
        long[] que = new long[is.length];
        int head = 0, tail = 0;
        for (int i = 0, a = 0, b = 0; i < as.length; i++) {
            while (b < bs[i]) {
                while (head < tail && que[tail - 1] > is[b]) tail--;
                que[tail++] = is[b++];
            }
            while (a < as[i]) {
                if (head < tail && que[head] == is[a]) head++;
                a++;
            }
            res[i] = head == tail ? Long.MAX_VALUE : que[head];
        }
        return res;
    }

    class RMQ {
        private int[] dat;
        private int n;
        public RMQ(int _n) {
            n = 1;
            while (n < _n) n <<= 1;
            dat = new int[n + n];
        }

        public void update(int i, int a) {
            i += n - 1;
            dat[i] = a;
            while (i > 0) {
                i = (i - 1) >> 1;
                dat[i] = Math.min(dat[(i << 1) + 1], dat[(i << 1) + 2]);
            }
        }

        public int query(int l, int r) {
            return query(l, r, 0, 0, n);
        }

        private int query(int a, int b, int i, int l, int r) {
            if (r <= a || b <= l) return Integer.MAX_VALUE;
            if (a <= l && r <= b) return dat[i];
            int vl = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
            int vr = query(a, b, (i << 1) + 2, (l + r) >>> 1, r);
            return Math.min(vl, vr);
        }
    }
}
