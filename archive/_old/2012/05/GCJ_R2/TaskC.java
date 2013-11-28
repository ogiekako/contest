package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] x = new int[N];
        for (int i = 0; i < x.length; i++) x[i] = in.nextInt();

        out.printf("Case #%d: ", testNumber);

        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++) {
                if (i < x[j] && x[j] < x[i]) {
                    out.println("Impossible"); return;
                }
            }

        int[] res = solve(x, 0, N, 0);
        for (int i = 0; i < res.length; i++) {
            out.print(" "); out.print(res[i]);
        }
        out.println();
    }
    int MX = (int) 1e9;
    private int[] solve(int[] x, int left, int right, int d) {
        int ni = x[left];
        if (ni < right) {
            int[] ll = solve(x, left, ni + 1, d);
            int[] rr = solve(x, ni, right, d);
            int[] res = new int[right - left];
            for (int i = 0; i < ll.length; i++) res[i] = ll[i];
            for (int i = 1; i < rr.length; i++) {
                res[ll.length + i - 1] = rr[i] + ll[ll.length - 1];
            }
            return res;
        }
        int[] md = solve(x, left + 1, right - 1, d + 1);

    }
}
