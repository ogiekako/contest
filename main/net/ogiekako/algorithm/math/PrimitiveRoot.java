package net.ogiekako.algorithm.math;

import java.util.Random;

public class PrimitiveRoot {
    /**
     * Compute a primitive root of the given prime using
     * randomized algorithm.
     * <p/>
     * TODO check the complexity
     *
     * @param prime prime
     * @return A primitive root of the given prime.
     */
    long primitiveRoot(int prime) {
        Random rand = new Random();
        long q = rand.nextInt(prime);
        while (!check(q, prime)) q = rand.nextInt(prime);
        return q;
    }

    long pow(long n, long p, long MOD) {
        if (p == 0) return 1 % MOD;
        long m = pow(n, p >> 1, MOD);
        return (p & 1) == 0 ? m * m % MOD : m * m % MOD * n % MOD;
    }
    boolean check(long q, long p) {
        if (q <= 1) return false;
        for (long i = 2; i * i <= p - 1; i++)
            if ((p - 1) % i == 0) {
                if (pow(q, i, p) == 1) return false;
                if (pow(q, (p - 1) / i, p) == 1) return false;
            }
        return true;
    }
}
