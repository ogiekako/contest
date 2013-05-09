package net.ogiekako.algorithm.geometry.lattice;

public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x; this.y = y;
    }

    public Point plus(Point o) {
        return make(x + o.x, y + o.y);
    }

    public Point minus(Point o) {
        return make(x - o.x, y - o.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public static Point make(int x, int y) {
        return new Point(x, y);

    }
}
