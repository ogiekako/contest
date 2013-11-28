package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.Cast;
import net.ogiekako.algorithm.utils.CollectionUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Euler357 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] primes = MathUtils.generatePrimes(N + 1);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        int[] is = new int[100];
        is[0] = 2;
        dfs(is, 1, 1, 2, N, primes, list);
        long res = CollectionUtils.sum(list);
        out.println(res);
    }

    public static int[] generateNoRepeatedPrimeFactorNumbers(int upTo) {
        int[] primes = MathUtils.generatePrimes(upTo);
        ArrayList<Integer> list = new ArrayList<Integer>();
        dfs(new int[20], 0, 0, 1, upTo, primes, list);
        int[] res = Cast.toInt(list);
        Arrays.sort(res);
        return res;
    }

    private static void dfs(int[] is, int n, int pi, int number, int N, int[] primes, ArrayList<Integer> list) {
        {
            boolean ok = true;
            for (int i = 0; i < 1 << n; i++) {
                int p1 = 1;
                int p2 = 1;
                for (int j = 0; j < n; j++) {
                    if ((i >> j & 1) == 0) p1 *= is[j];
                    else p2 *= is[j];
                }
                int p = p1 + p2;
                if (Arrays.binarySearch(primes, p) < 0) {
                    ok = false;
                    break;
                }
            }
            if (ok) list.add(number);
        }
        for (int j = pi; j < primes.length; j++) {
            int prime = primes[j];
            is[n] = prime;
            if (number > N / prime) break;
            dfs(is, n + 1, j + 1, number * prime, N, primes, list);
        }
    }
}
