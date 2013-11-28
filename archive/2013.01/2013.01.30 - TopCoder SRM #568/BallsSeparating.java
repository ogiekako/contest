package tmp;

import java.util.Arrays;

public class BallsSeparating {
    public int minOperations(int[] red, int[] green, int[] blue) {
        int[] dp = new int[8];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < red.length; i++) {
            int[] nDp = new int[8];
            Arrays.fill(nDp, Integer.MAX_VALUE);
            for (int j = 0; j < 8; j++)
                if (dp[j] < Integer.MAX_VALUE) {
                    nDp[j | 1] = Math.min(nDp[j | 1], dp[j] + green[i] + blue[i]);
                    nDp[j | 2] = Math.min(nDp[j | 2], dp[j] + red[i] + blue[i]);
                    nDp[j | 4] = Math.min(nDp[j | 4], dp[j] + green[i] + red[i]);
                }
            dp = nDp;
        }
        return dp[7] == Integer.MAX_VALUE ? -1 : dp[7];
    }

}
