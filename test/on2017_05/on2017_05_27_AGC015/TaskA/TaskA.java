package on2017_05.on2017_05_27_AGC015.TaskA;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        long A = in.nextInt(), B = in.nextInt();
        if (A > B) {
            out.println(0);
            return;
        }
        if (A == B) {
            out.println(1);
            return;
        }
        if (N == 1) {
            out.println(0);
            return;
        }
        out.println(A + B * (N - 1) - A * (N - 1) - B + 1);
    }
}
