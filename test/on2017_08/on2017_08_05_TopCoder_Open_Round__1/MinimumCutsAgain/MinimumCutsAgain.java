package on2017_08.on2017_08_05_TopCoder_Open_Round__1.MinimumCutsAgain;



import java.util.Arrays;

public class MinimumCutsAgain {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] construct(int n) {
        int c = 1;
        int s = 0, t = 0;
        int lst = 19;
        int m = 0;
        while (1 << m <= n) m++;
        int[][] graph = new int[20][20];
        for (int i = m - 1; i >= 0; i--) {
            // x 2
            if (s > 0) {
                graph[s][lst] = graph[lst][t] = 1;
                c++;
                lst--;
            }

            // + 1
            if (((1 << i) & n) > 0) {
                graph[s + 1][s] = c;
                s++;
            }
        }
        // [1, s) -> [2, s + 1). s -> 0, t -> 1, [lst, n) -> [s + 1, ..)
        int e = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (graph[i][j] > 0) e++;
            }
        }
        int[] res = new int[1 + 3 * e];
        res[0] = (19 - lst) + s + 1;
        e = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (graph[i][j] > 0) {
                    int ii = i == s ? 0 : i == t ? 1 : (1 <= i && i < s) ? i + 1 : i + (s - lst);
                    int jj = j == s ? 0 : j == t ? 1 : (1 <= j && j < s) ? j + 1 : j + (s - lst);
                    res[1 + e * 3] = ii;
                    res[2 + e * 3] = jj;
                    res[3 + e * 3] = graph[i][j];
                    e++;
                }
            }
        }
        return res;
    }
}
