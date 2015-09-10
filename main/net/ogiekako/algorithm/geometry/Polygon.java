package net.ogiekako.algorithm.geometry;

public class Polygon implements GeometricalObject {
    private final Point[] ps;
    private final int n;

    public Polygon(Point[] ps) {
        this.ps = ps.clone();
        n = ps.length;
    }

    /**
     * Ordering doesn't matter, however the orientation (counterclockwise or clockwise)
     * does matter.
     * TODO: Current implementation assumes all points are distinct. Remove this restriction.
     */
    @Override
    public boolean isEqualTo(GeometricalObject other) {
        if (!(other instanceof Polygon)) return false;
        Polygon o = (Polygon) other;
        if (ps.length != o.ps.length) return false;
        Polygon[] polygons = {this, o};
        // Index of the smallest point.
        int[] zeros = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                if (polygons[i].ps[j].compareTo(polygons[i].ps[zeros[i]]) < 0) {
                    zeros[i] = j;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (polygons[0].ps[(zeros[0] + i) % n].isEqualTo(polygons[1].ps[(zeros[1] + i) % n])) {
                return false;
            }
        }
        return true;
    }

    /**
     * If the given point is inside this polygon including boundaries.
     */
    public boolean contains(Point p) {
        int n = ps.length;
        int res = -1;
        for (int i = 0; i < n; i++) {
            Point a = ps[i].sub(p), b = ps[(i + 1) % n].sub(p);
            if (a.y > b.y) {
                Point t = a;
                a = b;
                b = t;
            }
            assert a.y <= b.y;
            if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
            // Boundary. (0, 0) lies on ab.
            if (new Segment(a, b).intersect(new Point(0, 0))) {
                return true;
            }
        }
        return res == 1;
    }
}
