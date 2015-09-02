package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Line_methods {
    private Line_methods() {
    }

    // TODO: Add descr.
    public static boolean crsSS(Point p1, Point p2, Point q1, Point q2) {
        if (Math.max(p1.x, p2.x) + EPS.value() < Math.min(q1.x, q2.x)) return false;
        if (Math.max(q1.x, q2.x) + EPS.value() < Math.min(p1.x, p2.x)) return false;
        if (Math.max(p1.y, p2.y) + EPS.value() < Math.min(q1.y, q2.y)) return false;
        if (Math.max(q1.y, q2.y) + EPS.value() < Math.min(p1.y, p2.y)) return false;
        return sgn(p2.sub(p1).det(q1.sub(p1))) * sgn(p2.sub(p1).det(q2.sub(p1))) <= 0 &&
                sgn(q2.sub(q1).det(p1.sub(q1))) * sgn(q2.sub(q1).det(p2.sub(q1))) <= 0;
    }

    private static int sgn(double d) {
        return d < -EPS.value() ? -1 : d > EPS.value() ? 1 : 0;
    }

    /**
     * 点と線分の距離
     *
     * @param p1
     * @param p2
     * @param q
     * @return
     */
    public static double disSP(Point p1, Point p2, Point q) {// AOJ1265
        if (p2.sub(p1).dot(q.sub(p1)) < 0) return q.sub(p1).norm();
        if (p1.sub(p2).dot(q.sub(p2)) < 0) return q.sub(p2).norm();
        // AOJ1265
        return new Line(p1, p2).distance(q);
    }

    int ccw(Point a, Point b, Point c) {
        b = b.sub(a);
        c = c.sub(a);
        if (b.det(c) > EPS.value()) return 1;
        if (b.det(c) < -EPS.value()) return -1;
        if (b.dot(c) < -EPS.value()) return 2;
        if (b.norm() < c.norm()) return -2;
        return 0;
    }

    /**
     * p1,p2が交差するかを返す.
     *
     * @param p1
     * @param p2
     * @param r1
     * @param r2
     * @return
     */
    boolean crossSS2(Point p1, Point p2, Point r1, Point r2) {
        return ccw(p1, p2, r1) * ccw(p1, p2, r2) <= 0
                && ccw(r1, r2, p1) * ccw(r1, r2, p2) <= 0;
    }

    Point foot(Point p1, Point p2, Point r) {// aoj0081
        double d = p1.dist(p2);
        return r.add(p2.sub(p1).rot90().mul(r.sub(p1).det(p2.sub(p1)) / d / d));
    }

    Point mirrored(Point p1, Point p2, Point q) {// aoj1299
        Point p = foot(p1, p2, q);
        return q.add(p.sub(q).mul(2));
    }

    /**
     * p1,p2を結ぶ直線の上に,(x,res)がある.
     *
     * @param p1
     * @param p2
     * @param x
     * @return
     */
    double isLY(Point p1, Point p2, int x) {// AOJ1265
        double y = p1.y + (x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x);
        return y;
    }

    /**
     * 線分と線分の距離
     *
     * @param p1
     * @param p2
     * @param q1
     * @param q2
     * @return
     */
    double disSS(Point p1, Point p2, Point q1, Point q2) {// AOJ1265
        if (crsSS(p1, p2, q1, q2)) return 0;
        double res = Double.POSITIVE_INFINITY;
        res = Math.min(res, disSP(p1, p2, q1));
        res = Math.min(res, disSP(p1, p2, q2));
        res = Math.min(res, disSP(q1, q2, p1));
        res = Math.min(res, disSP(q1, q2, p2));
        return res;
    }

    /**
     * ls[i][0] -> ls[i][1] の左側半平面の共通部分を表すエンベロープ
     * をなすid を,左回りにエンベロープをなすような順序で返す. 共通部分が無限領域とならない場合は,nullを返す.
     * 平行な直線は存在してはいけない.
     * O(n log n).  O(n log n) かかるのは最初のソート部分で,それ以外はO(n).
     * <p/>
     * verified: old.GCJ 2010 round2 D
     */
    public int[] getEnvelopeIds(Point[][] ls) {
        int n = ls.length;
        final double[] args = new double[n];
        for (int i = 0; i < n; i++) {
            args[i] = ls[i][0].argTo(ls[i][1]);
        }
        Integer[] Id = new Integer[n];
        for (int i = 0; i < n; i++) Id[i] = i;
        Arrays.sort(Id, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Double.compare(args[o1], args[o2]);
            }
        });
        boolean ok = false;
        int from = 0;
        if (args[Id[0]] - args[Id[n - 1]] + 2 * Math.PI > Math.PI) {
            ok = true;
        } else {
            for (int i = 1; i < n; i++) {
                if (args[Id[i]] - args[Id[i - 1]] > Math.PI) {
                    from = i;
                    ok = true;
                    break;
                }
            }
        }
        if (!ok) return null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(Id[from]);
        for (int i = 1; i < n; i++) {
            int i2 = Id[(from + i) % n];
            int j = list.get(list.size() - 1);
            Point is = new Line(ls[i2][0], ls[i2][1]).intersection(new Line(ls[j][0], ls[j][1]));
            Point dir = ls[i2][1].sub(ls[i2][0]);
            for (; ; ) {
                if (list.size() == 1) break;
                j = list.get(list.size() - 2);
                Point is2 = new Line(ls[i2][0], ls[i2][1]).intersection(new Line(ls[j][0], ls[j][1]));
                if (is2.sub(is).dot(dir) > 0) {
                    list.remove(list.size() - 1);
                    is = is2;
                } else {
                    break;
                }
            }
            list.add(i2);
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }


}
