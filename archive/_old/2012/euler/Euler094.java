package tmp;

import net.ogiekako.algorithm.math.IntegralTriangle;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler094 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        IntegralTriangle[] ts = IntegralTriangle.almostEquilateralTriangles(N);
        long res = 0;
        for (IntegralTriangle t : ts) {
            if (t.a + t.b + t.c <= N) {
                res += t.a + t.b + t.c;
            }
        }
        out.println(res);
    }
}
