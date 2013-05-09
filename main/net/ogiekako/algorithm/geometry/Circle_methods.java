package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

/*
 - アポロニウスの円(AP:PB=a:b(a!=b) なる点Pの軌跡)は,内分点と,外分点を直径とする円.

 */
public class Circle_methods {
    private Circle_methods() {}

    // verified: pku3011.
    public static P[][] innerTangent(P o1, double r1, P o2, double r2) {// 内接線
        P o1o2 = o2.sub(o1);
        double l = o1o2.norm() * o1o2.norm();
        double d = l - (r1 + r2) * (r1 + r2);
        if (d < 0) return null;
        if (EPS.eq(d, 0)) {
            P p = o1.add(o1o2.mul(r1 / (r1 + r2)));
            return new P[][]{{p, p.add(o1o2.rot90().mul(1 / (r1 + r2)))}};
        }
        P p = o1o2.mul((r1 + r2) / l);
        P q = o1o2.rot90().mul(Math.sqrt(d) / l);
        return new P[][]{{o1.add(p.mul(r1)).add(q.mul(r1)), o2.sub(p.mul(r2)).sub(q.mul(r2))},
                {o1.add(p.mul(r1)).sub(q.mul(r1)), o2.sub(p.mul(r2)).add(q.mul(r2))}};
    }
    // verified: pku3011.
    public static P[][] outerTangent(P o1, double r1, P o2, double r2) {// 外接線
        P o1o2 = o2.sub(o1);
        double l = o1o2.norm2();
        double d = l - (r1 - r2) * (r1 - r2);
        if (d < 0) return null;
        P p = o1o2.mul((r1 - r2) / l);
        P q = o1o2.rot90().mul(Math.sqrt(d) / l);
        return new P[][]{{o1.add(p.mul(r1)).add(q.mul(r1)), o2.add(p.mul(r2)).add(q.mul(r2))},
                {o1.add(p.mul(r1)).sub(q.mul(r1)), o2.add(p.mul(r2)).sub(q.mul(r2))}};
    }


    // verified at GCJ10R2 D
    double areaCC(P o1, double r1, P o2, double r2) {// 円と円の共通部分の面積
        double R = o1.dist(o2);
        if (r1 + r2 - R < EPS.EPS) return 0.0;
        double x = (R * R + r1 * r1 - r2 * r2) / (2 * R);
        double d = r1 * r1 - x * x;
        if (d < -EPS.EPS) return Math.PI * Math.min(r1, r2) * Math.min(r1, r2);
        if (d < 0) d = 0;
        double y = Math.sqrt(d);
        double theta1 = Math.atan2(y, x), theta2 = Math.atan2(y, R - x);
        return theta1 * r1 * r1 + theta2 * r2 * r2 - R * y;
    }

    // o1 -> o2 の右側から.
    public static P[] isCC(P o1, double r1, P o2, double r2, double EPS) {
        double R = o1.dist2(o2);
        double x = (R + r1 * r1 - r2 * r2) / (2 * R);
        double Y = r1 * r1 / R - x * x;
        if (Y < -EPS) return new P[0];
        if (Y < 0) Y = 0;
        P p1 = o1.add(o2.sub(o1).mul(x));
        P p2 = o2.sub(o1).rot90().mul(Math.sqrt(Y));
        return new P[]{p1.sub(p2), p1.add(p2)};
    }

    // p1に近い順.
    public static P[] isCL(P o, double r, P p1, P p2) {
        double x = p2.sub(p1).dot(o.sub(p1));
        double y = p1.dist2(p2);
        double d = y * (r * r - o.dist2(p1)) + x * x;
        if (d < -EPS.EPS) return new P[0];
        if (d < 0) d = 0;
        P s1 = p1.add(p2.sub(p1).mul(x / y));
        P s2 = p2.sub(p1).mul(Math.sqrt(d) / y);
        return new P[]{s1.sub(s2), s1.add(s2)};
    }


    // p -> o の右側から.
    public static P[] tanCP(P o, double r, P p) {
        double x = p.dist2(o);
        double d = x - r * r;
        if (d < -EPS.EPS) return new P[0];
        if (d < 0) d = 0;
        P s1 = p.sub(o).mul(r * r / x);
        P s2 = p.sub(o).rot90().mul(r * Math.sqrt(d) / x);
        return new P[]{o.add(s1.add(s2)), o.add(s1.sub(s2))};
    }

    /**
     * pを中心とする半径rの円の上に(res[i],y) がある.
     *
     * @param p
     * @param r
     * @param y
     * @return
     */
    private double[] crsCX(P p, double r, double y) {// AOJ1265
        double D = r * r - (y - p.y) * (y - p.y);
        if (D < 0) return new double[0];
        double d = Math.sqrt(D);
        return new double[]{p.x + d, p.x - d};
    }

    private double[] crsCY(P p, double r, double x) {// AOJ1265
        double D = r * r - (x - p.x) * (x - p.x);
        if (D < 0) return new double[0];
        double d = Math.sqrt(D);
        return new double[]{p.y + d, p.y - d};
    }
}
