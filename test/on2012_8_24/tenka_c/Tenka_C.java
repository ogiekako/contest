package on2012_8_24.tenka_c;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class Tenka_C {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        a = new int[N];b=new int[N];c=new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = in.nextInt(); b[i] = in.nextInt() ;c[i] = in.nextInt();
        }
        dp = new int[101][101][30];
        for (int i = 0; i < 101; i++) for (int j = 0; j < 101; j++) for (int k = 0; k < 30; k++) dp[i][j][k] = -1;
        
        int res = calc(100,a[0],0);
        if(res==INF)out.println(-1);
        else out.println(res);
    }

    int INF = 1<<28;
    int[][][] dp;
    private int calc(int HP, int op, int p) {
        if(HP <= b[p])return INF;
        if(dp[HP][op][p]>=0)return dp[HP][op][p];
        int res = INF;
        // C
        if(b[p] < 50)
            res = Math.min(res, 1 + calc(HP-b[p]+50, Math.min(op + c[p], a[p]), p));
        // B
//        for(int b=0;)
        return 0;
    }

    int[] a,b,c;
}
