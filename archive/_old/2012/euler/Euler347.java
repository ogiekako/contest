package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler347 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] primes = MathUtils.generatePrimes(N);
        long res = 0;
        for (int i = 0; i < primes.length; i++)
            for (int j = i + 1; j < primes.length && primes[i] * primes[j] <= N; j++) {
                long p = primes[i], q = primes[j];
                long max = 0;
                for (long pp = p; pp * q <= N; pp *= p) {
                    for (long qq = q; pp * qq <= N; qq *= q) {
                        long val = pp * qq;
                        max = Math.max(max, val);
                    }
                }
                res += max;
            }
        out.println(res);
    }
}
