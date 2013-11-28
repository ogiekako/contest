package tmp;

import net.ogiekako.algorithm.math.IntegralTriangle;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler086 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long th = in.nextLong();
        int left = 0, right = 10000000;
        do {
            int M = (left + right) / 2;
            long count = solve(M);
            if (count > th) {
                right = M;
            } else {
                left = M;
            }
        } while (right - left > 1);
        out.println(right);
    }

    private long solve(int M) {
        IntegralTriangle[] ps = IntegralTriangle.generatePrimitivePythagoreans(M * 3);
        long res = 0;
        for (IntegralTriangle p : ps) {
            if (p.a <= M) {
                res += func((int) p.a, (int) p.b, M);
            }
            if (p.b <= M) {
                res += func((int) p.b, (int) p.a, M);
            }
        }
        return res;
    }

    private long func(int z, int xy, int M) {
        int to = M / z;
        long res = 0;
        for (int k = 1; k <= to; k++) {
            int f = (k * xy + 1) / 2;
            int t = Math.min(k * z, k * xy - 1);
            if (f <= t) {
                res += (t - f + 1);
            }
        }
        return res;
    }
}
