package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class JACK {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        int res = 0;
        int[] xs = new int[N];
        for (int i = 0; i < N; i++) {
            xs[i] = in.nextInt();
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < i; j++)
                for (int k = 0; k < j; k++) {
                    int sum = xs[i] + xs[j] + xs[k];
                    if (sum <= M) res = Math.max(res, sum);
                }
        out.println(res);
    }
}
