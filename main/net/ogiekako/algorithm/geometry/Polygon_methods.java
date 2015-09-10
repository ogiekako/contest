package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

import java.util.ArrayList;
import java.util.Arrays;

public class Polygon_methods {
    public static double eps = EPS.get();

    // p->r の左側を返す.
    Point[] convexCut(Point[] ps, Point p, Point r) {// AOJ1299
        int n = ps.length;
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            Point a = ps[i], b = ps[(i + 1) % n];
            if (ccw(p, r, a) != -1) list.add(a);
            if (ccw(p, r, a) * ccw(p, r, b) == -1) {
                Point ab = b.sub(a), pr = r.sub(p);
                list.add(a.add(ab.mul(pr.det(p.sub(a)) / pr.det(ab))));
            }
        }
        return list.toArray(new Point[list.size()]);
    }

    int ccw(Point a, Point b, Point c) {
        b = b.sub(a);
        c = c.sub(a);
        if (b.det(c) > eps) return 1;
        if (b.det(c) < eps) return -1;
        if (b.dot(c) < eps) return 2;
        if (b.norm() + eps < c.norm()) return -2;
        return 0;
    }

    public static double area(Point[] ps) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
        double S = 0;
        for (int i = 0; i < ps.length; i++)
            S += ps[i].det(ps[(i + 1) % ps.length]);
        return S / 2;
    }

    // verified: pku3348.
    // generate anti-clockwise convex hull.
    // le -> lt : include points on edge.
    static int dn;
    static Point[] ds;
    static int un;
    static Point[] us;

    public static Point[] convexHull(Point[] ps, boolean includePointsOnEdge) { // O(n logn).
        if (includePointsOnEdge) return convexHullIncludingPointOnEdge(ps);
        else return convexHullExcludingPointOnEdge(ps);
    }

    public static Point[] convexHullExcludingPointOnEdge(Point[] ps) { // O(n logn).
        ps = ps.clone();
        int n = ps.length;
        Arrays.sort(ps);
        ds = new Point[n];// ds[0] = us[un-1] = ps[0].
        us = new Point[n];// ds[dn-1] = us[0] = ps[n-1].
        dn = 0;
        un = 0;
        for (int i = 0; i < n; ds[dn++] = ps[i++])
            while (dn >= 2 && EPS.le(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])), 0))
                dn--;
        for (int i = n - 1; i >= 0; us[un++] = ps[i--])
            while (un >= 2 && EPS.le(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])), 0))
                un--;
        Point[] res = new Point[dn + un - 2];
        System.arraycopy(ds, 0, res, 0, dn - 1);
        System.arraycopy(us, 0, res, dn - 1, un - 1);
        return res;
    }

    public static Point[] convexHullIncludingPointOnEdge(Point[] ps) {
        ps = ps.clone();
        int n = ps.length;
        Arrays.sort(ps);
        ds = new Point[n];// ds[0] = us[un-1] = ps[0].
        us = new Point[n];// ds[dn-1] = us[0] = ps[n-1].
        dn = 0;
        un = 0;
        for (int i = 0; i < n; ds[dn++] = ps[i++])
            while (dn >= 2 && EPS.lt(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])), 0))
                dn--;
        for (int i = n - 1; i >= 0; us[un++] = ps[i--])
            while (un >= 2 && EPS.lt(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])), 0))
                un--;
        Point[] res = new Point[dn + un - 2];
        System.arraycopy(ds, 0, res, 0, dn - 1);
        System.arraycopy(us, 0, res, dn - 1, un - 1);
        return res;
    }

    // pku2048
    public static int contains(Point[] ps, Point p) {// OUT,ON,IN = -1, 0, 1.
        int n = ps.length;
        int res = -1;
        for (int i = 0; i < n; i++) {
            Point a = ps[i].sub(p), b = ps[(i + 1) % n].sub(p);
            if (a.y > b.y) {
                Point t = a;
                a = b;
                b = t;
            }
            if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
            if (a.det(b) == 0 && a.dot(b) <= 0) return 0;
        }
        return res;
    }

    public boolean crs(Point[] ps1, Point[] ps2) {
        for (Point p : ps1) if (contains(ps2, p) == 1) return true;
        for (Point p : ps2) if (contains(ps1, p) == 1) return true;
        if (contains(ps2, gcenter(ps1)) == 1) return true;
        if (contains(ps1, gcenter(ps2)) == 1) return true;
        return false;
    }

    // center of gravity
    private Point gcenter(Point[] ps) {
        Point res = new Point(0, 0);
        for (Point p : ps) res = res.add(p);
        return res.div(ps.length);
    }
}
