package on2013_05.on2013_05_19_TopCoder_SRM__579.TravellingPurchasingMan;


import java.util.Arrays;
import java.util.Scanner;
public class TravellingPurchasingMan {
    int INF = Integer.MAX_VALUE / 2;
    public int maxStores(int N, String[] interestingStores, String[] roads) {
        int M = interestingStores.length;
        int[] open = new int[M], close = new int[M], duration = new int[M];
        for (int i = 0; i < M; i++) {
            Scanner in = new Scanner(interestingStores[i]);
            open[i] = in.nextInt(); close[i] = in.nextInt(); duration[i] = in.nextInt();
        }
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) Arrays.fill(dist[i], INF);
        for (int i = 0; i < N; i++) dist[i][i] = 0;
        for (String road : roads) {
            Scanner in = new Scanner(road);
            int a = in.nextInt(), b = in.nextInt(), d = in.nextInt();
            dist[a][b] = dist[b][a] = Math.min(dist[a][b], d);
        }
        for (int k = 0; k < N; k++)
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        int[][] dp = new int[1 << M][N];
        for (int i = 0; i < 1 << M; i++) Arrays.fill(dp[i], INF);
        dp[0][N - 1] = 0;
        for (int vis = 0; vis < 1 << M; vis++)
            for (int last = 0; last < N; last++)
                if (dp[vis][last] < INF) {
                    for (int next = 0; next < M; next++)
                        if (vis << 31 - next >= 0) {
                            int nVis = vis | 1 << next;
                            int reached = dp[vis][last] + dist[last][next];
                            reached = Math.max(reached, open[next]);
                            if (reached <= close[next]) {
                                dp[nVis][next] = Math.min(dp[nVis][next], reached + duration[next]);
                            }
                        }
                }
        int res = 0;
        for (int vis = 0; vis < 1 << M; vis++)
            for (int last = 0; last < N; last++) if (dp[vis][last] < INF) res = Math.max(res, Integer.bitCount(vis));
        return res;
    }
}
