package on2017_09.on2017_09_09_Fun_SRM_2017_09_08.RewardOnATree;



import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class RewardOnATree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int collect(int[] parent, int[] reward) {
        int N = parent.length + 1;
        int[] depth = new int[N];
        int[] tot = new int[N];
        for (int i = 0; i < N; i++) {
            int dep = 0;
            for (int j = i; j > 0; j = parent[j - 1]) {
                dep++;
            }
            depth[i] = dep;
            tot[dep] += Math.max(0, reward[i]);
        }
        int[][] ids = new int[N][]; // ids per depth
        for (int i = 0; i < N; i++) {
            int cnt = 0;
            for (int j = 0; j < N; j++) {
                if (depth[j] == i) cnt++;
            }
            ids[i] = new int[cnt];
            cnt = 0;
            for (int j = 0; j < N; j++) {
                if (depth[j] == i) ids[i][cnt++] = j;
            }
        }
        int INF = (int) 1e9;
        int[] dp = new int[N];
        Arrays.fill(dp, INF);
        int res = reward[0];
        int total = tot[0];
        dp[0] = Math.max(0, -reward[0]);
        for (int i = 1; i < N; i++) {
            total += tot[i];
            int best = INF;
            for (int j : ids[i]) {
                dp[j] = Math.min(dp[j], dp[parent[j - 1]] + Math.max(0, -reward[j]));
                best = Math.min(best, dp[j]);
            }
            for (int j : ids[i]) {
                dp[j] = Math.min(dp[j], best + Math.max(0, -reward[j]));

                res = Math.max(res, total - dp[j]);
            }
        }
        return res;
    }
}
