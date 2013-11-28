package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtilsMinor;

import java.io.PrintWriter;

public class Euler188 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int a = in.nextInt(), n = in.nextInt();
        int mod = in.nextInt();
        int res = (int) MathUtilsMinor.tetration(a, n, mod);
        out.println(res);
    }
}
