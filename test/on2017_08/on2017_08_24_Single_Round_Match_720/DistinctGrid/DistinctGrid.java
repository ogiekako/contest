package on2017_08.on2017_08_24_Single_Round_Match_720.DistinctGrid;



import java.util.Arrays;

public class DistinctGrid {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] findGrid(int n, int k) {
        int[][] res = new int[n][n];
        int p = 0;
        {
            p++;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < k - 1; j++) {
                    res[i][(i + j) % n] = p++;
                }
            }
        }
        int[] res2 = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res2[i * n + j] = res[i][j];
            }
        }
        return res2;
    }
}
