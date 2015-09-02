package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

public class Point implements Comparable<Point>, GeometricalObject {

    static int precision = 2;
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static int sgn(double d) {
        return d < -EPS.value() ? -1 : d > EPS.value() ? 1 : 0;
    }

    static boolean le(double a, double b) {
        return a + EPS.value() < b;
    }

    static boolean eq(double a, double b) {
        return Math.abs(a - b) < EPS.value();
    }

    public static Point make(double x, double y) {
        return new Point(x, y);
    }

    public static void setPrecisionOfString(int precision) {
        Point.precision = precision;
    }

    public double dist(Point p) {
        return sub(p).norm();
    }

    double dist2(Point p) {
        return sub(p).norm2();
    }

    double norm() {
        return Math.sqrt(x * x + y * y);
    }

    double norm2() {
        return x * x + y * y;
    }

    public Point sub(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point mul(double d) {
        return new Point(x * d, y * d);
    }

    Point div(double d) {
        return new Point(x / d, y / d);
    }

    public double dot(Point p) {
        return x * p.x + y * p.y;
    }

    public double det(Point p) {
        return x * p.y - y * p.x;
    }

    double argTo(Point p) {
        return p.sub(this).arg();
    }

    public double arg() {
        double res = Math.atan2(y, x);
        if (res < -EPS.value()) res += 2 * Math.PI;
        if (res < 0) res = 0;
        return res;
    }

    /**
     * this を原点周りに res 回したら p と重なる.
     */
    public double arg(Point p) {// [0,2*PI)
        double res = Math.atan2(det(p), dot(p));
        if (res < -EPS.value()) res += 2 * Math.PI;
        if (res < 0) res = 0;
        return res;
    }

    Point rot90() {
        return new Point(-y, x);
    }

    Point rot(double r) {
        return new Point(x * Math.cos(r) - y * Math.sin(r), y * Math.cos(r) + x * Math.sin(r));
    }

    Point unit(double d) {
        return mul(d / norm());
    }

    public int compareTo(Point o) {
        return sgn(x - o.x) == 0 ? sgn(y - o.y) : sgn(x - o.x);
    }

    @Override
    public String toString() {
        String format = String.format("%%.%df %%.%df", precision, precision);
        return String.format(format, x, y);
    }

    public Segment asSegment() {
        return new Segment(this, this);
    }

    /**
     * Return true if relative or absolute error is less than EPS value.
     * <p>Verified: AOJ2404 Dog Food</p>
     */
    public boolean isEqualTo(GeometricalObject other) {
        if (!(other instanceof Point)) return false;
        Point o = (Point) other;
        double absDiff = dist(o);
        double myLen = norm();
        double pLen = o.norm();
        return absDiff < EPS.value() || absDiff < myLen * EPS.value() || absDiff < pLen * EPS.value();
    }
}
