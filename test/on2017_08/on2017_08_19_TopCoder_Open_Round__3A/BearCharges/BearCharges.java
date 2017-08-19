package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.BearCharges;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class BearCharges {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;
    double[][] dp;
    double[][] graph;

    public double minTime(int[] x, int[] y) {
        n = x.length;
        dp = new double[1 << n][n];
        ArrayUtils.fill(dp, -1);
        graph = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
            }
        }
        return recur((1 << n) - 1, 0);
    }

    private double recur(int mask, int i) {
        if (dp[mask][i] >= 0) return dp[mask][i];
        int m = mask ^ (1 << i);
        if (m == 0) {
            dp[mask][i] = 0;
            return 0;
        }
        return dp[mask][i] = recur2(new double[10], new int[10], m, 0, i);
    }

    private double recur2(double[] ts, int[] is, int remain, int m, int root) {
        if (remain == 0) {
            double time = 0;
            double res = 0;
            for (int i = 0; i < m; i++) {
                time += graph[root][is[i]];
                res = Math.max(ts[i] + time, res);
            }
            return res;
        }
        double res = Double.POSITIVE_INFINITY;
        for (int sub = remain; sub > 0; sub = (sub - 1) & remain) {
            for (int j = 0; j < n; j++) {
                if (sub << 31 - j < 0) {
                    ts[m] = recur(sub, j);
                    if (m - 1 >= 0 && ts[m] > ts[m - 1]) continue;
                    is[m] = j;
                    double cur = recur2(ts, is, remain ^ sub, m + 1, root);
                    res = Math.min(res, cur);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(1209182048L);
        int n = 6;
        for (int iter = 0; iter < 10000; iter++) {
            int[] x = new int[n];
            int[] y = new int[n];
            for (int i = 0; i < n; i++) {
                x[i] = rnd.nextInt(100);
                y[i] = rnd.nextInt(100);
            }
            double res = new BearCharges().minTime(x, y);
            double exp = solveStupid(x, y);
            if (Math.abs(res - exp) > 1e-9) {
                debug(x, y, res, exp, "iter", iter);
                throw new RuntimeException();
            }
        }
    }

    private static double solveStupid(int[] x, int[] y) {
        double[] energy = new double[x.length];
        return recurStupid(1 << 0, energy, 0, x, y);
    }

    private static double recurStupid(final int mask, final double[] energy, final double time, int[] x, int[] y) {
        if (mask == (1 << x.length) - 1) return time;
        double res = Double.POSITIVE_INFINITY;
        for (int i = 0; i < x.length; i++) {
            if (mask << 31 - i >= 0) {
                for (int j = 0; j < x.length; j++) {
                    if (mask << 31 - j < 0) {
                        double use = Math.hypot(x[i] - x[j], y[i] - y[j]);
                        double[] nEnergy = energy.clone();
                        double plus = 0;
                        if (energy[j] < use) {
                            plus = use - energy[j];
                            for (int k = 0; k < x.length; k++) {
                                if (mask << 31 - k < 0)
                                    nEnergy[k] += plus;
                            }
                        }
                        nEnergy[j] -= use;
                        double cur = recurStupid(mask | (1 << i), nEnergy, time + plus, x, y);
                        res = Math.min(res, cur);
                    }
                }
            }
        }
        return res;
    }
}
