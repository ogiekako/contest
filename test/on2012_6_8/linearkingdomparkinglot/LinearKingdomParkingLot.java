package on2012_6_8.linearkingdomparkinglot;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class LinearKingdomParkingLot {
   public int borrowKeys(int[] exitOrder) {
       int n = exitOrder.length;
		int[][][] dp = new int[n+1][n+1][n+1];
       for (int i = 0; i < n + 1; i++) for (int j = 0; j < n + 1; j++) Arrays.fill(dp[i][j],1<<30);
       dp[0][n][n] = 0;
       for(int i=0;i<n;i++){
           int e = exitOrder[i];
           for (int j = 0; j < n + 1; j++) for (int k = 0; k < n + 1; k++)if(dp[i][j][k] < 1<<30){
               int add = j < e ? 1 : 0;
               dp[i + 1][Math.min(j, e)][k] = Math.min(dp[i + 1][Math.min(j, e)][k], dp[i][j][k] + add);
               add = k < e ? 1 : 0;
               dp[i + 1][j][Math.min(k,e)] = Math.min(dp[i + 1][j][Math.min(k,e)], dp[i][j][k] + add);
           }
       }
       int res = Integer.MAX_VALUE;
       for (int i = 0; i < n + 1; i++) for (int j = 0; j < n + 1; j++) res = Math.min(res, dp[n][i][j]);
       return res;
   }


}

