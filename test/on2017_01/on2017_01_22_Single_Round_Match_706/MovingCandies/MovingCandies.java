package on2017_01.on2017_01_22_Single_Round_Match_706.MovingCandies;



import java.util.Arrays;

public class MovingCandies {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minMoved(String[] t) {
        int h = t.length, w = t[0].length();
        int K = 0;
        for(int i=0;i<h;i++)for(int j=0;j<w;j++)if(t[i].charAt(j)=='#')K++;
        if(K==0)return -1;
        int INF = (int) 1e9;
        int[][][] dp = new int[K+1][h][w]; // min .
        for(int i=0;i<K+1;i++)for(int j=0;j<h;j++)Arrays.fill(dp[i][j], INF);
        dp[1][0][0] = t[0].charAt(0) == '.' ? 1 : 0;
        int[] dx={1,0,-1,0}, dy={0,1,0,-1};
        int res=INF;
        for(int k=1;k<K;k++) {
            if(dp[k][h-1][w-1]<INF)res=Math.min(res,dp[k][h-1][w-1]);
            for(int i=0;i<h;i++)for(int j=0;j<w;j++)if(dp[k][i][j] < INF){
                for(int d=0;d<4;d++){
                    int ni=i+dx[d],nj=j+dy[d];
                    if(0<=ni&&ni<h&&0<=nj&&nj<w){
                        dp[k+1][ni][nj] = Math.min(dp[k+1][ni][nj], dp[k][i][j] + (t[ni].charAt(nj)=='.'?1:0));
                    }
                }
            }
        }
        if(dp[K][h-1][w-1]<INF)res=Math.min(res,dp[K][h-1][w-1]);
        return res==INF ? -1 : res;
    }
}
