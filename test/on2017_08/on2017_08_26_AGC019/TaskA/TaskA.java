package on2017_08.on2017_08_26_AGC019.TaskA;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskA {

    long min(long... a) {
        long res = a[0];
        for (long b : a) res = Math.min(res, b);
        return res;
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long[] a = new long[4];
        for (int i = 0; i < 4; i++) {
            a[i] = in.nextInt();
        }
        long N = in.nextInt() * 4L;
        long res =
                N / 8 * min(8 * a[0], 4 * a[1], 2 * a[2], 1 * a[3]) +
                        N % 8 / 4 * min(4 * a[0], 2 * a[1], 1 * a[2]) +
                        N % 4 / 2 * min(2 * a[0], 1 * a[1]) +
                        N % 2 / 1 * min(1 * a[0]);
        out.println(res);
    }
}
