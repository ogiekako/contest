package on_2012.on2012_6_1.taskd;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[][] A = read(in, N);
        long[][] B = read(in, N);
        long[][] C = read(in, N);
        boolean res = true;
        Random rnd = new Random(120489124L);
        for (int r = 0; r < 30; r++) {
            long[] v = new long[N];
            for (int i = 0; i < N; i++) v[i] = rnd.nextBoolean() ? 1 : 0;
            long[] ABv = mul(A, mul(B, v));
            long[] Cv = mul(C, v);
            if (!Arrays.equals(ABv, Cv)) {
                res = false;
                break;
            }
        }
        out.println(res ? "YES" : "NO");
    }

    private long[] mul(long[][] A, long[] v) {
        long[] Av = new long[A.length];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[i].length; j++) Av[i] += A[i][j] * v[j];
        return Av;
    }

    private long[][] read(MyScanner in, int N) {
        long[][] A = new long[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) A[i][j] = in.nextInt();
        return A;
    }
}
