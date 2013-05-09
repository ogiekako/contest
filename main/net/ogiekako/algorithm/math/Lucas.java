package net.ogiekako.algorithm.math;

/**
 * lucas algorithm
 * MOD must > 1
 * <p/>
 * verified at TCO10WC1000.
 */

public class Lucas {
    // C(n,k) % MOD
    public int lucas(int n, int k) {
        if (n < k) return 0;
        long res = 1;
        while (n > 0) {
            int ni = n >= M ? n % M : n;
            int ki = k >= M ? k % M : k;
            res *= choose(ni, ki);
            if (res >= TH) res %= M;
            n /= M; k /= M;
        }
        return (int) (res % M);
    }
    /**
     * MOD must be a prime.
     * MOD must > 1.
     * memory: O(MOD)
     * construct: O(MOD * log MOD)
     * query: O(log_MOD(n)). / (1+log_MOD(n)) modulo.
     *
     * @param MOD
     */
    public Lucas(int MOD) {
        M = MOD;
        TH = Long.MAX_VALUE / M;
        preCalc();
    }

    private int M; // MOD;
    private long TH;
    private long[] fact;
    private long[] ifact;// factinv
    private long choose(int n, int k) {
        if (n < k) return 0;
        long tmp = fact[n] * ifact[k];
        if (tmp >= TH) tmp %= M;
        return tmp * ifact[n - k] % M;
    }

    private long pow(long n, int p) {
        if (p == 0) return 1;
        long m = pow(n, p >> 1);
        if ((p & 1) == 0) return m * m % M;
        long tmp = m * m;
        if (tmp >= TH) tmp %= M;
        return tmp * n % M;
    }

    private void preCalc() {
        fact = new long[M];
        ifact = new long[M];
        for (int i = 0; i < M; i++) fact[i] = i == 0 ? 1 : fact[i - 1] * i % M;
        for (int i = M - 1; i >= 0; i--) ifact[i] = i == M - 1 ? pow(fact[i], M - 2) : ifact[i + 1] * (i + 1) % M;
    }
}