package src;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskD {
    int T = (int) (1e6 + 10);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        P[] ps = new P[n];
        P[] rev = new P[n];
        for (int i = 0; i < n; i++) {
            ps[i] = P.of(in.nextInt() + T, in.nextInt() + T);
            rev[i] = P.of(ps[i].y, ps[i].x);
        }
        double a = solve(ps);
        double b = solve(rev);
        out.println((a + b) / 2);
    }

    int n;

    private double solve(P[] ps) {
        n = ps.length;
        for (int i = 0; i < n; i++) {
            long det = ps[next(i)].sub(ps[i]).det(ps[next(next(i))].sub(ps[i]));
            if (det < 0) {
                ArrayUtils.reverse(ps);
                break;
            }
        }
        int lower = 0;
        for (int i = 0; i < n; i++) if (ps[i].compareTo(ps[lower]) < 0) lower = i;
        long[] count = new long[T * 2];
        int upper = lower;
        if (ps[prev(lower)].x == ps[lower].x) {
            upper = prev(lower);
        }
        while (ps[upper].x < ps[prev(upper)].x) {
            long x1 = ps[upper].x;
            long y1 = ps[upper].y;
            long x2 = ps[prev(upper)].x;
            long y2 = ps[prev(upper)].y;
            for (long x = x1; x < x2; x++) {
                count[((int) x)] += ((x2 - x) * y1 + (x - x1) * y2) / (x2 - x1);
            }
            upper = prev(upper);
        }
        while (ps[lower].x < ps[next(lower)].x) {
            long x1 = ps[lower].x;
            long y1 = ps[lower].y;
            long x2 = ps[next(lower)].x;
            long y2 = ps[next(lower)].y;
            for (long x = x1; x < x2; x++) {
                count[((int) x)] -= ((x2 - x) * y1 + (x - x1) * y2 - 1) / (x2 - x1);
            }
            lower = next(lower);
        }

        count[((int) ps[upper].x)] += ps[upper].y;
        count[((int) ps[lower].x)] -= ps[lower].y - 1;

        for (int x = 0; x < count.length; x++)
            if (count[x] > 0) {
                debug("A", x, count[x]);
            }

        double S = 0;
        double S2 = 0;
        double N = 0;
        for (int x = 0; x < count.length; x++) {
            N += count[x];
            S += x * count[x];
            S2 += (double) x * x * count[x];
        }
        debug(S);
        debug(S2);
        double sum = 2 * N * S2 - 2 * S * S;
        debug("sum", sum);
        return sum / ((double) N * (N - 1));
    }

    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    private int prev(int p) {
        p--;
        if (p < 0) p += n;
        return p;
    }

    private int next(int p) {
        p++;
        if (p >= n) p -= n;
        return p;
    }
}

class P implements Comparable<P> {
    final long x, y;

    public P(long x, long y) {
        this.x = x; this.y = y;
    }

    static P of(long x, long y) {
        return new P(x, y);
    }

    public P sub(P p) {
        return of(x - p.x, y - p.y);
    }

    public long det(P p) {
        return x * p.y - y * p.x;
    }

    public int compareTo(P o) {
        return x == o.x ? compare(y, o.y) : compare(x, o.x);
    }

    private int compare(long a, long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }
}