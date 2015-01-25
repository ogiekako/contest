package src;

import java.util.Arrays;

public class CtuRobots {
    public double maxDist(int B, int[] cost, int[] cap) {
        long[] ls = new long[cost.length];
        for (int i = 0; i < ls.length; i++) {
            ls[i] = (long) cap[i] << 32 | cost[i];
        }
        Arrays.sort(ls);
        for (int i = 0; i < ls.length; i++) {
            cap[i] = (int) (ls[i] >> 32);
            cost[i] = (int) (ls[i] & (1L << 32) - 1);
        }
        int N = cost.length;
        double[] dp = new double[B + 1];
        double res = 0;
        for (int i=0;i<N;i++){
            for(int j=B-cost[i];j>=0;j--){
                res = Math.max(res, (dp[j] + cap[i])/2);
                dp[j+cost[i]] = Math.max(dp[j+cost[i]], (dp[j] + cap[i]) / 3);
            }
        }
        return res;
    }
}
