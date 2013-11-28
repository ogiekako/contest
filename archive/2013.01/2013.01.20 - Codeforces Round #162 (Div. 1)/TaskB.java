package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    int T = (int) 1e5 + 10;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        if (n == 1) {
            out.println(1); return;
        }
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        int[] ptr = new int[T];
        for (int i = 2; i < T; i++) {
            for (int j = i; j < T; j += i) {
                ptr[j]++;
            }
        }
        int[][] div = new int[T][];
        for (int i = 0; i < T; i++) {
            div[i] = new int[ptr[i]];
            ptr[i] = 0;
        }
        for (int i = 2; i < T; i++) {
            for (int j = i; j < T; j += i) {
                div[j][ptr[j]++] = i;
            }
        }

        int[] last = new int[T];
        for (int i = 0; i < a.length; i++) {
            int max = 0;
            for (int d : div[a[i]]) {
                max = Math.max(max, last[d]);
            }
            for (int d : div[a[i]]) {
                last[d] = max + 1;
            }
        }
        int res = 0;
        for (int l : last) res = Math.max(res, l);
        out.println(res);
    }
}
