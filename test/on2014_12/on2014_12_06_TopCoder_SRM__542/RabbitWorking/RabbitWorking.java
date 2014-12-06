package on2014_12.on2014_12_06_TopCoder_SRM__542.RabbitWorking;


import net.ogiekako.algorithm.ip.MonotoneIp2Solver;

import java.util.Arrays;

public class RabbitWorking {
    public double getMaximum(String[] profit) {
        double left = 0, right = 10;
        for (int i = 0; i < 35; i++) {
            double R = (left + right) / 2;
            if (possible(profit, R)) {
                left = R;
            } else {
                right = R;
            }
        }
        return right;
    }

    boolean possible(String[] profit, double R) {
        int n = profit.length;
        int numVertex = 0;
        int[] x1 = new int[n];
        int[][] x2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            x1[i] = numVertex++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x2[i][j] = numVertex++;
            }
        }
        long[] u = new long[numVertex];
        MonotoneIp2Solver.LongToDouble[] w = new MonotoneIp2Solver.LongToDouble[numVertex];
        Arrays.fill(u, 1);
        for (int i = 0; i < n; i++) {
            w[x1[i]] = new MonotoneIp2Solver.Linear(400 * R);
            for (int j = 0; j < n; j++) {
                w[x2[i][j]] = new MonotoneIp2Solver.Linear(-(profit[i].charAt(j) - '0' + 2 * R));
            }
        }

        MonotoneIp2Solver monotoneIp2Solver = new MonotoneIp2Solver(u, w);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // yij - xi <= 0
                monotoneIp2Solver.addConstraint(x2[i][j], x1[i], 1, 1, 0, 0, 0, new MonotoneIp2Solver.Linear(0));
                // yij - xj <= 0
                if (i != j) {
                    monotoneIp2Solver.addConstraint(x2[i][j], x1[j], 1, 1, 0, 0, 0, new MonotoneIp2Solver.Linear(0));
                }
            }
        }
        return -monotoneIp2Solver.solve() > 1e-9;
    }
}
