package net.ogiekako.algorithm.geometry.lattice;

import net.ogiekako.algorithm.utils.IntegerUtils;

/**
* Created by IntelliJ IDEA.
* User: ogiekako
* Date: 12/05/05
* Time: 3:51
* To change this template use File | Settings | File Templates.
*/
public class QuadTree {
    Point small, large;
    QuadTree[][] children;
    int pointCount = 0;

    public int getPointCount(){
        return pointCount;
    }

    public QuadTree(Point small, Point large) {
        this.small = small;
        this.large = large;
    }

    public void add(Point p) {
        pointCount++;
        if (small.equals(large))return;

        int midX = IntegerUtils.floorHalf(small.x + large.x);
        int midY = IntegerUtils.floorHalf(small.y + large.y);

        int ix = p.x <= midX ? 0 : 1;
        int iy = p.y <= midY ? 0 : 1;
        if(children==null)children=new QuadTree[2][2];
        if (children[ix][iy] == null) {
            int nSmallX = ix == 0 ? small.x : midX + 1;
            int nLargeX = ix == 0 ? midX : large.x;
            int nSmallY = iy == 0 ? small.y : midY + 1;
            int nLargeY = iy == 0 ? midY : large.y;
            children[ix][iy] = new QuadTree(new Point(nSmallX, nSmallY), new Point(nLargeX, nLargeY));
        }
        children[ix][iy].add(p);
    }

    public int search(Rectangle region) {
        if (children==null || region.contains(getRectangle())) return pointCount;
        int res = 0;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if (children[i][j] != null) {
                    Rectangle intersection = children[i][j].getRectangle().intersection(region);
                    if (intersection != null) res += children[i][j].search(intersection);
                }
        return res;
    }

    public Rectangle getRectangle() {
        return new Rectangle(small, large);
    }

    public static int calcDist(Rectangle region, QuadTree root, int MX) {
        int left = -1, right = MX;
        do {
            int mid = (right + left) / 2;
            Point diff = new Point(mid, mid);
            Point small = region.small.minus(diff);
            Point large = region.large.plus(diff);
            if (root.search(new Rectangle(small, large)) > 0) {
                right = mid;
            } else {
                left = mid;
            }
        } while (right - left > 1);
        return right;
    }
}
