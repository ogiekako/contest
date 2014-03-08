package on2013_12.on2013_12_08_Recruit_2013.TaskD;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            int n = in.nextInt();
            int[] x = new int[n], y = new int[n], w = new int[n], h = new int[n];
            int[][] cnt = new int[200][200];
            for (int i = 0; i < n; i++) {
                x[i] = in.nextInt();
                y[i] = in.nextInt();
                w[i] = in.nextInt();
                h[i] = in.nextInt();
                for (int j = x[i]; j < x[i] + w[i]; j++) {
                    for (int k = y[i]; k < y[i] + h[i]; k++) {
                        cnt[j][k]++;
                    }
                }
            }
            boolean res = false;
            for (int i = 0; i < n; i++) {
                boolean ok = true;
                for (int j = x[i]; j < x[i] + w[i]; j++) {
                    for (int k = y[i]; k < y[i] + h[i]; k++) {
                        if (cnt[j][k] <= 1) ok = false;
                    }
                }
                if (ok) {
                    res = true;
                }
            }
            out.println(res ? "TRUE" : "FALSE");
        }
    }
}
