package on2017_07.on2017_07_29_ARC079.TaskE;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        long[] A = new long[N];
        long sum = 0;
        for (int i = 0; i < N; i++) {
            A[i] = in.nextLong();
            sum += A[i];
        }
        out.println(solve(A));
//        for (long K = Math.max(0, sum - 2500); K <= sum; K++) {
//            boolean ok = true;
//            long Y = 0;
//            for (int i = 0; i < N; i++) {
//                long x = (A[i] + K) / (N + 1);
//                if (A[i] + K - N >= K * (N + 1)) {
//                    ok = false;
//                }
//                Y += x;
//            }
//            if (ok && K == Y) {
//                out.println(K);
//                return;
//            }
//        }
//        throw new RuntimeException();
    }

    private long solve(long[] a) {
        Arrays.sort(a);
        int N = a.length;
        if (a[N-1] < N) return 0;
        long d = a[N - 1] / N;
        a[N - 1] -= N * d;
        for (int i = 0; i < N - 1; i++) {
            a[i] += d;
        }
        return solve(a) + d;
    }
}
