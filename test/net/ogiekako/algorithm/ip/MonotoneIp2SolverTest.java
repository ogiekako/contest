package net.ogiekako.algorithm.ip;

import org.junit.Assert;
import org.junit.Test;

import static net.ogiekako.algorithm.ip.MonotoneIp2Solver.IntToDouble;
import static net.ogiekako.algorithm.ip.MonotoneIp2Solver.Linear;

public class MonotoneIp2SolverTest {

    @Test
    public void testSolve() throws Exception {
        int n = 2;
        long[] u = new long[n];
        IntToDouble[] w = new IntToDouble[n];
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
    // TODO(oka): w can be negative
}