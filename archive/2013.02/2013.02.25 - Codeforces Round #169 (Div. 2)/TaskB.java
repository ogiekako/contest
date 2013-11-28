package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int odd = 0;
        String s = in.next();
        for (char c = 'a'; c <= 'z'; c++) {
            int count = 0;
            for (char d : s.toCharArray()) if (c == d) count++;
            if (count % 2 == 1) odd++;
        }
        out.println(odd == 0 || odd % 2 == 1 ? "First" : "Second");
    }
}
