package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '0' || i == s.length() - 1) {
                out.println(s.substring(0, i) + s.substring(i + 1)); return;
            }
    }
}
