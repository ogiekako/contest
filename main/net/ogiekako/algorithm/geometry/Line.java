package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.EPS;

public class Line {
    public final Point from, to;

    public Line(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    private Point dir = null;

    public Point direction() {
        if (dir != null) return dir;
        return dir = to.sub(from);
    }

    /**
     * The length of the perpendicular from the given point to this line.
     * <p>Verified: TCO15 R2A 950 TrianglePainting</p>
     */
    public double distance(Point p) {// AOJ1265
        return Math.abs(direction().det(p.sub(from))) / direction().norm();
    }

    public boolean intersect(Point p) {
        return EPS.eq(0, distance(p));
    }

    public boolean intersect(Segment s) {
        return s.intersect(this);
    }

    public boolean intersect(Line l) {
        double d = l.direction().det(direction());
        if (EPS.signum(d) == 0) {
            double e = l.direction().det(l.to.sub(from));
            if (Math.abs(e) < EPS.get()) return true;// same line.
            return false;// parallel
        }
        return true;
    }

    /**
     * Returns null if the lines are parallel. Otherwise returns the intersection point.
     * <p>Verified: AOJ2404 Dog Food</p>
     */
    public Point intersection(Line l) {
        double d = l.direction().det(direction());
        // The lines are identical or parallel. Use intersect(l) to know which.
        if (EPS.eq(d, 0)) {
            return null;
        }
        double e = l.direction().det(l.from.sub(from));
        return from.add(direction().mul(e / d));
    }

    /**
     * Returns POSITIVE_INFINITY if this line is parallel to the x-axis.
     * Otherwise return the x value of the intersection of this line and the line (Y = y).
     * In other words, (res, y) lies on this line.
     * <p>Verified: AOJ1265 Shy Polygons</p>
     */
    public double findXWhereYIs(double y) {
        if (from.y == to.y) return Double.POSITIVE_INFINITY;
        return (y - from.y) / (to.y - from.y) * (to.x - from.x) + from.x;
    }
}
