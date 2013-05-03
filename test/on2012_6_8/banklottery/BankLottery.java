package on2012_6_8.banklottery;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class BankLottery {
    static void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }
   public double expectedAmount(int[] accountBalance, int weeklyJackpot, int weekCount) {
		int total = 0;
       for(int a:accountBalance)total += a;
       double[][] dp = new double[weekCount+1][weekCount+1];
        dp[0][0] = 1;
       for (int i = 0; i < weekCount; i++) for (int j = 0; j < weekCount + 1; j++)if(dp[i][j] > 0){
           double prob = (double)(accountBalance[0] + j * weeklyJackpot) / (total+i * weeklyJackpot);
           dp[i+1][j] += dp[i][j] * (1-prob);
           dp[i+1][j+1] += dp[i][j] * prob;
       }

       double res = 0;
       for(int i=0;i<weekCount+1;i++)res += (accountBalance[0] + i * weeklyJackpot) * dp[weekCount][i];
       return res;
   }


}

