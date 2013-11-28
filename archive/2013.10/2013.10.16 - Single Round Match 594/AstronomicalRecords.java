package src;

import java.util.Arrays;
/*
65537,1
65536,65536
 */
public class AstronomicalRecords {
    public int minimalPlanets(int[] A, int[] B) {
        int N = A.length, M = B.length;
        int[][] dp = new int[N][M];
        for(int i=0;i<N;i++) Arrays.fill(dp[i], 1);
        int res = 0;
        for(int i=0;i<N;i++)for(int j=0;j<M;j++){
            res = Math.max(res, dp[i][j]);
            for(int k=i+1;k<N;k++)for(int l=j+1;l<M;l++){
                if((long)A[i] * B[l] == (long)A[k] * B[j]){
                    dp[k][l] = Math.max(dp[k][l], dp[i][j] + 1);
                }
            }
        }
        return N + M - res;
    }
    public static void main(String[] args) {
        int a = (1<<16) + 1, d = (1<<16);
        int b = 1, c = (1<<16);
        boolean res = (a*d) == (b*c);
        boolean exp = ((long)a*d) == ((long)b*c);
        System.out.println(res + " " + exp);
        System.out.println(a+" "+b+" "+c+" "+d);
    }
}
