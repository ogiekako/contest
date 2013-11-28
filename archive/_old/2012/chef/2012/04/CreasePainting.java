package tmp;

import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.dataStructure.interval.IntervalSet;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class CreasePainting {
    String DIR = "DRUL";
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[][] X = new long[N + 1][2], Y = new long[N + 1][2];
        for (int i = 0; i < N; i++) {
            long x = X[i][1], y = Y[i][1];
            int dir = DIR.indexOf(in.nextChar());
            int dist = in.nextInt();
            X[i + 1][0] = x + dx[dir];
            Y[i + 1][0] = y + dy[dir];
            x += dx[dir] * dist;
            y += dy[dir] * dist;
            X[i + 1][1] = x;
            Y[i + 1][1] = y;
            if (dy[dir] == 0) {
                Interval me = make(X[i + 1]);
                IntervalSet set = new IntervalSet();
                for (int j = 0; j <= i; j++) {
                    Interval o = get(X[j], Y[j], me, y);
                    Interval is = me.intersection(o);
                    set.add(is);
                }
                long res = me.length() - set.cardinality();
                out.println(res);
            } else {
                Interval me = make(Y[i + 1]);
                IntervalSet set = new IntervalSet();
                for (int j = 0; j <= i; j++) {
                    Interval o = get(Y[j], X[j], me, x);
                    Interval is = me.intersection(o);
                    set.add(is);
                }
                long res = me.length() - set.cardinality();
                out.println(res);
            }
        }
    }

    private Interval get(long[] X, long[] Y, Interval xInterval, long y) {
        if (Y[0] == Y[1]) {
            if (Y[0] == y) {
                return xInterval.intersection(make(X));
            }
            return Interval.getEmptyInterval();
        }
        Interval yi = make(Y);
        if (yi.contains(y)) {
            return xInterval.intersection(new Interval(X[0], X[0] + 1));
        }
        return Interval.getEmptyInterval();
    }

    private Interval make(long[] X) {
        long left = Math.min(X[0], X[1]);
        long right = Math.max(X[0], X[1]) + 1;
        return new Interval(left, right);
    }
}
