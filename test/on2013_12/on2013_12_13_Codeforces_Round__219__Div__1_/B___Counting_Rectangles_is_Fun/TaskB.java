package on2013_12.on2013_12_13_Codeforces_Round__219__Div__1_.B___Counting_Rectangles_is_Fun;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt(), q = in.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            char[] cs = in.next().toCharArray();
            for (int j = 0; j < m; j++) {
                map[i][j] = cs[j] - '0';
            }
        }
        int[][] S = sum(map);
        int[][][][] M = new int[n+1][m+1][n+1][m+1];
        for(int i=0;i<n;i++)for(int j=0;j<m;j++)for(int k=i+1;k<=n;k++)for(int l=j+1;l<=m;l++){
            M[i][j][k][l] = S[k][l] - S[i][l] - S[k][j] + S[i][j] == 0 ? 1 : 0;
        }
        for(int i=0;i<n;i++)for(int j=0;j<m;j++)for(int k=i+1;k<=n;k++)for(int l=j+1;l<=m;l++){
            M[i][j][k][l] += M[i][j][k][l-1] + M[i][j][k-1][l] - M[i][j][k-1][l-1];
        }
        for(int k=1;k<=n;k++)for(int l=1;l<=m;l++)for(int i=k-1;i>=0;i--)for(int j=l-1;j>=0;j--){
            M[i][j][k][l] += M[i+1][j][k][l] + M[i][j+1][k][l] - M[i+1][j+1][k][l];
        }
        for (int i = 0; i < q; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            int c = in.nextInt() , d = in.nextInt();
            out.println(M[a][b][c][d]);
        }
    }
    private int[][] sum(int[][] map) {
        int n = map.length, m = map[0].length;
        int[][] S = new int[n+1][m+1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                S[i+1][j+1] = S[i+1][j] + S[i][j+1] - S[i][j] + map[i][j];
            }
        }
        return S;
    }
}
