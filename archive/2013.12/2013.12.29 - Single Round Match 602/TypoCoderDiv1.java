package src;

import java.util.Arrays;
import java.util.Random;
public class TypoCoderDiv1 {
    public int getmax(int[] D, int X) {
        int n = D.length;
        int[][] dp = new int[n + 1][2200];
        for (int i = 0; i < n + 1; i++) Arrays.fill(dp[i], Integer.MIN_VALUE);
        dp[0][X] = 0;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2200; j++)
                if (dp[i][j] > Integer.MIN_VALUE) {
                    int nj = j + D[i];
                    if (nj >= 2200) {
                        if (i + 1 < n) {
                            int nnj = Math.max(0, nj - D[i + 1]);
                            if (nnj < 2200) {
                                dp[i + 2][nnj] = Math.max(dp[i + 2][nnj], dp[i][j] + 2);
                            }
                        } else {
                            dp[i + 1][0] = Math.max(dp[i + 1][0], dp[i][j] + 1);
                        }
                    } else {
                        dp[i + 1][nj] = Math.max(dp[i + 1][nj], dp[i][j]);
                    }
                    nj = Math.max(j - D[i], 0);
                    dp[i + 1][nj] = Math.max(dp[i + 1][nj], dp[i][j]);
                }
        }
        for (int j = 0; j < 2200; j++) res = Math.max(res, dp[n][j]);
        return res;
    }
    public static void main(String[] args) {
        int[] r = new int[50];
        Random rnd = new Random();
        for (int i = 0; i < r.length; i++) {
            if(rnd.nextBoolean()){
                System.err.print("," + rnd.nextInt(2200) + 1);
            }else{
                System.err.print("," + rnd.nextInt((int) 1e9));
            }
        }
    }
}
