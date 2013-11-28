package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int tc = 1; ; tc++) {
            int n = in.nextInt();
            if (n == 0) return;
            int[] from = new int[n];
            int[] to = new int[n];
            for (int i = 0; i < n; i++) {
                from[i] = in.nextInt();
                to[i] = in.nextInt();
            }
            int[] is = new int[n];
            for (int i = 0; i < n; i++) is[i] = i;
            double res = 0;
            do {
                double left = 0, right = 1500;
                for (int o = 0; o < 50; o++) {
                    double md = (left + right) / 2;
                    double cur = from[is[0]];
                    boolean ok = true;
                    for (int i = 1; i < n; i++) {
                        double nxt = cur + md;
                        if (nxt < from[is[i]]) nxt = from[is[i]];
                        else if (nxt > to[is[i]]) {
                            ok = false; break;
                        }
                        cur = nxt;
                    }
                    if (ok) {
                        left = md;
                    } else {
                        right = md;
                    }
                }
                res = Math.max(res, left);
            } while (Permutation.nextPermutation(is));
            int time = (int) Math.round(res * 60);
            int min = time / 60;
            int sec = time % 60;
            out.printf("Case %d: %d:%02d\n", tc, min, sec);
        }
    }

}
