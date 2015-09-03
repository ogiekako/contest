package net.ogiekako.algorithm.geometry;

import static net.ogiekako.algorithm.EPS.eq;

public class Triangle_methods {
    private Triangle_methods() {
    }

    // 外心
    Point circumcenter(Point A, Point B, Point C) {// pku2957
        Point AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
        return (A.add(B).sub(AB.rot90().mul(BC.dot(CA) / AB.det(BC)))).mul(0.5);
    }

    // AP:BP:CP = a:b:c.
    Point[] ratioABC(Point A, Point B, Point C, double a, double b, double c) {// CDF 2C
        if (eq(a, b) && eq(a, c)) {
            return new Point[]{circumcenter(A, B, C)};
        }
        if (eq(a, b)) {
            double d = a;
            a = c;
            c = d;
            Point p = A;
            A = C;
            C = p;
        }
        if (eq(a, c)) {
            double d = a;
            a = b;
            b = d;
            Point p = A;
            A = B;
            B = p;
        }
        Point ab1 = A.mul(b).add(B.mul(a)).div(a + b);
        Point ab2 = A.mul(-b).add(B.mul(a)).div(a - b);
        Point o1 = ab1.add(ab2).div(2);
        double r1 = o1.distance(ab1);
        Point ac1 = A.mul(c).add(C.mul(a)).div(a + c);
        Point ac2 = A.mul(-c).add(C.mul(a)).div(a - c);
        Point o2 = ac1.add(ac2).div(2);
        double r2 = o2.distance(ac1);
        return Circle_methods.intersection(o1, r1, o2, r2, 1e-6);
    }

    /**
     * Returns singed area when move from a to b to c.
     * The sign is positive if it is conter-clockwise and negative if clockwise.
     * <p>Tested</p>
     */
    public static double signedArea(Point a, Point b, Point c) {
        return Polygon_methods.area(new Point[]{a, b, c});
    }
}
