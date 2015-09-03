package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;
import net.ogiekako.algorithm.dataStructure.interval.Interval;

public class Segment implements GeometricalObject {
    public final Point a, b;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Segment(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Returns the distance of nearest points in this segment and the given segment.
     * <p>Verified: AOJ1265 Shy Polygons</p>
     */
    public double distance(Segment s) {
        if (intersect(s)) return 0;
        double res = Double.POSITIVE_INFINITY;
        res = Math.min(res, distance(s.a));
        res = Math.min(res, distance(s.b));
        res = Math.min(res, s.distance(a));
        res = Math.min(res, s.distance(b));
        return res;
    }

    /**
     * Returns the distance from p to a point on this segment that is nearest from p.
     * <p>Tested</p>
     */
    public double distance(Point p) {
        // Perpendicular foot is on the `a' side.
        if (b.sub(a).dot(p.sub(a)) < 0) return p.distance(a);
        // Perpendicular foot is on the `b' side.
        if (a.sub(b).dot(p.sub(b)) < 0) return p.distance(b);
        return new Line(a, b).distance(p);
    }

    public double length() {
        return a.distance(b);
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
     * Returns if this segment and the given segment (include end points) have non-empty intersection.
     * <p>Tested</p>
     */
    public boolean intersect(Segment s) {
        // These four if-s are necessary for taking care of corner cases where two segments lies on
        // the same line. They are also a good heuristic for speed up.
        if (EPS.lt(Math.max(a.x, b.x), Math.min(s.a.x, s.b.x))) {
            return false;
        }
        if (EPS.lt(Math.max(s.a.x, s.b.x), Math.min(a.x, b.x))) {
            return false;
        }
        if (EPS.lt(Math.max(a.y, b.y), Math.min(s.a.y, s.b.y))) {
            return false;
        }
        if (EPS.lt(Math.max(s.a.y, s.b.y), Math.min(a.y, b.y))) {
            return false;
        }
        // s is in completely right side or left side of this segment.
        if (EPS.signum(b.sub(a).det(s.a.sub(a))) * EPS.signum(b.sub(a).det(s.b.sub(a))) > 0) {
            return false;
        }
        // This segment is in completely right side or left side of s.
        if (EPS.signum(s.b.sub(s.a).det(a.sub(s.a))) * EPS.signum(s.b.sub(s.a).det(b.sub(s.a))) > 0) {
            return false;
        }
        return true;
    }

    /**
     * <p>Verified: TCO15 R2A 950 TrianglePainting</p>
     * <p>Tested</p>
     */
    public boolean intersect(Line l) {
        return EPS.signum(l.direction().det(a.sub(l.from))) * EPS.signum(l.direction().det(b.sub(l.from))) <= 0;
    }

    public boolean intersect(Point p) {// AOJ1299
        return EPS.eq(a.distance(p) + b.distance(p), a.distance(b)); // triangle inequality
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
