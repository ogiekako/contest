package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

public class Segment implements GeometricalObject {
    public final Point a, b;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Segment(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public double length() {
        return a.dist(b);
    }

    public Point asPoint() {
        return a;
    }

    /**
     * Return the intersection (possibly end point) or null.
     * <p>Tested</p>
     */
    public Segment intersection(Line l) {
        if (!intersect(l)) {
            return null;
        }
        if (l.intersect(a) && l.intersect(b)) return this;
        //noinspection ConstantConditions
        return new Line(a, b).intersection(new Line(l.from, l.to)).asSegment();
    }

    /**
     * <p>Verified: TCO15 R2A 950 TrianglePainting</p>
     */
    public boolean intersect(Line l) {
        return EPS.signum(l.direction().det(a.sub(l.from))) * EPS.signum(l.direction().det(b.sub(l.from))) <= 0;
    }

    public boolean intersect(Point p) {// AOJ1299
        return EPS.eq(a.dist(p) + b.dist(p), a.dist(b)); // triangle inequality
    }

    /**
     * <p>Verified: AOJ2404 Dog Food</p>
     */
    public boolean intersectStrict(Point p) {
        return !a.isEqualTo(p) && !b.isEqualTo(p) && intersect(p);
    }

    public boolean intersectStrict(Line l) {
        return EPS.signum(l.direction().det(a.sub(l.from))) * EPS.signum(l.direction().det(b.sub(l.from))) < 0;
    }

    @Override
    public boolean isEqualTo(GeometricalObject other) {
        if (!(other instanceof Segment)) {
            return false;
        }
        Segment o = (Segment) other;
        return a.isEqualTo(o.a) && b.isEqualTo(o.b);
    }

    @Override
    public String toString() {
        return "S: " + a + "-" + b;
    }

    public Line asLine() {
        return new Line(a, b);
    }

}
