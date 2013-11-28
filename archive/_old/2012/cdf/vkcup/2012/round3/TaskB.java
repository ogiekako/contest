package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int la = in.nextInt(), lb = in.nextInt();
        int[] as = new int[la * 2 + 1];
        int[] bs = new int[lb];
        for (int i = 0; i < la; i++) {
            as[i] = as[i + la] = in.nextInt();
        }
        for (int i = 0; i < lb; i++) bs[i] = in.nextInt();
        int[] revB = new int[1000010];
        Arrays.fill(revB, -1);
        for (int i = 0; i < lb; i++) revB[bs[i]] = i;
        int res = 0;
        for (int s = 0; s < as.length; ) {
            while (s < as.length && revB[as[s]] == -1) s++;
            if (s >= as.length) break;
            int t = s;
            int sum = 0;
            while (t < as.length && revB[as[t]] != -1) {
                if (sum >= lb) {
                    s++;
                    int sub = revB[as[s]] - revB[as[s - 1]];
                    if (sub <= 0) sub += lb;
                    sum -= sub;
                } else {
                    t++;
                    int add = revB[as[t]] - revB[as[t - 1]];
                    if (add <= 0) add += lb;
                    sum += add;
                    res = Math.max(res, t - s);
                }
            }
            s = t;
        }
        out.println(res);
    }
}
