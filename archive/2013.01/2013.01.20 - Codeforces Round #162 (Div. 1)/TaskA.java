package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        int l = 0, r = s.length() - 1;
        int[] res = new int[s.length()];
        for (int i = 0; i < res.length; i++) {
            if (s.charAt(i) == 'l') res[r--] = i;
            else res[l++] = i;
        }
        for (int i = 0; i < res.length; i++) {
            out.println(res[i] + 1);
        }
    }
}
