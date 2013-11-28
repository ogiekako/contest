package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Tenka1B1 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
        for (int i = 1; i <= 127; i++) {
            if (i % 3 == a && i % 5 == b && i % 7 == c) out.println(i);
        }
    }
}
