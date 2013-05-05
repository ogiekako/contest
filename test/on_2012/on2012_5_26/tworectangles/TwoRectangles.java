package on_2012.on2012_5_26.tworectangles;


// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.geometry.lattice.Rectangle;

public class TwoRectangles {
    public String describeIntersection(int[] A, int[] B) {
        Rectangle r1 = Rectangle.make(A[0], A[1], A[2], A[3]);
        Rectangle r2 = Rectangle.make(B[0], B[1], B[2], B[3]);
        Rectangle is = r1.intersection(r2);
        if (is == null) return "none";
        if (is.xSide() == 0 && is.ySide() == 0) return "point";
        if (is.xSide() > 0 && is.ySide() > 0) return "rectangle";
        return "segment";
    }
}

