package on2015_08.on2015_08_27_TopCoder_SRM__641.TrianglesContainOrigin;



import java.util.Arrays;

public class TrianglesContainOrigin {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long count(int[] x, int[] y) {
        int n = x.length;
        long res = (long) n * (n-1) * (n-2) / 6;
        for (int i = 0; i < n; i++) {
            long cnt = 0;
            for (int j = 0; j < n; j++) {
                if (x[i] * y[j] - y[i] * x[j] >= 0) cnt++;
            }
            res -= (cnt - 1) * (cnt - 2) / 2;
        }
        return res;
    }
}
