package on2013_06.on2013_06_21_TopCoder_SRM__583.TravelOnMars;


public class TravelOnMars {
    public int minTimes(int[] range, int startCity, int endCity) {
        int N = range.length;
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) dist[i][j] = Integer.MAX_VALUE / 2;
        for (int i = 0; i < N; i++) dist[i][i] = 0;
        for (int i = 0; i < N; i++) {
            for (int d = -range[i]; d <= range[i]; d++) {
                int j = ((i + d) % N + N) % N;
                dist[i][j] = Math.min(dist[i][j], 1);
            }
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        return dist[startCity][endCity];
    }
}
