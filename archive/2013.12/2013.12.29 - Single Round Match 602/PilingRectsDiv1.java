package src;

import net.ogiekako.algorithm.utils.Cast;

import java.util.Arrays;
import java.util.Random;
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
            long S2 = Math.min(atMost, minY) * ps[0].x;
            minY = Math.min(minY, ps[i].y);
            if (i > 0)
                res = Math.max(res, S1 + S2);
        }
        TreeSet<Long> set = new TreeSet<Long>();
        for (int i = n * 2 - 1; i > n; i--) set.add((ps[i].y << 32) + i);
        for (int i = n; i >= 0; i--) {
            set.add((ps[i].y << 32) + i);
            res = Math.max(res, ps[0].x * y[0] + ps[i].x * (set.first() >> 32));
            set.pollFirst();
        }
        return res;
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
            return 0;
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random(12124908L);
        int n = 10;
        int m = 0;
        for (int i = 0; i < 1 << n * 2; i++) {
            if (Integer.bitCount(i) == n) m++;
        }
        int[] bits = new int[m];
        m = 0;
        for (int i = 0; i < 1 << n * 2; i++) {
            if (Integer.bitCount(i) == n) bits[m++] = i;
        }
        for (int t = 0; t < 50; t++) {
            System.err.println("t " + t);
            int[] X = new int[n * 2];
            int[] Y = new int[n * 2];
            for (int i = 0; i < n * 2; i++) {
                X[i] = rnd.nextInt((int) 1e9);
                Y[i] = rnd.nextInt((int) 1e9);
            }
            for (int i = 0; i < n * 2; i++) {
                if (X[i] > Y[i]) {
                    int tmp = X[i]; X[i] = Y[i]; Y[i] = tmp;
                }
            }
            long exp = 0;
            for (int b : bits) {
                int a = (1 << n * 2) - 1 ^ b;
                exp = Math.max(exp, f(X, Y, a) + f(X, Y, b));
            }
            long res = new PilingRectsDiv1().solve(Cast.toLong(X), Cast.toLong(Y));
            if (exp != res) {
                debug(exp, res, X, Y);
                throw new AssertionError();
            }
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    private static long f(int[] x, int[] y, int a) {
        int minX = Integer.MAX_VALUE, minY = Integer.MIN_VALUE;
        for (int i = 0; i < x.length; i++) {
            if (a << 31 - i < 0) {
                minX = Math.min(minX, x[i]);
                minY = Math.min(minY, y[i]);
            }
        }
        return (long) minX * minY;
    }
}
