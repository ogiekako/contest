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
        if (dir != null)return dir;
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
}
