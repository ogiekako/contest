package net.ogiekako.algorithm.geometry.lattice;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/04/29
 * Time: 4:51
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle {
    // [small.x, large.x] * [small.y, large.y]
    public final Point small;
    public final Point large;

    public Rectangle(Point small, Point large) {
        if (small.x > large.x) throw new IllegalArgumentException();
        if (small.y > large.y) throw new IllegalArgumentException();
        this.small = small;
        this.large = large;
    }

    public Rectangle intersection(Rectangle other) {
        int nSmallX = Math.max(small.x, other.small.x);
        int nSmallY = Math.max(small.y, other.small.y);
        int nLargeX = Math.min(large.x, other.large.x);
        int nLargeY = Math.min(large.y, other.large.y);
        if (nSmallX <= nLargeX && nSmallY <= nLargeY)
            return new Rectangle(new Point(nSmallX, nSmallY), new Point(nLargeX, nLargeY));
        return null;
    }

    public boolean intersect(Rectangle other) {
        return intersection(other) != null;
    }

    public Rectangle expand(int len) {
        Point d = Point.make(len, len);
        return make(small.minus(d), large.plus(d));
    }

    public static Rectangle make(Point small, Point large) {
        return new Rectangle(small, large);
    }

    public static Rectangle make(int smallX, int smallY, int largeX, int largeY) {
        return make(Point.make(smallX, smallY), Point.make(largeX, largeY));
    }

    public long size() {
        return (long)(large.x - small.x + 1) * (large.y - small.y + 1);
    }

    public boolean contains(Rectangle other) {
        return small.x <= other.small.x && other.large.x <= large.x
                && small.y <= other.small.y && other.large.y <= large.y;
    }

    public int ySide() {
        return large.y - small.y;
    }

    public int xSide() {
        return large.x - small.x;
    }
}
