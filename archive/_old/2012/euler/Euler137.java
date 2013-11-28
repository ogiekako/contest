package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Euler137 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        ArrayList<Long> list = new ArrayList<Long>();
        for (long np = 1; np <= 1000000; np++) {
            {
                long D = 5 * np * np + 4;
                if (MathUtils.isSquare(D)) {
                    long d = MathUtils.sqrt(D);
                    long b = np + d;
                    if (b % 2 == 0) {
                        long mp = b / 2;
                        long n = mp * np;
                        if (n % 2 == 0)
                            list.add(n);
                    }
                }
            }
            {
                long D = 5 * np * np - 1;
                if (MathUtils.isSquare(D)) {
                    long d = MathUtils.sqrt(D);
                    long mp = 2 * np + d;
                    long n = (mp + np) * (mp - np);
                    if (n % 2 == 1)
                        list.add(n);
                }
            }
        }
        System.err.println(list);
        Collections.sort(list);
        out.println(list.get(N - 1));
    }
}
