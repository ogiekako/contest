package on2017_07.on2017_07_29_ARC079.TaskA;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        int M = in.nextInt();
        boolean[] have = new boolean[N];
        for (int i = 0; i < M; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            if (a == 0) {
                if (have[b]) {
                    out.println("POSSIBLE");
                    return;
                }
                have[b] = true;
            }
            if (b == N - 1) {
                if (have[a]) {
                    out.println("POSSIBLE");
                    return;
                }
                have[a] = true;
            }
        }
        out.println("IMPOSSIBLE");
    }
}
