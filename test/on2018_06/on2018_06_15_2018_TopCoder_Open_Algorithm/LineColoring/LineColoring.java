package on2018_06.on2018_06_15_2018_TopCoder_Open_Algorithm.LineColoring;



import java.util.Arrays;

public class LineColoring {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minCost(int[] x) {
        int res = Integer.MAX_VALUE;
        if (x.length == 1) res = x[0];
        int max = 0;
        {
            int m1 = 0, m2 = 0;
            for (int i = 0; i < x.length; i++) {
                if (i % 2 == 0) m1 = Math.max(m1, x[i]);
                else m2 = Math.max(m2, x[i]);
            }
            res = Math.min(res, m1 + m2);
            max = Math.max(m1, m2);
        }
        int n = x.length;
        for (int i = 0; i < n; i++) {
            int[] dp = new int[3]; // last -> ?
            // 0: max
            // 1: x[i]
            // 2: ?
            for (int j = 0; j < n; j++) {
                int[] nDp = new int[3];
                Arrays.fill(nDp, Integer.MAX_VALUE);
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (k == l) continue;
                        if (l == 1 && x[j] > x[i]) continue;
                        int nv = l == 2 ? Math.max(dp[k], x[j]) : dp[k];
                        nDp[l] = Math.min(nDp[l], nv);
                    }
                }
                dp = nDp;
            }
            res = Math.min(res, max + x[i] + Math.min(Math.min(dp[0], dp[1]), dp[2]));
        }
        return res;
    }
}
