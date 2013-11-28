package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.BitSet;

/*
偶数だけで見ると,
1,6,17,23,29,31,72,95,134,
奇数は,
1,3,3,0,0,0,0,0,0,0
とりあえず,7以上は,解なしと考えよう.
*/
public class Euler331 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long res = 0;
        for (int i = 0; i < n; i++) {
            int v = in.nextInt();
            System.err.println(v);
            res += solve(v);
        }
        out.println(res);
    }


    private long solve(int n) {
        if ((n & 1) == 1) {
            if (n == 1) return 1;
            if (n <= 5) return 3;
            return 0;
        }
        BitSet rowPar = new BitSet();
        for (int x = 0, y0 = n - 1, y1 = n - 1; x < n; x++) {
            while (y0 >= 0 && withIn(x, y0, n)) y0--;
            while (!withIn(x, y1, n)) y1--;
            if (MathUtils.odd(y1 - y0)) rowPar.set(x);
        }

        long R = rowPar.cardinality();
        long res = 2 * R * (n - R);

        for (int x = 0, y0 = n - 1, y1 = n - 1; x < n; x++) {
            while (y0 >= 0 && withIn(x, y0, n)) y0--;
            while (!withIn(x, y1, n)) y1--;
            for (int y = y1; y > y0; y--) {
                if (rowPar.get(x) ^ rowPar.get(y)) {
                    res--;
                } else {
                    res++;
                }
            }
        }

        return res;
    }

    private static boolean withIn(long x, long y, long n) {
        return (n - 1) * (n - 1) <= x * x + y * y && x * x + y * y < n * n;
    }

    private void show(boolean[][] bs) {
        for (boolean[] b : bs) {
            show(b);
        }
    }

    private void show(boolean[] bs) {
        for (boolean b : bs) {
            System.err.print(b ? 1 : 0);
        }
        System.err.println();
    }
}
