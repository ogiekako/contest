package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class Euler204 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int type = in.nextInt();
        int N = in.nextInt();
        int[] table = MathUtils.generateGeneralizedHammingNumbers(type, N);
        out.println(table.length);
    }

}
