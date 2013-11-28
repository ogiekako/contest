package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Dom2012A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        while (n-- > 0) {
            y = in.nextInt(); m = in.nextInt(); d = in.nextInt();
            int res;
            for (res = 0; y < 1000; res++) {
                next();
            }
            out.println(res);
        }
    }

    private void next() {
        d++;
        if (d > 20 || y % 3 != 0 && m % 2 == 0 && d == 20) {
            d = 1;
            addMonth();
        }
    }

    private void addMonth() {
        m++;
        if (m > 10) {
            m = 1;
            y++;
        }
    }

    int y, m, d;
}
