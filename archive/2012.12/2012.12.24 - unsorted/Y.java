package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PrimeDecomposition;
import net.ogiekako.algorithm.math.PrimePower;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class Y {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int t = in.nextInt();
        while (t-- > 0) {
            System.err.println("#test: " + t);
            long n = in.nextLong();
            PrimeDecomposition factor = factorize(n);
            boolean first = true;
            for (PrimePower pp : factor) {
                for (int i = 0; i < pp.power; i++) {
                    if (!first) out.print(" ");
                    first = false;
                    out.print(pp.prime);
                }
            }
            out.println();
        }
    }

    private PrimeDecomposition factorize(long n) {
        PrimeDecomposition res = new PrimeDecomposition();
        if (n == 1) return res;
        if (BigInteger.valueOf(n).isProbablePrime(30)) {
            res.add(new PrimePower(n, 1));
            return res;
        }
        Random rnd = new Random();
        for (int iteration = 0; iteration < 100; iteration++) {
            long a = (long) (rnd.nextDouble() * n);
            long b = (long) (rnd.nextDouble() * n);
            debug("a", "b", a, b);
            long x = 2, y = 2, d = 1;
            int tmp = 0;
            while (d == 1) {
                tmp++;
                if (tmp == 100000) {
//                    debug("iter",tmp);
//                    debug("x,y,d",x,y,d);
                    break;
                }
                x = f(x, a, b, n);
                y = f(f(y, a, b, n), a, b, n);
                d = MathUtils.gcd(Math.abs(x - y), n);
            }
            if (d != 1) {
                PrimeDecomposition res1 = factorize(d);
                PrimeDecomposition res2 = factorize(n / d);
                for (int i = 0, j = 0; i < res1.size() || j < res2.size(); ) {
                    if (i < res1.size() && (j == res2.size() || res1.get(i).prime < res2.get(j).prime)) {
                        res.add(res1.get(i++));
                    } else if (i == res1.size() || res1.get(i).prime > res2.get(j).prime) {
                        res.add(res2.get(j++));
                    } else {
                        long prime = res1.get(i).prime;
                        int power = res1.get(i++).power + res2.get(j++).power;
                        res.add(new PrimePower(prime, power));
                    }
                }
                return res;
            }
        }
        res.add(new PrimePower(n, 1));
        return res;
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private long f(long x, long a, long b, long mod) {
        BigInteger X = BigInteger.valueOf(x);
        BigInteger A = BigInteger.valueOf(a);
        BigInteger B = BigInteger.valueOf(b);
        BigInteger M = BigInteger.valueOf(mod);
        return X.multiply(A).add(B).mod(M).longValue();
    }
}
