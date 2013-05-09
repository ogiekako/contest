package on_2012.on2012_5_19.fastdogfood;


import net.ogiekako.algorithm.EPS;
import net.ogiekako.algorithm.geometry.Line_methods;
import net.ogiekako.algorithm.geometry.P;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;
import java.util.ArrayList;

public class FastDogFood {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        if (n <= 0) throw new UnknownError();
        int Dx = in.nextInt(), Dy = in.nextInt();
        int Fx = in.nextInt(), Fy = in.nextInt();
        int[] x = new int[n], y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt(); y[i] = in.nextInt();
        }
        double res = solve(Dx, Dy, Fx, Fy, x, y);
        out.printf("%.12f\n", res);
    }

    private double solve(int Dx, int Dy, int Fx, int Fy, int[] x, int[] y) {
        double length = Math.hypot(Dx, Dy);
        int n = x.length;
        P origin = new P(0, 0);
        P Dog = new P(Dx, Dy);
        P Food = new P(Fx, Fy);
        P[] kui = new P[n];
        for (int i = 0; i < n; i++) kui[i] = new P(x[i], y[i]);
        double res = Double.POSITIVE_INFINITY;
        for (int msk = 0; msk < 1 << n; msk++) {
            int m = Integer.bitCount(msk);
            int[] is = new int[m];
            m = 0;
            for (int i = 0; i < n; i++) if ((msk >> i & 1) == 1) is[m++] = i;
            do {
                P[] orbit = new P[m + 2];
                orbit[0] = Dog;
                for (int i = 0; i < m; i++) orbit[i + 1] = kui[is[i]];
                orbit[m + 1] = Food;
                if (!goodOrbit(orbit)) continue;
                ArrayList<P> tangled = new ArrayList<P>();
                tangled.add(origin);
                proceed(Dog, 1, orbit, tangled, kui);
                tangled.add(Food);
                double needLength = 0;
                for (int i = 0; i < tangled.size() - 1; i++) needLength += tangled.get(i).dist(tangled.get(i + 1));
                if (needLength < length) {
                    double walk = 0;
                    for (int i = 0; i < orbit.length - 1; i++) walk += orbit[i].dist(orbit[i + 1]);
                    res = Math.min(res, walk);
                }
            } while (Permutation.nextPermutation(is));
        }
        return res == Double.POSITIVE_INFINITY ? -1 : res;
    }

    private boolean goodOrbit(P[] orbit) {
        P p = orbit[0];
        P r1 = new P(0, 0).sub(p);
        P Food = orbit[orbit.length - 1];
        P r2 = Food.sub(p);
        for (int i = 1; i < orbit.length; i++) {
            P q = orbit[i].sub(p);
            double dot1 = q.dot(r1);
            double dot2 = q.dot(r2);
            double det1 = r1.det(q);
            double det2 = q.det(r2);
            if (dot1 < -EPS.get() && dot2 < -EPS.get()) return false;
            if (Math.abs(det1) > EPS.get() && Math.abs(det2) > EPS.get() && Math.signum(det1) != Math.signum(det2))
                return false;
            r1 = q;
            r2 = Food.sub(orbit[i]);
            p = orbit[i];
        }
        return true;
    }

    private void proceed(P Dog, int p, P[] orbit, ArrayList<P> tangled, P[] kui) {
        if (p >= orbit.length) return;
        P next = orbit[p];
        P center = tangled.get(tangled.size() - 1);
        P nCenter = center;
        for (int i = 0; i < kui.length; i++)
            if (!kui[i].eq(center) && !kui[i].eq(Dog)) {
                P is = Line_methods.isLL(Dog, next, center, kui[i]);
                if (is != null && Line_methods.crsSP_exEdge(center, is, kui[i]) &&
                        (Line_methods.crsSP_exEdge(Dog, next, is) || is.eq(next) && Line_methods.crsSP_exEdge(nCenter, is, kui[i]))) {
                    next = is;
                    nCenter = kui[i];
                }
            }
        if (!nCenter.eq(center)) tangled.add(nCenter);
        if (next.eq(orbit[p])) p++;
        proceed(next, p, orbit, tangled, kui);
    }
}
