package on2015_08.on2015_08_22_TopCoder_Open_Round__2C.YetAnotherCardGame;



import java.util.Arrays;

public class YetAnotherCardGame {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maxCards(int[] petr, int[] snuke) {
        int[] dp = new int[101];
        for (int i = 0; i < petr.length; i++) {
            for (int j = dp.length-1; j >= 0; j--) {
                for(int k:petr)if(j<k){
                    dp[k]=Math.max(dp[k],dp[j]+1);
                }
            }
            for (int j = dp.length-1; j >= 0; j--) {
                for(int k:snuke)if(j<k){
                    dp[k]=Math.max(dp[k],dp[j]+1);
                }
            }
        }
        int res = 0;
        for(int d:dp)res=Math.max(res,d);
        return res;
    }
}
