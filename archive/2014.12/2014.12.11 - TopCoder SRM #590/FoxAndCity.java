package src;

import net.ogiekako.algorithm.ip.MonotoneIp2Solver;

import java.util.Arrays;

public class FoxAndCity {
    int INF = (int) 1e9;

    public int minimalCost(String[] linked, final int[] want) {
        int n = linked.length;
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], INF);
            d[i][i] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (linked[i].charAt(j) == 'Y') d[i][j] = 1;
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
        long[] u = new long[n - 1];
        MonotoneIp2Solver.LongToDouble[] w = new MonotoneIp2Solver.LongToDouble[n - 1];
        for (int i = 0; i < n - 1; i++) {
            u[i] = d[0][i + 1] - 1;
            final int p = want[i + 1];
            w[i] = new MonotoneIp2Solver.LongToDouble() {
                @Override
                public Double f(Long x) {
                    return (double) (x + 1 - p) * (x + 1 - p);
                }
            };
        }
        MonotoneIp2Solver monotoneIp2Solver = new MonotoneIp2Solver(u, w);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                monotoneIp2Solver.addConstraint(i - 1, j - 1, 1, 1, d[i][j], 0, 0, null);
            }
        }
        return (int) Math.round(monotoneIp2Solver.solve());
    }
}
