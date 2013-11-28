package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.TreeSet;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        double res = 0;
        long B = in.nextLong();
        int N = in.nextInt();
        long[] is = new long[37];
        for (int i = 0; i < N; i++) is[i] = in.nextLong();
        N = 37;
        Arrays.sort(is);
        long A = 0;
//        for(long i:is)B += i;
        long K = 0;

        TreeSet<Long> cands = new TreeSet<Long>();
        for(int i=0;i<N;i++)for(int d=-1;d<=1;d++)cands.add(is[i] + d);

        for (int n = 1; n <= N; n++) {
            if (n > 0) {
                A += is[n - 1];
                K = is[n - 1];
            }

            long left=K, right= (long) (1.05 * 1e14);
            do{
                long m1 = (left * 2 + right) / 3;
                long m2 = (left + right * 2) / 3;
                double e1 = calc(n,m1,A,N,B,is);
                double e2 = calc(n,m2,A,N,B,is);
                if(e1 < e2)left = m1;
                else right = m2;
            }while(right - left > 3);

            for(long k=left;k<=right;k++){
                res = Math.max(res, calc(n,k,A,N,B,is));
            }
        }
        out.printFormat("Case #%d: %.09f\n", testNumber, res);
    }

    double calc(int n,long k,long A,int N,long B,long[] is)   {
        long X = k * n - A;
        long Y = 0;
        for (int i = n; i < N; i++) Y += Math.max(0, k + 1 - is[i]);
        if(X+Y > B)return -1;
        return (double) 36 / n * X - (X + Y);
    }


    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
}
