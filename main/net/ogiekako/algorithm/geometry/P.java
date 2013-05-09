package net.ogiekako.algorithm.geometry;


import net.ogiekako.algorithm.EPS;
public class P implements Comparable<P> {

    static int sgn(double d) {
        return d < -EPS.get() ? -1 : d > EPS.get() ? 1 : 0;
    }

    static boolean le(double a, double b) {
        return a + EPS.get() < b;
    }

    static boolean eq(double a, double b) {
        return Math.abs(a - b) < EPS.get();
    }

    public final double x, y;

    public P(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dist(P p) {
        return sub(p).norm();
    }

    double dist2(P p) {
        return sub(p).norm2();
    }

    double norm() {
        return Math.sqrt(x * x + y * y);
    }

    double norm2() {
        return x * x + y * y;
    }

    public P sub(P p) {
        return new P(x - p.x, y - p.y);
    }

    public P add(P p) {
        return new P(x + p.x, y + p.y);
    }

    public P mul(double d) {
        return new P(x * d, y * d);
    }

    P div(double d) {
        return new P(x / d, y / d);
    }

    public double dot(P p) {
        return x * p.x + y * p.y;
    }

    public double det(P p) {
        return x * p.y - y * p.x;
    }

    double argTo(P p) {
        return p.sub(this).arg();
    }

    public double arg() {
        double res = Math.atan2(y, x);
        if (res < -EPS.get()) res += 2 * Math.PI;
        if (res < 0) res = 0;
        return res;
    }

    /**
     * this を原点周りに res 回したら p と重なる.
     */
    public double arg(P p) {// [0,2*PI)
        double res = Math.atan2(det(p), dot(p));
        if (res < -EPS.get()) res += 2 * Math.PI;
        if (res < 0) res = 0;
        return res;
    }

    P rot90() {
        return new P(-y, x);
    }

    P rot(double r) {
        return new P(x * Math.cos(r) - y * Math.sin(r), y * Math.cos(r) + x * Math.sin(r));
    }

    P unit(double d) {
        return mul(d / norm());
    }

    public int compareTo(P o) {
        return sgn(x - o.x) == 0 ? sgn(y - o.y) : sgn(x - o.x);
    }

    public static P make(double x, double y) {
        return new P(x, y);
    }
    /*
    relative or absolute error is less than EPS.
    */
    public boolean eq(P p) {
        double absDiff = dist(p);
        double myLen = norm();
        double pLen = p.norm();
        return absDiff < EPS.get() || absDiff < myLen * EPS.get() || absDiff < pLen * EPS.get();
    }
}