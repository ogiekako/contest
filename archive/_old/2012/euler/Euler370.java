package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.Cast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Euler370 {
    private int dmp;

    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        int MX = (int) Math.sqrt(N + 10) + 10;
        int[] factorTable = MathUtils.generateDivisorTable(MX);
        double phi = (Math.sqrt(5) + 1) / 2;

        long res = 0;
        for (int m = 1; 3L * m * m <= N; m++) {
            long upTo = (long) (phi * m);
            List<Integer> primeList = new ArrayList<Integer>();
            int tmp = m;
            while (tmp > 1) {
                int f = factorTable[tmp];
                primeList.add(f);
                while (tmp % f == 0) tmp /= f;
            }
            if (tmp > 1) {
                primeList.add(tmp);
            }
            int[] primes = Cast.toInt(primeList);

            for (long from = m; from <= upTo; ) {
                long k = N / ((long) m * m + m * from + from * from);
                long to = (long) (Math.sqrt((double) N / k - 3.0 / 4.0 * m * m) - m / 2.0);
                to = Math.min(to, upTo);

                res += count(from, to, primes) * k;

                from = to + 1;
            }
        }
        out.println(res);
    }

    private long count(long from, long to, int[] primes) {
        return count(to, primes) - count(from - 1, primes);
    }

    private long count(long to, int[] primes) {
        int n = primes.length;
        long res = 0;
        for (int i = 0; i < 1 << n; i++) {
            long num = 1;
            for (int j = 0; j < n; j++)
                if ((i >> j & 1) == 1) {
                    num *= primes[j];
                }
            if ((Integer.bitCount(i) & 1) == 0) {
                res += to / num;
            } else {
                res -= to / num;
            }
        }
        return res;
    }
}
