package net.ogiekako.algorithm.math;

import net.ogiekako.algorithm.utils.Cast;

import java.util.ArrayList;

/**
 * fib[0] = 0, fib[1] = 1
 * fib[i+2] = fib[i+1] + fib[i].
 */
public class Fibonacci {
    /**
     * fib[0] = 0, fib[1] = 1.
     * The last value of the result is the largest value less than or equal to 'upTo'.
     */
    public static long[] generateFibonacciNumbers(long upTo) {
        return generateFibonacciFrom(0, 1, upTo);
    }

    public static long[] generateFibonacciFrom(long f0, long f1, long upTo) {
        ArrayList<Long> list = new ArrayList<Long>();
        while (f0 <= upTo) {
            list.add(f0);
            long tmp = f0 + f1;
            f0 = f1;
            f1 = tmp;
        }
        return Cast.toLong(list);
    }

    /**
     * Euler297
     */
    public static long sumOfTermCountOverAllZeckendorfRepresentationLessThan(long N) {
        long[] fibs = generateFibonacciFrom(1, 2, N);
        long res = 0;
        int cnt = 0;
        long[][][] way = new long[fibs.length + 1][fibs.length + 1][2];
        way[0][0][0] = 1;
        for (int i = 0; i < fibs.length; i++) {
            for (int j = 0; j < fibs.length; j++) {
                way[i + 1][j + 1][1] += way[i][j][0];
                way[i + 1][j][0] += way[i][j][0] + way[i][j][1];
            }
        }
        for (int i = fibs.length - 1; i >= 0; i--) {
            if (fibs[i] < N) {
                // fib[i-1] + fib[i-3] + ... < N
                for (int j = 0; j * 2 <= i + 1; j++) {
                    res += (way[i][j][0] + way[i][j][1]) * (cnt + j);
                }
                N -= fibs[i];
                cnt++;
            }
        }
        // for N-1.
        res += cnt;
        return res;
    }

    /**
     * fib[0] = 0, fib[1] = 1.
     *
     * @param nth
     * @return
     */
    public static double logFibonacci(int nth) {
        double phi = MathUtils.GOLDEN_RATIO;
        if (nth < 100) {
            double fib = (Math.pow(phi, nth) - Math.pow(1 - phi, nth)) / Math.sqrt(5);
            return Math.log(fib);
        }
        return nth * Math.log(phi) - Math.log(5) / 2;
    }
}
