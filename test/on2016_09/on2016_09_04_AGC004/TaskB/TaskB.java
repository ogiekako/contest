package on2016_09.on2016_09_04_AGC004.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long res = Long.MAX_VALUE;
        int N = in.nextInt();
        int x = in.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = in.nextInt();
        long[] min = new long[N];
        Arrays.fill(min, Long.MAX_VALUE);
        for (int i = 0; i < N; i++) {
            long val = 0;
            for (int j = 0; j < N; j++) {
                min[j] = Math.min(min[j], a[(i-j+N)%N]);
                val += min[j];
            }
            res = Math.min(res, val + (long) i * x);
        }
        out.println(res);
    }
}
