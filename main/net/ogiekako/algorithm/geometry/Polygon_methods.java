package net.ogiekako.algorithm.geometry;

import java.util.ArrayList;
import java.util.Arrays;

import static net.ogiekako.algorithm.EPS.le;

public class Polygon_methods {
    public static double EPS = 1e-9;
    // p->r の左側を返す.
    P[] convexCut(P[] ps, P p, P r) {// AOJ1299
        int n = ps.length;
        ArrayList<P> list = new ArrayList<P>();
        for (int i = 0; i < n; i++) {
            P a = ps[i], b = ps[(i + 1) % n];
            if (ccw(p, r, a) != -1) list.add(a);
            if (ccw(p, r, a) * ccw(p, r, b) == -1) {
                P ab = b.sub(a), pr = r.sub(p);
                list.add(a.add(ab.mul(pr.det(p.sub(a)) / pr.det(ab))));
            }
        }
        return list.toArray(new P[0]);
    }
    int ccw(P a, P b, P c) {
        b = b.sub(a);
        c = c.sub(a);
        if (b.det(c) > EPS) return 1;
        if (b.det(c) < EPS) return -1;
        if (b.dot(c) < EPS) return 2;
        if (b.norm() + EPS < c.norm()) return -2;
        return 0;
    }
    static double area(P[] ps) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
        double S = 0;
        for (int i = 0; i < ps.length; i++)
            S += ps[i].det(ps[(i + 1) % ps.length]);
        return S / 2;
    }

    // verified: pku3348.
    // generate anti-clockwise convex hull.
    // le -> lt : include points on edge.
    static int dn;
    static P[] ds;
    static int un;
    static P[] us;
    public static P[] convexHull(P[] ps) { // O(n logn).
        ps = ps.clone();
        int n = ps.length;
        Arrays.sort(ps);
        ds = new P[n];// ds[0] = us[un-1] = ps[0].
        us = new P[n];// ds[dn-1] = us[0] = ps[n-1].
        dn = 0;
        un = 0;
        for (int i = 0; i < n; ds[dn++] = ps[i++])
            while (dn >= 2 && le(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])), 0))
                dn--;
        for (int i = n - 1; i >= 0; us[un++] = ps[i--])
            while (un >= 2 && le(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])), 0))
                un--;
        P[] res = new P[dn + un - 2];
        System.arraycopy(ds, 0, res, 0, dn - 1);
        System.arraycopy(us, 0, res, dn - 1, un - 1);
        return res;
    }

    // pku2048
    public static int contains(P[] ps, P p) {// OUT,ON,IN = -1, 0, 1.
        int n = ps.length;
        int res = -1;
        for (int i = 0; i < n; i++) {
            P a = ps[i].sub(p), b = ps[(i + 1) % n].sub(p);
            if (a.y > b.y) {
                P t = a;
                a = b;
                b = t;
            }
            if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
            if (a.det(b) == 0 && a.dot(b) <= 0) return 0;
        }
        return res;
    }

    public boolean crs(P[] ps1, P[] ps2) {
        for (P p : ps1) if (contains(ps2, p) == 1) return true;
        for (P p : ps2) if (contains(ps1, p) == 1) return true;
        if (contains(ps2, gcenter(ps1)) == 1) return true;
        if (contains(ps1, gcenter(ps2)) == 1) return true;
        return false;
    }
    // center of gravity
    private P gcenter(P[] ps) {
        P res = new P(0, 0);
        for (P p : ps) res = res.add(p);
        return res.div(ps.length);
    }
}
