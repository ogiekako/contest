package net.ogiekako.algorithm.math;

import net.ogiekako.algorithm.utils.Cast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class MathUtils {
    public static final double GOLDEN_RATIO = (Math.sqrt(5) + 1.0) / 2.0;

    public static long gcd(long x, long y) {
        while (y != 0) {
            x %= y;
            if (x == 0) return y;
            y %= x;
        }
        return x;
    }

    public static int gcd(int x, int y) {
        while (y != 0) {
            x %= y;
            if (x == 0) return y;
            y %= x;
        }
        return x;
    }

    /**
     * O(sqrt(N))
     *
     * @param number - number
     * @return - factors
     */
    public static PrimeDecomposition factorize(long number) {
        return PrimeDecomposition.factorize(number);
    }

    public static PrimeDecomposition[] factorize(long from, long to) {
        if (to - from > Integer.MAX_VALUE) throw new IllegalArgumentException("to - from must <= Integer.MAX_VALUE");
        int n = (int) (to - from);
        int sq = (int) (MathUtils.sqrt(to) + 1);
        BitSet isNotPrimes = new BitSet(sq);
        long[] values = new long[n];
        for (int i = 0; i < n; i++) values[i] = from + i;
        PrimeDecomposition[] res = new PrimeDecomposition[n];
        for (int i = 0; i < n; i++) res[i] = new PrimeDecomposition(1);
        for (int prime = 2; prime < sq; prime++)
            if (!isNotPrimes.get(prime)) {
                if ((long) prime * prime < sq)
                    for (int multiple = prime * prime; multiple < sq; multiple += prime) isNotPrimes.set(multiple);
                for (int i = (int) ((from + prime - 1) / prime * prime - from); i < n; i += prime) {
                    int power = 0;
                    while (values[i] % prime == 0) {
                        values[i] /= prime;
                        power++;
                    }
                    res[i].add(new PrimePower(prime, power));
                }
            }
        for (int i = 0; i < n; i++) if (values[i] > 1) res[i].add(new PrimePower(values[i], 1));
        return res;
    }

    public static boolean isPrime(long number) {
        if (number < 2) return false;
        for (long d = 2; d * d <= number; d++) {
            if (number % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * floor( sqrt(N) ).
     */
    public static long sqrt(long N) {
        if (N < 0) throw new IllegalArgumentException("N must not be negative but was " + N);
        long n = (long) Math.sqrt(N);
        while (n * n > N) n--;
        while (n * n <= N) n++;
        return n - 1;
    }

    /**
     * @verified GCJ 2011 R3 D, Mystery Square
     */
    public static BigInteger sqrt(BigInteger b) {
        int n = b.bitLength();
        int d = n / 2;
        BigInteger res = BigInteger.ZERO;
        while (d >= 0) {
            BigInteger tmp = res.or(BigInteger.ONE.shiftLeft(d));
            if (tmp.pow(2).compareTo(b) <= 0) res = tmp;
            d--;
        }
        return res;
    }

    public static int[] generatePrimes(int upTo) {
        int N = upTo;
        boolean[] isPrime = generatePrimaryTable(N);
        int m = 0;
        for (boolean p : isPrime) if (p) m++;
        int[] res = new int[m];
        m = 0;
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) res[m++] = i;
        }
        return res;
    }

    public static boolean[] generatePrimaryTable(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        if (0 < n) isPrime[0] = false;
        if (1 < n) isPrime[1] = false;
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i)
                    isPrime[j] = false;
            }
        }
        return isPrime;
    }

    // 0 <= res < modulo
    public static long powMod(long value, long exponent, int modulo) {
        if (exponent < 0) throw new IllegalArgumentException("negative exponent");
        if (modulo == 1) return 0;
        if (value >= modulo) value %= modulo;
        if (value < 0) value = value % modulo + value;
        long res = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                res = res * value % modulo;
            }
            value = value * value % modulo;
            exponent >>= 1;
        }
        return res;
    }

    public static int[] generateMobiusFunction(int upTo) {
        int N = upTo;
        int[] primes = generatePrimes(N);
        int[] mobius = new int[N + 1];
        mobius[1] = 1;
        for (int prime : primes) {
            for (int i = (mobius.length - 1) / prime; i >= 1; i--) {
                mobius[i * prime] = mobius[i] * -1;
            }
        }
        return mobius;
    }

    /**
     * upTo 以下で,square free な 数の 総数.
     * O(sqrt(N))
     * Euler193_2
     *
     * @param upTo
     * @return
     */
    public static long squareFreeCount(long upTo) {
        long N = upTo;
        int sq = (int) MathUtils.sqrt(N);
        int[] mobius = MathUtils.generateMobiusFunction(sq);
        long res = 0;
        for (int k = 1; k <= sq; k++) {
            res += mobius[k] * (N / k / k);
        }
        return res;
    }

    public static long power(long base, int exponent) {
        long res = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) res *= base;
            base *= base;
            exponent >>= 1;
        }
        return res;
    }

    public static boolean isSquare(long number) {
        long sq = sqrt(number);
        return sq * sq == number;
    }

    public static double log2(double number) {
        return Math.log(number) / Math.log(2);
    }

    public static double log(double base, double number) {
        return Math.log(number) / Math.log(base);
    }

    public static int[] generateDivisorTable(int upTo) {
        int N = upTo;
        int[] res = new int[N + 1];
        for (int i = 2; i * i <= N; i++) {
            if (res[i] == 0) {
                for (int j = i * i; j <= N; j += i) {
                    res[j] = i;
                }
            }
        }
        for (int i = 2; i <= N; i++) {
            if (res[i] == 0) {
                res[i] = i;
            }
        }
        return res;
    }

    public static long[][] combination(int upTo) {
        int N = upTo;
        long[][] C = new long[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j <= i; j++) {
                C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
            }
        return C;
    }

    public static long[][] generateCombinationMod(int h, int w, int MOD) {
        long[][] C = new long[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w && j <= i; j++) {
                long value = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (value >= MOD) value -= MOD;
                C[i][j] = value;
            }
        return C;
    }

    /**
     * どの素因数もtype 以下である数で,upTo以下のもの.
     * Euler204
     * 100, 1e9 -> about 3000000
     *
     * @param type
     * @param upTo
     * @return
     */
    public static int[] generateGeneralizedHammingNumbers(int type, int upTo) {
        int[] primes = generatePrimes(type);
        ArrayList<Integer> list = new ArrayList<Integer>();
        dfsGenerateGeneralizedHammingNumbers(0, 1, upTo, primes, list);
        int[] res = Cast.toInt(list);
        Arrays.sort(res);
        return res;
    }

    public static void dfsGenerateGeneralizedHammingNumbers(int i, int cur, int upTo, int[] primes, ArrayList<Integer> list) {
        if (i == primes.length) {
            list.add(cur);
            return;
        }
        dfsGenerateGeneralizedHammingNumbers(i + 1, cur, upTo, primes, list);
        int prime = primes[i];
        while (cur <= upTo / prime) {
            cur *= prime;
            dfsGenerateGeneralizedHammingNumbers(i + 1, cur, upTo, primes, list);
        }
    }

    // sum{s,t).
    public static long sum(long s, long t) {
        if (t < s) throw new IllegalArgumentException();
        long a = t - s;
        long b = s + t - 1;
        if ((a & 1) == 0) a /= 2;
        else b /= 2;
        return a * b;
    }

    public static long[][] combinationMod(int n, int modulus) {
        long[][] C = new long[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i + 1; j++) {
                C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (C[i][j] >= modulus) C[i][j] -= modulus;
            }
        return C;
    }

    public static boolean odd(int i) {
        return (i & 1) == 1;
    }

    // 0 <= res < modPrime
    public static long inverse(long value, int modPrime) {
        return powMod(value, modPrime - 2, modPrime);
    }

    /*
     * (base^(k-1) + base^(k-2) + ... + base^0 ) % modulus
     * を返す.
     * modulus は素数でなくとも大丈夫.
     *
     */
    public static long sumOfGeometricSequence(long base, long k, int modulus) {
        if (k <= 0) return 0;
        if (k == 1) return 1 % modulus;
        long sum2 = sumOfGeometricSequence(base * base % modulus, k >> 1, modulus);
        if ((k & 1) == 0) {
            return sum2 * (base + 1) % modulus;
        } else {
            return (sum2 * (base * base % modulus + base) + 1) % modulus;
        }
    }

    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    /*
    xor [0, upTo )
     */
    public static long xorSum(long upTo) {
        switch ((int) (upTo & 3)) {
            case 0:
                return 0;
            case 1:
                return upTo - 1;
            case 2:
                return 1;
            case 3:
                return upTo;
            default:
                throw new AssertionError();
        }
    }

    public static double hypot(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static long[] factorialMod(int n, int modulus) {
        long[] res = new long[n];
        for (int i = 0; i < n; i++) res[i] = (i == 0 ? 1 : res[i - 1] * i) % modulus;
        return res;
    }

    public static int[] generateNumberOfPrimeDivisorsWithMultiplicity(long from, long to) {
        int n = (int) (to - from);
        int[] res = new int[n];
        int[] primes = MathUtils.generatePrimes((int) (MathUtils.sqrt(to + 1) + 1));
        long[] values = new long[n];
        for (int i = 0; i < n; i++) values[i] = from + i;
        for (int prime : primes) {
            long v = from / prime * prime;
            if (v < from) v += prime;
            for (; v < to; v += prime) {
                int i = (int) (v - from);
                while (values[i] % prime == 0) {
                    values[i] /= prime;
                    res[i]++;
                }
            }
        }
        for (int i = 0; i < n; i++) if (values[i] > 1) res[i]++;
        return res;
    }

    // verified
    public static long[] generateCatalanNumber(int upTo) {
        int N = upTo;
        long[] C = new long[N];
        for (int n = 0; n < N; n++) {
            if (n == 0) C[n] = 1;
            for (int i = 0; i < n; i++) {
                C[n] += C[i] * C[n - 1 - i];
            }
        }
        return C;
    }

    public static long[] generatePowers(int base, int count, int modulus) {
        long[] res = new long[count];
        for (int i = 0; i < count; i++) {
            res[i] = (i == 0 ? 1 : res[i - 1] * base) % modulus;
        }
        return res;
    }
}
