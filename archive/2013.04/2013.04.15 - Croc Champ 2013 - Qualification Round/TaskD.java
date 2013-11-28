package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        int[] a = new int[n];
        for (int i = 1; i < n; i++) a[i] = 1;
        for (int i = 0; i < k; i++) {
            int[] r = new int[n];
            for (int j = 0; j < n; j++) {
                if (j > 0 && (j & j - 1) == 0) {
                    if (a[j] < j) {
                        a[j] += a[j];
                        r[j] = j;
                    } else {
                        r[j] = 0;
                    }
                } else {
                    if (j > 0 && j - 1 << 31 - i < 0) {
                        a[j] += a[1 << i];
                        r[j] = 1 << i;
                    } else {
                        r[j] = 0;
                    }
                }
            }
            for (int j = 0; j < n; j++) {
                out.printFormat("%d%c", n - r[n - 1 - j], j == n - 1 ? '\n' : ' ');
            }
        }
    }
}
