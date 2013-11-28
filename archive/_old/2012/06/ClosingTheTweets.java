package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class ClosingTheTweets {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        boolean[] bs = new boolean[N];
        int res = 0;
        for (int i = 0; i < K; i++) {
            if (in.next().equals("CLICK")) {
                int j = in.nextInt() - 1;
                bs[j] = !bs[j];
                if (bs[j]) res++;
                else res--;
                out.println(res);
            } else {
                res = 0;
                Arrays.fill(bs, false);
                out.println(res);
            }
        }
    }
}
