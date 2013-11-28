package tmp;

import net.ogiekako.algorithm.math.Fibonacci;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler104 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long f0 = 0;
        long f1 = 1;
        long MOD = MathUtils.power(10, 9);
        for (int i = 0; ; i++) {

            long tmp = f0;
            int msk = 0;
            while (tmp > 0) {
                int k = (int) (tmp % 10);
                if (k != 0) {
                    msk |= 1 << k;
                }
                tmp /= 10;
            }
            if (Integer.bitCount(msk) == 9) {
                double logFib = Fibonacci.logFibonacci(i);
                double log10Fib = logFib / Math.log(10);
                int dig = (int) (log10Fib) + 1;
                double power = log10Fib - dig + 9;
                long head = (long) Math.pow(10, power);
                tmp = head;
                msk = 0;
                while (tmp > 0) {
                    int k = (int) (tmp % 10);
                    if (k != 0) {
                        msk |= 1 << k;
                    }
                    tmp /= 10;
                }
                if (Integer.bitCount(msk) == 9) {
                    out.println(i);
                    return;
                }
            }

            tmp = f0 + f1;
            if (tmp >= MOD) tmp -= MOD;
            f0 = f1;
            f1 = tmp;
        }
    }

}
