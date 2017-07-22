package on2017_07.on2017_07_22_TopCoder_Open_Round__2B.DengklekGaneshAndTree;



import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DengklekGaneshAndTree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);
    public int getCount(String labels, int[] parents) {
        int n = labels.length();
        int[] level = new int[n];
        int L = 0;
        for (int i = 0; i < n - 1; i++) {
            level[i + 1] = level[parents[i]] + 1;
            L = Math.max(L, level[i + 1]);
        }
        L++;
        int[] max = level.clone();
        for (int i = n - 2; i >= 0; i--) {
            if (labels.charAt(i + 1) == labels.charAt(parents[i])) {
                max[parents[i]] = Math.max(max[parents[i]], max[i + 1]);
                max[i + 1] = -1;
            }
        }
        ArrayList<Interval> intervals = new ArrayList<>();
        for (int i=0;i<n;i++) {
            if (max[i] >= 0) {
                intervals.add(new Interval(level[i], max[i] + 1));
            }
        }
        Collections.sort(intervals);

        long[][] dp = new long[2][L + 1];
        dp[0][0] = 1;
        int cur = 0, nxt = 1;
        for (Interval interval : intervals) {
            Arrays.fill(dp[nxt], 0);
            int l = (int) interval.left;
            int r = (int) interval.right;
            for (int i = 0; i < L + 1; i++) {
                dp[nxt][i] += dp[cur][i];
                if (dp[nxt][i] >= MOD) dp[nxt][i] -= MOD;
                if (i >= l) {
                    dp[nxt][Math.max(i, r)] += dp[cur][i];
                    if (dp[nxt][Math.max(i, r)] >= MOD) dp[nxt][Math.max(i, r)] -= MOD;
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        
        return (int) dp[cur][L];
    }

}
