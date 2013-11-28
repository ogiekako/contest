package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long cnt = 0;
        for (int i = 0; i < n; i++) {
            in.nextInt();
            if (in.nextChar() == '(') cnt += in.nextInt();
            else cnt -= in.nextInt();
            if (cnt == 0) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
    }
}
