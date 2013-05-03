package on2012_6_3.kingdomanddice;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class KingdomAndDice {
    void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
    }

   public double newFairness(int[] firstDie, int[] secondDie, int X) {
		int N = firstDie.length;
       int[] cnt = new int[N+1];
       Arrays.sort(firstDie);
       Arrays.sort(secondDie);
       cnt[0] = 100;
       for(int i=0;i<N-1;i++){
           int val = secondDie[i+1] - secondDie[i] - 1;
           for(int k:firstDie){
               if(secondDie[i] < k && k<secondDie[i+1])val--;
           }
           cnt[i+1] = val;
       }
       cnt[N] = X - secondDie[N-1];
       for(int k:firstDie)if(secondDie[N-1] < k)cnt[N]--;
       int obj = N*N;
       int win = 0;
       for(int k:firstDie){
           for(int l:secondDie)if(l < k){
               win++;
               obj-=2;
           }
       }
       int Z = 0;
       for(int k:firstDie)if(k==0)Z++;
       boolean[][][] dp = new boolean[2][Z+1][2510];
       dp[0][0][0] = true;
       int cur = 0, nxt = 1;
       for (int i = 0; i < N + 1; i++){
           for (int j = 0; j < Z + 1; j++)Arrays.fill(dp[nxt][j],false);
           for (int j = 0; j < Z + 1; j++) for (int k = 0; k < 2505; k++)if(dp[cur][j][k]){
               for (int l = 0; l <= Z - j && l <= cnt[i]; l++) {
                   int nk = k + l * i;
                   dp[nxt][j + l][nk] = true;
               }
           }
           int tmp = cur;cur=nxt;nxt=tmp;
       }
        for(int i=0;;i++){
            int t = obj - i;
            if(t<2510 && t>=0 && t%2==0 && dp[cur][Z][t/2]){
                debug(t);
                return (double)(win + t/2)/N/N;
            }
            t = obj + i;
            if(t<2510 && t>=0 && t%2==0 && dp[cur][Z][t/2]){
                debug(t);
                return (double)(win + t/2)/N/N;
            }
        }
   }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++){
            System.err.print((i+2) * 100 + ",");
        }
    }

}

