package src;

import net.ogiekako.algorithm.geometry.Point;

import java.util.*;

public class FamilyCrest {
    double PRECISION = 1e-12;
    double MAX = 1e3;
    double MOVE_LEN = MAX * PRECISION;

    class Seg {
        Point a, b;

        public Seg move(double arg) {
            Seg res = new Seg();
            res.a = a.add(new Point(Math.cos(arg), Math.sin(arg)).mul(MOVE_LEN));
            res.b = b.add(new Point(Math.cos(arg), Math.sin(arg)).mul(MOVE_LEN));
            return res;
        }
    }

    class Angle {// counter clockwise
        double from;
        double to;

        public Angle(double from, double to) {
            this.from = normalize(from);
            this.to = normalize(to);
        }

        @Override
        public String toString() {
            return "Angle{" +
                    "from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    public String canBeInfinite(int[] A, int[] B, int[] C, int[] D) {
        int n = A.length;
        Seg[] s = new Seg[n];
        for (int i = 0; i < n; i++) {
            s[i] = new Seg();
            s[i].a = new Point(A[i], B[i]);
            s[i].b = new Point(C[i], D[i]);
        }

        List<Angle> forbidden = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double a = s[i].a.sub(s[i].b).arg();
            for (int j = 0; j < n; j++) {
                double b = s[j].a.sub(s[j].b).arg();
                double diff = Math.abs(a - b);
                if (Math.abs(diff) == 0 || Math.abs(diff - Math.PI) == 0) continue;
                List<Double> angles = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    angles.add(normalize(Math.PI * k + a));
                    angles.add(normalize(Math.PI * k + b));
                }
                Collections.sort(angles);
                for (int k = 0; k < angles.size(); k++) {
                    int k2 = (k + 1) % angles.size();
                    double a1 = angles.get(k);
                    double a2 = angles.get(k2);
                    double dir = (a1 + a2) / 2;
                    Seg moved = s[i].move(dir);
                    if (crsSS(moved.a, moved.b, s[j].a, s[j].b)) {
                        forbidden.add(new Angle(a1, a2));
                    }
                }
            }
        }
        debug("forbidden", forbidden);
        boolean covered = covered(forbidden);
        return covered ? "Finite" : "Infinite";
    }

    private boolean covered(List<Angle> forbidden) {
        Set<Double> as = new TreeSet<>();
        for (Angle angle : forbidden) {
            as.add(normalize(angle.from));
            as.add(normalize(angle.to));
        }
        double[] ds = tods(as.toArray(new Double[0]));
        boolean[] badMove = new boolean[ds.length]; // [i,i+1)
        for (Angle angle : forbidden) {
            int a = Arrays.binarySearch(ds, normalize(angle.from));
            int b = Arrays.binarySearch(ds, normalize(angle.to));
            if (b < a) b += badMove.length;
            for (int i = a; i < b; i++) badMove[i % badMove.length] = true;
        }
        double covered = 0;
        for (int i = 0; i < badMove.length; i++) {
            if (badMove[i]) {
                if (i < badMove.length - 1) {
                    covered += ds[i + 1] - ds[i];
                } else {
                    covered += ds[0] - ds[badMove.length - 1] + Math.PI * 2;
                }
            }
        }
        return covered >= Math.PI * 2 - 10 * PRECISION;
    }

    void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    private double[] tods(Double[] Ds) {
        double[] ds = new double[Ds.length];
        for (int i = 0; i < ds.length; i++) {
            ds[i] = Ds[i];
        }
        return ds;
    }

    private Double normalize(double arg) {
        arg %= Math.PI * 2;
        if (arg < 0) arg += Math.PI * 2;
        return arg;
    }

    // TODO: Add descr.
    public boolean crsSS(Point p1, Point p2, Point q1, Point q2) {
        if (Math.max(p1.x, p2.x) < Math.min(q1.x, q2.x)) return false;
        if (Math.max(q1.x, q2.x) < Math.min(p1.x, p2.x)) return false;
        if (Math.max(p1.y, p2.y) < Math.min(q1.y, q2.y)) return false;
        if (Math.max(q1.y, q2.y) < Math.min(p1.y, p2.y)) return false;
        return sgn(p2.sub(p1).det(q1.sub(p1))) * sgn(p2.sub(p1).det(q2.sub(p1))) <= 0 &&
                sgn(q2.sub(q1).det(p1.sub(q1))) * sgn(q2.sub(q1).det(p2.sub(q1))) <= 0;
    }

    int sgn(double d) {
        return d < -(double) 0 ? -1 : d > (double) 0 ? 1 : 0;
    }
}
