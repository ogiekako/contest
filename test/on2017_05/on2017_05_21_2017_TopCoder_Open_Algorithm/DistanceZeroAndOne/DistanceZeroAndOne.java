package on2017_05.on2017_05_21_2017_TopCoder_Open_Algorithm.DistanceZeroAndOne;



import java.util.Arrays;

public class DistanceZeroAndOne {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String[] construct(int[] dist0, int[] dist1) {
        int n = dist0.length;
        char[][] g = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = 'N';
                if (i==j)continue;
                if (Math.abs(dist0[i] - dist0[j]) <= 1 && Math.abs(dist1[i] - dist1[j]) <= 1) g[i][j] = 'Y';
            }
        }
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = g[i][j] == 'Y' ? 1 : 100;
            }
            dist[i][i] = 0;
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if(dist[0][i] != dist0[i] || dist[1][i] != dist1[i]) return new String[0];
        }

        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            res[i] = new String(g[i]);
        }

        return res;
    }
}
