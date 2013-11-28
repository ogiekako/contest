package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Dom2012E {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (; ; ) {
            n = in.nextInt();
            if (n == 0) return;
            x = new int[n];
            y = new int[n];
            r = new int[n];
            for (int i = 0; i < n; i++) {
                x[i] = in.nextInt(); y[i] = in.nextInt(); r[i] = in.nextInt();
            }
            double res = solve();
            out.printf("%.12f\n", res);
        }
    }

    private double solve() {
        P[] os = new P[n];
        double[] rs = new double[n];
        for (int i = 0; i < n; i++) {
            os[i] = new P(x[i], y[i]);
            rs[i] = r[i];
        }
        P[][] cands = new P[n + 1][];
        double[][] dp = new double[n + 1][2];
        for (int i = 0; i < n + 1; i++) for (int j = 0; j < 2; j++) dp[i][j] = Double.POSITIVE_INFINITY;
        cands[0] = new P[]{os[0]};
        cands[n] = new P[]{os[n - 1]};
        for (int i = 0; i < n - 1; i++) {
            cands[i + 1] = isCC(os[i], rs[i], os[i + 1], rs[i + 1]);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n + 1; i++)
            for (int j = 0; j < cands[i].length; j++) {
                for (int k = i + 1; k < n + 1; k++)
                    for (int l = 0; l < cands[k].length; l++) {
                        P p1 = cands[i][j], p2 = cands[k][l];
                        boolean good = true;
                        for (int m = i; m < k - 1; m++) {
                            P[] c1s = isCL(os[m], rs[m], p1, p2);
                            P[] c2s = isCL(os[m + 1], rs[m + 1], p1, p2);
                            if (c1s.length == 0 || c2s.length == 0) {good = false; continue;}
                            P c1 = c1s[1];
                            P c2 = c2s[0];
                            if (!crsSP(p1, c1, c2)) {
                                good = false; break;
                            }
                        }
                        if (good) {
                            dp[k][l] = Math.min(dp[k][l], dp[i][j] + p1.dist(p2));
                        }
                    }
            }
        return dp[n][0];
    }

    private boolean crsSP(P p1, P p2, P q) {
        return p1.dist(q) + p2.dist(q) - p1.dist(p2) < 1e-9;
    }

    private P[] isCL(P o, double r, P p1, P p2) {
        double x = p2.sub(p1).dot(o.sub(p1));
        double y = p1.dist2(p2);
        double d = y * (r * r - o.dist2(p1)) + x * x;
        if (d < -1e-9) return new P[0];
        if (d < 0) d = 0;
        P s1 = p1.add(p2.sub(p1).mul(x / y));
        P s2 = p2.sub(p1).mul(Math.sqrt(d) / y);
        return new P[]{s1.sub(s2), s1.add(s2)};
    }

    private P[] isCC(P o1, double r1, P o2, double r2) {
        double R = o1.dist2(o2);
        double x = (R + r1 * r1 - r2 * r2) / (2 * R);
        double Y = r1 * r1 / R - x * x;
        if (Y < -1e-9) throw new AssertionError();
        if (Y < 0) Y = 0;
        P p1 = o1.add(o2.sub(o1).mul(x));
        P p2 = o2.sub(o1).rot90().mul(Math.sqrt(Y));
        return new P[]{p1.sub(p2), p1.add(p2)};
    }

    class P {
        double x, y;
        P(double x, double y) {
            this.x = x; this.y = y;
        }

        public double dist2(P p) {
            return sub(p).norm2();
        }

        private double norm2() {
            return x * x + y * y;
        }

        private P sub(P p) {
            return new P(x - p.x, y - p.y);
        }

        public P mul(double d) {
            return new P(x * d, y * d);
        }

        public P add(P p) {
            return new P(x + p.x, y + p.y);
        }

        public P rot90() {
            return new P(-y, x);
        }

        public double dist(P p) {
            return Math.sqrt(dist2(p));
        }

        public double dot(P p) {
            return x * p.x + y * p.y;
        }
    }

    int n;
    int[] x, y, r;
}
