package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        String bef = null;
        int res = 1;
        for (int i = 0; i < n; i++) {
            String cur = in.next();
            if(bef != null && !bef.equals(cur))res++;
            bef = cur;
        }
        out.println(res);
    }
}
