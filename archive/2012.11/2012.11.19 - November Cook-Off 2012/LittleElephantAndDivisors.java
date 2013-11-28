package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class LittleElephantAndDivisors {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int g = 0;
        for (int i = 0; i < n; i++) g = MathUtils.gcd(g, in.nextInt());
        if (g == 1) {
            out.println(-1); return;
        }
        for (int i = 2; i * i <= g; i++)
            if (g % i == 0) {
                out.println(i); return;
            }
        out.println(g);
    }
}
