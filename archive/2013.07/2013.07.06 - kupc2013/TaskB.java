package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), x = in.nextInt(), m = in.nextInt();
        int[] l = new int[m], r = new int[m], s = new int[m];
        for (int i = 0; i < m; i++) {
            l[i] = in.nextInt(); r[i] = in.nextInt(); s[i] = in.nextInt();

        }
        int[] tens = new int[n + 1];
        for (int i = 0; i < n + 1; i++) tens[i] = i == 0 ? 1 : tens[i - 1] * (x + 1);
        int max = -1;
        int[] res = null;
        for (int i = 0; i < tens[n]; i++) {
            int[] a = new int[n];
            int p = i;
            for (int j = 0; j < n; j++) {
                a[j] =
                       p% (x + 1);
                p /= (x + 1);
            }
            boolean ok = true;
            int cnt = 0;
            for (int b : a) cnt += b;
            int[] S = new int[n + 1];
            for (int j = 0; j < n; j++) S[j + 1] = S[j] + a[j];
            for (int j = 0; j < m; j++) {
                int val = S[r[j]] - S[l[j] - 1];
                if (val != s[j]) ok = false;
            }
            if (ok && max < cnt) {
                max = cnt;
                res = a;
            }
        }
        if (max == -1) {
            out.println(-1);
        } else {
            for (int i = 0; i < n; i++) out.printFormat("%d%c", res[i], i == n - 1 ? '\n' : ' ');
        }
    }
}
