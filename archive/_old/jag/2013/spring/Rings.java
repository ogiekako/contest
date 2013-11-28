package src;
// implement 0:45 - 1:34
// debug 1:46 - 3:45

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class Rings {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Point OO = of(in), OX = of(in), OY = of(in), OZ = OX.det(OY);
        Point O = of(in), X = of(in), Y = of(in);
        O = O.minus(OO).as(OX, OY, OZ);// bug fixed
        X = X.as(OX, OY, OZ);
        Y = Y.as(OX, OY, OZ);
        Circle C = new Circle(O, X, Y);
        double tMax = C.findZPos(true);
        double tMin = C.findZPos(false);
        if (C.zAt(tMax) < 0 || C.zAt(tMin) > 0) {
            out.println("NO"); return;
        }
        double t1 = C.findZero(tMin, tMax);
        double t2 = C.findZero(tMax, tMin);
        if (C.at(t1).norm2D() < 1 ^ C.at(t2).norm2D() < 1) {
            out.println("YES");
        } else {
            out.println("NO");
        }
    }

    private Point of(MyScanner in) {
        return of(in.nextDouble(), in.nextDouble(), in.nextDouble());
    }

    Point of(double x, double y, double z) {
        Point res = new Point();
        res.x = x; res.y = y; res.z = z;
        return res;
    }

    class Circle {
        Point O, X, Y;

        Circle(Point o, Point x, Point y) {
            O = o;
            X = x;
            Y = y;
        }

        public double findZPos(boolean max) {
            double t1 = findZPos(0, Math.PI, max);
            double t2 = findZPos(Math.PI, Math.PI * 2, max);
            if (zAt(t1) > zAt(t2) == max) return t1;
            return t2;
        }

        private double zAt(double theta) {
            return at(theta).z;
        }

        private Point at(double theta) {
            return O.add(X.times(Math.cos(theta)).add(Y.times(Math.sin(theta))));
        }

        private double findZPos(double from, double to, boolean max) {
            for (int i = 0; i < 200; i++) {
                double m1 = (from * 2 + to) / 3;
                double m2 = (from + to * 2) / 3;
                if (zAt(m1) > zAt(m2) == max) {
                    to = m2;
                } else {
                    from = m1;
                }
            }
            return from;
        }

        public double findZero(double from, double to) {
            while (to < from) to += Math.PI * 2;
            for (int i = 0; i < 200; i++) {
                double m = (from + to) / 2;
                double z1 = zAt(from);
                double z2 = zAt(m);
                if (Math.signum(z1) == Math.signum(z2)) {
                    from = m;
                } else {
                    to = m;
                }
            }
            return from;
        }
    }

    class Point {
        double x, y, z;

        public Point minus(Point o) {
            return of(x - o.x, y - o.y, z - o.z);
        }

        public Point det(Point o) {
            double nx = y * o.z - z * o.y;
            double ny = z * o.x - x * o.z;
            double nz = x * o.y - y * o.x;
            return of(nx, ny, nz);
        }

        public Point as(Point X, Point Y, Point Z) {
            return of(dot(X), dot(Y), dot(Z));
        }

        private double dot(Point o) {
            return x * o.x + y * o.y + z * o.z;
        }

        public Point times(double d) {
            return of(x * d, y * d, z * d);
        }

        public Point add(Point o) {
            return of(x + o.x, y + o.y, z + o.z);
        }

        public double norm2D() {
            return Math.sqrt(x * x + y * y);
        }

        public double norm() {
            return Math.sqrt(dot(this));
        }
    }
}
