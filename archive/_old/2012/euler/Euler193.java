package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler193 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        long res = MathUtils.squareFreeCount(N);
        out.println(res);
    }
}
