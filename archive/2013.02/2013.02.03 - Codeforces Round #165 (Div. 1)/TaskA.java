package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        long res = 0;
        for (int i = 0; i < n; i++) {
            long k = in.nextLong(), a = in.nextLong();
            for (long j = 1; j < a; j *= 4) k++;
            if (a == 1) k++;
            res = Math.max(res, k);
        }
        out.println(res);
    }
}
