package on2012_8_22.arc008d;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class ARC008D {
    static void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        in.nextLong();
        int M = in.nextInt();
        TreeSet<Long> set = new TreeSet<Long>();
        long[] is = new long[M];
        double[] qa = new double[M], qb = new double[M];
        for (int i = 0; i < M; i++) {
            is[i] = in.nextLong(); qa[i] = in.nextDouble(); qb[i] = in.nextDouble();
            set.add(is[i]);
        }
        long[] appeared = tols(set.toArray(new Long[0]));
        int N = appeared.length;
        double[] as = new double[N], bs = new double[N];
        Arrays.fill(as, 1);
        int sq = (int)Math.round(Math.sqrt(N));
        double[] X = new double[sq + 2];// 0, 1-sq, ...
        Arrays.fill(X, 1);
        double[] A = new double[sq + 2];
        Arrays.fill(A, 1);
        double min = 1, max = 1;
        for (int i = 0; i < M; i++){
            int p = Arrays.binarySearch(appeared, is[i]);
            as[p] = qa[i]; bs[p] = qb[i];
            int q = p/sq+1;
            double x = X[q-1];
            A[q] = 1;
            for(int j=0;j<sq && (q-1)*sq+j < N;j++){
                x = x * as[(q-1)*sq+j] + bs[(q-1)*sq+j];
                A[q] *= as[(q-1)*sq+j];
            }
            double a = 1;
            for(int j=q+1;j<X.length;j++){
                a *= A[j];
                X[j] -= (X[q] - x) * a;
            }
            X[q] = x;
            min = Math.min(min,X[X.length-1]);
            max = Math.max(max,X[X.length-1]);
        }
        out.printf("%.12f\n%.12f\n",min,max);
    }

    private long[] tols(Long[] Ls) {
        long[] ls = new long[Ls.length];
        for (int i = 0; i < ls.length; i++)ls[i] = Ls[i];
        return ls;
    }
}
