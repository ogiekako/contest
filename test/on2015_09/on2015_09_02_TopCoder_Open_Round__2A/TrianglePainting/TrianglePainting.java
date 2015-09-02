package on2015_09.on2015_09_02_TopCoder_Open_Round__2A.TrianglePainting;



import net.ogiekako.algorithm.geometry.Line;
import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.geometry.Segment;

import java.util.Arrays;

public class TrianglePainting {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double expectedArea(int[] x1, int[] y1, int[] x2, int[] y2, int[] prob) {
        int n = x1.length;
        Point o = new Point(0,0);
        Point[] p1s = new Point[n], p2s = new Point[n];
        for (int i = 0; i < n; i++) {
            p1s[i] = new Point(x1[i], y1[i]);
            p2s[i] = new Point(x2[i], y2[i]);
        }
        double res = 0;
        for (int i = 0; i < n; i++) {
            Point[] ps = new Point[3];
            ps[0] = o;
            ps[1] = p1s[i];
            ps[2] = p2s[i];
            for (int j = 0; j < 3; j++) {
                Segment seg = new Segment(ps[(j+1)%3], ps[(j+2)%3]);
                Line line = seg.asLine();
                double dist = seg.asLine().distance(o);
                for (int k = 0; k < n; k++) {
                    if (k == i) continue;
                    Point a1 = seg.a.add(p1s[k]);
                    Point a2 = seg.a.add(p2s[k]);

                    if (!new Segment(ps[j], a1).intersect(line)) {
                        a1 = null;
                    }
                    if (!new Segment(ps[j], a2).intersect(line)) {
                        a2 = null;
                    }
                    if (a1 == null) a1 = a2;
                    if (a1 == null) continue;
                    double d1 = seg.asLine().distance(a1);
                    if (a2 != null) {
                        double d2 = seg.asLine().distance(a2);
                        if (d1 < d2) {
                            d1 = d2;
                        }
                    }
                    dist += d1 * prob[k] / 100;
                }
                res += dist * seg.length() / 2 * prob[i] / 100;
            }
        }
        return res;
    }
}
