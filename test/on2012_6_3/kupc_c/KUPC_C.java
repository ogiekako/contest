package on2012_6_3.kupc_c;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class KUPC_C {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        int[][] boats = new int[K][];
        for (int i = 0; i < K; i++) {
            int m = in.nextInt();
            boats[i] = new int[m];
            for (int j = 0; j < m; j++)
                boats[i][j] = in.nextInt()-1;
        }
        boolean[] res = new boolean[N];
        int R = in.nextInt();
        for (int i = 0; i < R; i++){
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            for (int j = 0; j < K; j++){
                int cnt = 0;
                for(int k:boats[j])if(k==a || k==b)cnt++;
                if(cnt==2){
                    res[a] = res[b] = true;
                }
            }
        }
        int cnt = 0;
        for(boolean b:res)if(b)cnt++;
        out.println(cnt);
    }
}
