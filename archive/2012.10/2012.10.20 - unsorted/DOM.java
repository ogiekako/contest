package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class DOM {
    String forbidden = "CAMBRIDGE";
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        for (char c : s.toCharArray()) if (!forbidden.contains("" + c)) out.print(c);
        out.println();
    }
}
