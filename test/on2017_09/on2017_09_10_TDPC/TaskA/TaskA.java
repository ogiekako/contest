package on2017_09.on2017_09_10_TDPC.TaskA;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        boolean[] dp = new boolean[10010];
        dp[0] = true;
        for (int i = 0; i < N; i++) {
            int p = in.nextInt();
            for (int j = dp.length - 1; j >= 0; j--) {
                if (dp[j]) dp[j + p] = true;
            }
        }
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i]) res++;
        }
        out.println(res);
    }
}
