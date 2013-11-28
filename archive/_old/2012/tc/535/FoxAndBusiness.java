package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class FoxAndBusiness {
    public double minimumCost(int K, int totalWork, int[] a, int[] p) {
        double lb = 0, ub = Long.MAX_VALUE;
        for (int iteration = 0; iteration < 200; iteration++) {
            double M = (lb + ub) / 2;
            double[] values = new double[a.length];
            for (int i = 0; i < a.length; i++) {
                values[i] = M * a[i] - totalWork * (3600.0 + (double) a[i] * p[i]);
            }
            Arrays.sort(values);
            double sum = 0;
            for (int i = a.length - 1; i >= a.length - K; i--) {
                sum += values[i];
            }
            if (sum > 0) {
                ub = M;
            } else {
                lb = M;
            }
        }
        return ub;
    }
}

