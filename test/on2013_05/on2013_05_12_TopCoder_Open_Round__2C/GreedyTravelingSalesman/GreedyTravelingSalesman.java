package on2013_05.on2013_05_12_TopCoder_Open_Round__2C.GreedyTravelingSalesman;


import java.util.Set;
import java.util.TreeSet;
public class GreedyTravelingSalesman {
    public int worstDistance(String[] thousands, String[] hundreds, String[] tens, String[] ones) {
        int n = thousands.length;
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = 1000 * (thousands[i].charAt(j) - '0') +
                        100 * (hundreds[i].charAt(j) - '0') +
                        10 * (tens[i].charAt(j) - '0') +
                        1 * (ones[i].charAt(j) - '0');
            }
        }
        Set<Integer>[] cands = new Set[n];
        for (int i = 0; i < n; i++) {
            cands[i] = new TreeSet<Integer>();
            cands[i].add(9999);
            for (int j = 0; j < n; j++) {
                for (int d = -1; d <= 1; d++) {
                    int nd = dist[i][j] + d;
                    nd = Math.max(1, nd);
                    nd = Math.min(9999, nd);
                    cands[i].add(nd);

                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i != j) {
                    for (int next : cands[i]) {
                        int prev = dist[i][j];
                        dist[i][j] = next;
                        boolean[] vis = new boolean[n];
                        int cur = 0;
                        vis[cur] = true;
                        int score = 0;
                        for (int iter = 0; iter < n - 1; iter++) {
                            int to = -1;
                            for (int k = 0; k < n; k++)
                                if (!vis[k]) {
                                    if (to == -1 || dist[cur][to] > dist[cur][k]) to = k;
                                }
                            score += dist[cur][to];
                            cur = to;
                            vis[to] = true;
                        }
                        res = Math.max(res, score);

                        dist[i][j] = prev;
                    }
                }
        return res;
    }
}
