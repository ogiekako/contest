package on2015_08.on2015_08_29_TopCoder_SRM__635.SimilarRatingGraph;



import java.util.Arrays;

public class SimilarRatingGraph {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double maxLength(int[] date, int[] rating) {
        int n = date.length - 1;
        long[] dx = new long[n], dy = new long[n];
        for (int i = 0; i < n; i++) {
            dx[i] = date[i + 1] - date[i];
            dy[i] = rating[i + 1] - rating[i];
        }
        double res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                double sum = 0;
                for (int k = 0; i + k < n && j + k < n; k++) {
                    if (dx[i + k] * dy[j + k] != dy[i + k] * dx[j + k]) break;
                    if (dx[i + k] * dx[j] != dx[i] * dx[j + k]) break;
                    sum += Math.sqrt(dx[i + k] * dx[i + k] + dy[i + k] * dy[i + k]);
                }
                res = Math.max(res, sum);
            }
        }
        return res;
    }
}
