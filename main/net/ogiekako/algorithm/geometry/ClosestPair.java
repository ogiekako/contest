package net.ogiekako.algorithm.geometry;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Math.abs;
import static java.util.Arrays.sort;
import static java.util.Collections.sort;

public class ClosestPair {
    //

    class P {
        int x, y;
        public P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        long dist2(P p) {
            return p2(x - p.x) + p2(y - p.y);
        }

        private long p2(int i) {
            return i * i;
        }
    }
    /**
     * O(n log n)
     * 定数倍が結構大きい.
     * Codeforces120J で,n<10^5 で2s以内.
     *
     * @param ps points
     * @return the closest pair
     */
    public P[] closestPair(P[] ps) {
        sort(ps, new Comparator<P>() {
            public int compare(P o1, P o2) {
                return Double.compare(o1.x, o2.x);
            }
        });
        return closestPair(ps, 0, ps.length, new Comparator<P>() {
            public int compare(P o1, P o2) {
                return Double.compare(o1.y, o2.y);
            }
        });
    }

    private P[] closestPair(P[] ps, int from, int to, Comparator<P> compY) {
        int n = to - from;
        if (n < 100) {
            P[] best = null;
            for (int i = 0; i < n; i++) for (int j = 0; j < i; j++) best = best(best, mp(ps[i], ps[j]));
            return best;
        }
        int mid = from + n / 2;

        P[] resL = closestPair(ps, from, mid, compY);
        P[] resR = closestPair(ps, mid, to, compY);

        P[] best = best(resL, resR);
        long minDist = dist(best);

        P midPoint = ps[mid];
        ArrayList<P> list = new ArrayList<P>();
        for (P p : ps) if (abs(p.x - midPoint.x) < minDist) list.add(p);
        sort(list, compY);
        for (int i = 0; i < list.size(); i++) {
            int j = i + 1;
            while (j < list.size() && list.get(j).y - list.get(i).y < minDist) {
                long d = list.get(j).dist2(list.get(i));
                if (minDist > d) {
                    minDist = d;
                    best = mp(list.get(i), list.get(j));
                }
                j++;
            }
        }
        return best;
    }

    private P[] best(P[] as, P[] bs) {
        if (as == null) return bs;
        if (bs == null) return as;
        double a = dist(as);
        double b = dist(bs);
        return a > b ? bs : as;
    }

    private long dist(P[] ps) {
        return ps[0].dist2(ps[1]);
    }
    private P[] mp(P i, P j) {
        return new P[]{i, j};
    }

    //
}
