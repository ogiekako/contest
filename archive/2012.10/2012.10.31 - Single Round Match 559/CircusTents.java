package tmp;

import net.ogiekako.algorithm.geometry.Circle_methods;
import net.ogiekako.algorithm.geometry.Point;

import java.util.Arrays;

public class CircusTents {
    double EPS = 1e-9;

    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    public double findMaximumDistance(int[] x, int[] y, int[] r) {
        int n = x.length;
        Point[] center = new Point[n];
        for (int i = 0; i < n; i++) center[i] = new Point(x[i], y[i]);
        double[] theta = new double[n - 1];
        double[] phi = new double[n - 1];
        double[] candidates = new double[(n - 1) * 2 + 1];
        double[] dist = new double[n - 1];
        for (int i = 0; i < n - 1; i++) {
            Point[] ts = Circle_methods.tanCP(center[0], r[0], center[i + 1]);
            dist[i] = ts[0].dist(center[i + 1]) - r[i + 1];
            theta[i] = center[i + 1].sub(center[0]).arg();
            phi[i] = ts[0].sub(center[0]).arg() - theta[i];
            if (phi[i] < -EPS) phi[i] += 2 * Math.PI;
            if (phi[i] < 0) phi[i] = 0;
            candidates[i * 2] = theta[i];
            candidates[i * 2 + 1] = theta[i] + Math.PI;
            if (candidates[i * 2 + 1] > 2 * Math.PI - EPS) candidates[i * 2 + 1] -= 2 * Math.PI;
            if (candidates[i * 2 + 1] < 0) candidates[i * 2 + 1] = 0;
        }
        candidates[candidates.length - 1] = Double.POSITIVE_INFINITY;
        Arrays.sort(candidates);
        candidates[candidates.length - 1] = candidates[0] + 2 * Math.PI;
        double res = 0;
        for (int i = 0; i < candidates.length - 1; i++) {
            double from = candidates[i], to = candidates[i + 1];
            debug(from, to);
            for (int j = 0; j < 200; j++) {
                double m1 = (from * 2 + to) / 3, m2 = (from + to * 2) / 3;
                double d1 = solve(n, m1, center, theta, phi, r, dist);
                double d2 = solve(n, m2, center, theta, phi, r, dist);
                if (j < 10) debug(m1, m2, d1, d2);
                if (d1 < d2) {
                    from = m1;
                } else {
                    to = m2;
                }
                res = Math.max(res, d1);
            }
//            debug(from);
        }
        return res;
    }

    private double solve(int n, double a, Point[] center, double[] theta, double[] phi, int[] r, double[] dist) {
        double res = Double.POSITIVE_INFINITY;
        while (a > Math.PI * 2 - EPS) a -= Math.PI * 2;
        for (int i = 0; i < n - 1; i++) {
            boolean in = false;
            for (int j = -1; j <= 1; j++) {
                double na = a + 2 * Math.PI * j;
                if (theta[i] - phi[i] < na + EPS && na - EPS < theta[i] + phi[i]) in = true;
            }
            if (in) {
                Point p = center[0].add(new Point(r[0] * Math.cos(a), r[0] * Math.sin(a)));
                double val = p.dist(center[i + 1]) - r[i + 1];
                res = Math.min(res, val);
            } else {
                double val = Double.POSITIVE_INFINITY;
                for (int j = -1; j <= 1; j++) {
                    double na = a + 2 * Math.PI * j;
                    double d = na - (theta[i] + phi[i]);
                    if (d >= 0) val = Math.min(val, r[0] * d + dist[i]);
                    d = (theta[i] - phi[i]) - na;
                    if (d >= 0) val = Math.min(val, r[0] * d + dist[i]);
                }
                res = Math.min(res, val);
            }
        }
        return res;
    }


}
