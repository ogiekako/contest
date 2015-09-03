package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

/*
 - アポロニウスの円(AP:PB=a:b(a!=b) なる点Pの軌跡)は,内分点と,外分点を直径とする円.

 */
public class Circle_methods {
    private Circle_methods() {}

    // verified: pku3011.
    public static Point[][] innerTangent(Point o1, double r1, Point o2, double r2) {// 内接線
        Point o1o2 = o2.sub(o1);
        double l = o1o2.norm() * o1o2.norm();
        double d = l - (r1 + r2) * (r1 + r2);
        if (d < 0) return null;
        if (EPS.eq(d, 0)) {
            Point p = o1.add(o1o2.mul(r1 / (r1 + r2)));
            return new Point[][]{{p, p.add(o1o2.rot90().mul(1 / (r1 + r2)))}};
        }
        Point p = o1o2.mul((r1 + r2) / l);
        Point q = o1o2.rot90().mul(Math.sqrt(d) / l);
        return new Point[][]{{o1.add(p.mul(r1)).add(q.mul(r1)), o2.sub(p.mul(r2)).sub(q.mul(r2))},
                {o1.add(p.mul(r1)).sub(q.mul(r1)), o2.sub(p.mul(r2)).add(q.mul(r2))}};
    }
    // verified: pku3011.
    public static Point[][] outerTangent(Point o1, double r1, Point o2, double r2) {// 外接線
        Point o1o2 = o2.sub(o1);
        double l = o1o2.norm2();
        double d = l - (r1 - r2) * (r1 - r2);
        if (d < 0) return null;
        Point p = o1o2.mul((r1 - r2) / l);
        Point q = o1o2.rot90().mul(Math.sqrt(d) / l);
        return new Point[][]{{o1.add(p.mul(r1)).add(q.mul(r1)), o2.add(p.mul(r2)).add(q.mul(r2))},
                {o1.add(p.mul(r1)).sub(q.mul(r1)), o2.add(p.mul(r2)).sub(q.mul(r2))}};
    }


    // verified at GCJ10R2 D
    double areaCC(Point o1, double r1, Point o2, double r2) {// 円と円の共通部分の面積
        double R = o1.distance(o2);
        if (r1 + r2 - R < EPS.value()) return 0.0;
        double x = (R * R + r1 * r1 - r2 * r2) / (2 * R);
        double d = r1 * r1 - x * x;
        if (d < -EPS.value()) return Math.PI * Math.min(r1, r2) * Math.min(r1, r2);
        if (d < 0) d = 0;
        double y = Math.sqrt(d);
        double theta1 = Math.atan2(y, x), theta2 = Math.atan2(y, R - x);
        return theta1 * r1 * r1 + theta2 * r2 * r2 - R * y;
    }

    // o1 -> o2 の右側から.
    public static Point[] intersection(Point o1, double r1, Point o2, double r2, double EPS) {
        double R = o1.dist2(o2);
        double x = (R + r1 * r1 - r2 * r2) / (2 * R);
        double Y = r1 * r1 / R - x * x;
        if (Y < -EPS) return new Point[0];
        if (Y < 0) Y = 0;
        Point p1 = o1.add(o2.sub(o1).mul(x));
        Point p2 = o2.sub(o1).rot90().mul(Math.sqrt(Y));
        return new Point[]{p1.sub(p2), p1.add(p2)};
    }

    // p1に近い順.
    public static Point[] isCL(Point o, double r, Point p1, Point p2) {
        double x = p2.sub(p1).dot(o.sub(p1));
        double y = p1.dist2(p2);
        double d = y * (r * r - o.dist2(p1)) + x * x;
        if (d < -EPS.value()) return new Point[0];
        if (d < 0) d = 0;
        Point s1 = p1.add(p2.sub(p1).mul(x / y));
        Point s2 = p2.sub(p1).mul(Math.sqrt(d) / y);
        return new Point[]{s1.sub(s2), s1.add(s2)};
    }


    // p -> o の右側から.
    public static Point[] tanCP(Point o, double r, Point p) {
        double x = p.dist2(o);
        double d = x - r * r;
        if (d < -EPS.value()) return new Point[0];
        if (d < 0) d = 0;
        Point s1 = p.sub(o).mul(r * r / x);
        Point s2 = p.sub(o).rot90().mul(r * Math.sqrt(d) / x);
        return new Point[]{o.add(s1.add(s2)), o.add(s1.sub(s2))};
    }

    /**
     * pを中心とする半径rの円の上に(res[i],y) がある.
     *
     * @param p
     * @param r
     * @param y
     * @return
     */
    private double[] crsCX(Point p, double r, double y) {// AOJ1265
        double D = r * r - (y - p.y) * (y - p.y);
        if (D < 0) return new double[0];
        double d = Math.sqrt(D);
        return new double[]{p.x + d, p.x - d};
    }

    private double[] crsCY(Point p, double r, double x) {// AOJ1265
        double D = r * r - (x - p.x) * (x - p.x);
        if (D < 0) return new double[0];
        double d = Math.sqrt(D);
        return new double[]{p.y + d, p.y - d};
    }
}
