package tmp;

import net.ogiekako.algorithm.geometry.Circle_methods;
import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int tc = 1; ; tc++) {
            int x = in.nextInt();
            if (x == 0) return;
            int[] ds = new int[4];
            ds[0] = x;
            for (int i = 1; i < 4; i++) ds[i] = in.nextInt();
            double res = solve(ds);
            out.printf("Case %d: %d\n", tc, (int) Math.round(res));
        }
    }

    private double solve(int[] ds) {
        double left = 0, right = 100000;
        for (int d : ds) left = Math.max(left, d + EPS);
        for (int i = 0; i < 50; i++) {
            double md = (left + right) / 2;
            if (can(ds, md)) right = md;
            else left = md;
        }
        return right;
    }

    int N;
    Point center;
    double R;
    int[] ds;
    Point[] os;
    double EPS = 1e-6;
    private boolean can(int[] ds, double R) {
        this.ds = ds;
        this.R = R;
        N = ds.length;
        Arrays.sort(ds);
        do {
            center = Point.make(0, 0);
            os = new Point[N];
            os[0] = center.add(Point.make(0, R - ds[0]));
            boolean res = dfs(1);
            if (res) return true;
        } while (Permutation.nextPermutation(ds));
        return false;
    }

    private boolean dfs(int p) {
        if (p >= N) {
            for (int i = 0; i < N; i++) {
                if (os[i].distance(center) < R - ds[i] + EPS) ;
                else return false;
            }
            for (int i = 0; i < N; i++)
                for (int j = 0; j < i; j++) {
                    if (os[i].distance(os[j]) < ds[i] + ds[j] - EPS) return false;
                }
            return true;
        }
        for (int i = 0; i < p; i++) {
            Point p1 = center;
            double l1 = R - ds[p];
            Point p2 = os[i];
            double l2 = ds[i] + ds[p];
            Point[] cs = Circle_methods.intersection(p1, l1, p2, l2, EPS);
            for (Point c : cs) {
                os[p] = c;
                if (dfs(p + 1)) return true;
            }
        }
        for (int i = 0; i < p; i++)
            for (int j = 0; j < i; j++) {
                Point p1 = os[i];
                double l1 = ds[i] + ds[p];
                Point p2 = os[j];
                double l2 = ds[j] + ds[p];
                Point[] cs = Circle_methods.intersection(p1, l1, p2, l2, EPS);
                for (Point c : cs) {
                    os[p] = c;
                    if (dfs(p + 1)) return true;
                }
            }
        return false;
    }
}
