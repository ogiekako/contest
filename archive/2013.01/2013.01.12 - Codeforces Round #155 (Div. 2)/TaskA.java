package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] first = new int[n];
        int[] second = new int[n];
        int[] last = new int[5001];
        int ptr = 0;
        for (int i = 0; i < n * 2; i++) {
            int k = in.nextInt();
            if (last[k] == 0) {
                last[k] = i + 1;
            } else {
                first[ptr] = last[k];
                second[ptr++] = i + 1;
                last[k] = 0;
            }
        }
        if (ptr != n) {
            out.println(-1);
            return;
        }
        for (int i = 0; i < n; i++) {
            out.println(first[i], second[i]);
        }
    }
}
