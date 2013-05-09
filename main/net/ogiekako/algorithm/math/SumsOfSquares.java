package net.ogiekako.algorithm.math;
import java.util.Arrays;

import static java.lang.Math.sqrt;
public class SumsOfSquares {
    /**
     * http://en.wikipedia.org/wiki/Fermat%27s_theorem_on_sums_of_two_squares
     * l が高々2個の平方数の和で表されるかどうかを返す.
     * 合成数が高々二個の平方数の和で表されるための必要十分条件は、4を法として3に合同な素因数が全て平方（冪指数が偶数）になっていることである。
     *
     * @param l
     * @return
     */
    public static boolean expressedAsSumOfTwoSquares(long l) {
        int sq = (int) sqrt(l) + 2;
        int[] ps = makePrimeMod4is3UpTo(sq);
        return expressedAsSumOfTwoSquares(ps, l);
    }

    public static boolean expressedAsSumOfTwoSquares(int[] primesMod4is3, long l) {
        if (l == 0) return true;
        if (l < 0) return false;
        while (l % 2 == 0) l /= 2;
        for (int p : primesMod4is3) {
            if (p * p > l) break;
            int cnt = 0;
            while (l % p == 0) {
                l /= p;
                cnt++;
            }
            if (cnt % 2 == 1) return false;
        }
        if (l > 1 && l % 4 == 3) return false;
        return true;
    }

    public static int[] makePrimeMod4is3UpTo(int n) {
        boolean[] isP = new boolean[n];
        Arrays.fill(isP, true);
        isP[0] = isP[1] = false;
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (isP[i]) {
                if (i % 4 == 3) m++;
                for (int j = i + i; j < n; j += i) isP[j] = false;
            }
        }
        int[] ps = new int[m];
        m = 0;
        for (int i = 0; i < n; i++) {
            if (isP[i] && i % 4 == 3) {
                ps[m++] = i;
            }
        }
        return ps;
    }

    /**
     * x^2 + y^2 = n
     * なる整数(x,y) の組み合わせの数を返す.
     * ex. n = 1 のとき,(0,-1),(0,1),(-1,0),(1,0) の4つ.
     * <p/>
     * Factor n as n = p1^a1 * p2^a2 * ... * q1^b1 * q2^b2 * ... * 2^c, where the p's are
     * primes == 1 mod 4 and the q's are primes == 3 mod 4. Then a(n) = 0 if any b is odd,
     * otherwise a(n) = 4*(1 + a1)*(1 + a2)*...
     * http://oeis.org/A004018
     *
     * @param n
     * @return
     */
    public static long wayToWriteAsSumOfTwoSquares(long n) {
        int sq = (int) sqrt(n) + 2;
        int[] ps = MathUtils.generatePrimes(sq);
        return wayToWriteAsSumOfTwoSquares(ps, n);
    }

    /**
     * x^2 + y^2 = n
     * なる整数(x,y) の組み合わせの数を返す.
     * ex. n = 1 のとき,(0,-1),(0,1),(-1,0),(1,0) の4つ.
     * <p/>
     * next to last prime must > sqrt(n).
     * primes must be assending order.
     *
     * @param primes
     * @param n
     * @return
     */
    public static long wayToWriteAsSumOfTwoSquares(int[] primes, long n) {
        if (n == 0) return 1;
        if (n < 0) throw new IllegalArgumentException();
        long res = 4;
        for (int p : primes) {
            if (p * p > n) break;
            int cnt = 0;
            while (n % p == 0) {
                n /= p;
                cnt++;
            }
            if (cnt == 0) continue;
            if ((p & 3) == 3) {
                if ((cnt & 1) == 1) return 0;
            } else if ((p & 3) == 1) {
                res *= cnt + 1;
            }
        }
        if (n > 1) {
            if ((n & 3) == 3) return 0;
            if ((n & 3) == 1) res *= 2;
        }
        return res;
    }


    /**

     http://en.wikipedia.org/wiki/Pythagorean_quadruple

     The primitive quadruplets a,b,c,d with gcd(a,b,c)=1 and
     a^2 + b^2 + c^2  = d^2, can be generated using m,n,p,q

     a = m^2 + n^2 - p^2 - q^2
     b = 2 (mq + np)
     c = 2 (nq - mp)
     d = m^2 + n^2 + p^2 + q^2

     *
     */
}
