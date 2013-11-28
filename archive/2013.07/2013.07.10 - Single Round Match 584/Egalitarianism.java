package src;

public class Egalitarianism {
    public int maxDifference(String[] isFriend, int d) {
        int n=isFriend.length;
        int[][] g = new int[n][n];
        for(int i=0;i<n;i++)for(int j=0;j<n;j++)g[i][j] = 1000;
        for(int i=0;i<n;i++)for(int j=0;j<n;j++)g[i][j] = isFriend[i].charAt(j)=='Y' ? 1 : 1000;
        for(int k=0;k<n;k++)for(int i=0;i<n;i++)for(int j=0;j<n;j++)g[i][j] = Math.min(g[i][j],g[i][k]+g[k][j]);
        for(int i=0;i<n;i++)for(int j=0;j<i;j++)if(g[i][j] >= 1000)return -1;
        int res=0;
        for(int i=0;i<n;i++)for(int j=0;j<i;j++)res=Math.max(res,g[i][j]);
        return res*d;
    }
}
