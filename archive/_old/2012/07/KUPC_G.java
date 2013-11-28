package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class KUPC_G {
    void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        double R = in.nextDouble();
        P[] ps = new P[N];
        for (int i = 0; i < N; i++) ps[i] = new P(Double.parseDouble(in.next()), Double.parseDouble(in.next()));
//        debug(ps);
        double r = Math.random() * 2 * Math.PI;
        for (int i = 0; i < N; i++) {
            ps[i] = ps[i].rot(r);
        }
        Arrays.sort(ps);
//        debug(ps);
        boolean[] visited = new boolean[N];
        int res = 0;
        for (int i = 0; i < ps.length; i++)
            if (!visited[i]) {
//            System.err.println(i);
                res++;
                P p = ps[i];
                P right = new P(p.x + R + 2, p.y);
                int to = -Arrays.binarySearch(ps, right) - 1;
                for (int j = i; j < to + 1 && j < N; j++) {
                    if (ps[j].dist(p) < 2 * R) {
                        visited[j] = true;
                    }
                }
            }
        out.println(res);
    }
    class P implements Comparable<P> {
        double x, y;
        P(double x, double y) {
            this.x = x; this.y = y;
        }
        P rot(double t) {
            double nx = Math.sin(t) * x - Math.cos(t) * y;
            double ny = Math.cos(t) * x + Math.sin(t) * y;
            return new P(nx, ny);
        }

        public int compareTo(P o) {
            return Double.compare(x, o.x);
        }

        public double dist(P p) {
            double dx = x - p.x;
            double dy = y - p.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() {
            return "P{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
