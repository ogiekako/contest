package on2018_06.on2018_06_15_2018_TopCoder_Open_Algorithm.SubarrayAverages;



import java.util.Arrays;

public class SubarrayAverages {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double[] findBest(int[] arr) {
        double[] res = new double[arr.length];
        for (int i = 0; i < arr.length; ) {
            double sum = 0;
            double best = Double.POSITIVE_INFINITY;
            int bestI = -1;
            for (int j = i + 1; j < arr.length + 1; j++) {
                sum += arr[j - 1];
                double cur = sum / (j - i);
                if (best > cur) {
                    best = cur;
                    bestI = j;
                }
            }
            for (int j = i; j < bestI; j++) {
                res[j] = best;
            }
            i = bestI;
        }
        return res;
    }
}
