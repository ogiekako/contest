package on2012_5_19.favouritedigits;



// Paste me into the FileEdit configuration dialog

public class FavouriteDigits {
   public long findNext(long N, int digit1, int count1, int digit2, int count2) {
		int[] ds = new int[19];
       int dig=0;
       for (int i = 0; i < 18; i++){
           ds[i+1] = (int) (N%10);N/=10;
           if(ds[i+1]>0)dig=i+1;
       }
       boolean[][][][][] dp = new boolean[19][25][25][2][2];
       dp[0][0][0][1][1] = true;
       for (int d = 0; d < 18; d++) for (int c1 = 0; c1 < 22; c1++) for (int c2 = 0; c2 < 22; c2++)
           for (int k = 0; k < 2; k++) for (int z = 0; z < 2; z++)if(dp[d][c1][c2][k][z]){
               for (int v = 0; v < 10; v++){
                   int nk = ds[d+1]<v ? 1 : ds[d+1]==v ? k : 0;
                   int nz = v==0 ? 1 : 0;
                   int nc1 = v==digit1 ? c1+1 : c1;
                   int nc2 = v==digit2 ? c2+1 : c2;
                   dp[d+1][nc1][nc2][nk][nz] = true;
               }
           }
       System.err.println("dig "+dig);
       int D;
       loop:for(D=dig;;D++){
           for (int c1 = count1; c1 < 22; c1++) for (int c2 = count2; c2 < 22; c2++)if(dp[D][c1][c2][1][0]){
               System.err.println(c1+" "+c2 +" ");
              break loop;
           }
       }
       System.err.println("D "+D);
       long res = 0;
       boolean more = false;

       for(int d=D;d>0;d--){
           System.err.println("d more count1 "+d+" "+more+" "+count1);
           int V = 9;
           for (int c1 = Math.max(0,count1); c1 < 22; c1++) for (int c2 = Math.max(0,count2); c2 < 22; c2++)for(int k=0;k<2;k++)if(k==1 || more)for(int z=0;z<2;z++)if((z==0 || d<D) && dp[d][c1][c2][k][z]){
                for(int v=more?0:d==D?Math.max(1,ds[d]):ds[d];v<10;v++){
                    int nc1 = v==digit1 ? c1-1 : c1;
                    int nc2 = v==digit2 ? c2-1 : c2;
                    for(int nk=0;nk<2;nk++)if(nk==1 || more || v>ds[d]){
                        if (nc1 >= 0 && nc2 >= 0 && (dp[d - 1][nc1][nc2][nk][0] || dp[d - 1][nc1][nc2][nk][1])) {
                            V = Math.min(V,v);
                        }
                    }
                }
           }

           res *= 10;
           res += V;
           if (V == digit1) count1--;
           if (V == digit2) count2--;
                            System.err.println("v,ds " + V + " " + ds[d]+" ");
           if (V > ds[d]) more = true;
       }
       return res;
   }


}

