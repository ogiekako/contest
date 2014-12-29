package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC2 {
    int MOD = 2014122419;

    void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while(T-->0) {
            int N = in.nextInt(), M = in.nextInt();
//            debug(N,M);
            long[][] A = new long[N][N];
            int res = 0;
            for (int i = 0; i < M; i++) {
                int a  = in.nextInt()-1,b=in.nextInt()-1;
                if(a==b)res++;
//                debug(a,b);
//                A[a][b]++;
            }
//            int exp = 0;
//            int exp2 = 0;
//            for (int i = 0; i < N; i++) {
//                if (A[i][i] == 1)exp++;
//                exp2 += A[i][i];
//            }
//            long[][] B = Matrix.powered(A, MOD, MOD);
//            long res = 0;
//            for (int i = 0; i < B.length; i++) {
//                res += B[i][i];
//                if (res >= MOD)res-=MOD;
////                debug(B[i]);
//            }

//            long[] x = new long[N];
//            x[0] = 1;
//            long res = Matrix.powered(A, MOD, x, MOD)[0];
            out.println(res);
        }
    }
}
