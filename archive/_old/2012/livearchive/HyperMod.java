package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtilsMinor;

import java.io.PrintWriter;

public class HyperMod {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        out.printf("Case #%d: ", testNumber);
        int mod = in.nextInt();
        int n = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        long res = MathUtilsMinor.hyperexponentiation(n, a, b, mod);
        out.println(res);
    }
}
