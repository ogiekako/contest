package net.ogiekako.algorithm.ip;

import org.junit.Assert;
import org.junit.Test;

import static net.ogiekako.algorithm.ip.MonotoneIp2Solver.Linear;
import static net.ogiekako.algorithm.ip.MonotoneIp2Solver.LongToDouble;

public class MonotoneIp2SolverTest {

    @Test
    public void linearEquation() throws Exception {
        int n = 2;
        long[] u = new long[n];
        LongToDouble[] w = new LongToDouble[n];
        u[0] = 1;
        u[1] = 2;
        w[0] = new Linear(2, 0);
        w[1] = new Linear(3, 0);
        MonotoneIp2Solver monotoneIp2Solver = new MonotoneIp2Solver(u, w);
        //
        // x0 - x1 \leq -1 + z
        monotoneIp2Solver.addConstraint(0, 1, 1, 1, -1, 1, 1, new Linear(5));

        double res = monotoneIp2Solver.solve();
        Assert.assertEquals(3, res, 1e-9);
    }

    @Test
    public void wAndECanBeNegative() {
        int n = 2;
        long[] u = new long[n];
        LongToDouble[] w = new LongToDouble[n];
        u[0] = u[1] = 2;
        final long[][] ww = {{3, -5, 2}, {-5, 0, 2}};
        for (int i = 0; i < n; i++) {
            final int finalI = i;
            w[i] = new LongToDouble() {
                @Override
                public Double f(Long x) {
                    return (double) ww[finalI][(int) (long) x];
                }
            };
        }
        MonotoneIp2Solver monotoneIp2Solver = new MonotoneIp2Solver(u, w);
        // 2x0 - 3x1 <= -4 + z
        monotoneIp2Solver.addConstraint(0, 1, 2, 3, -4, 1, 2, new LongToDouble() {
            @Override
            public Double f(Long x) {
                return (x - 1) * (x - 1) - 4.0;
            }
        });
        double res = monotoneIp2Solver.solve();
        // x0 = 1, x1 = 2, z = 1
        // -> w(x0) = -5, w(x1) = 2, e(z) = -4
        // (2x0 - 3x1 = -4 <= -3 = -4 + z.)
        Assert.assertEquals(-7, res, 1e-9);
    }
}