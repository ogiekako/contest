package net.ogiekako.algorithm.math;

import static java.util.Arrays.fill;

/**
 * オイラーのトーティエント関数.
 * nが正の整数でaをnと互いに素な正の整数としたとき, a^tot(n) = 1 (mod n).
 * a,nが最大公約数g > 1 をもつ場合でも, a^(tot(n)+tot(n)) = a (mod tot(n)) となり,周期は,tot(n) の約数.
 * <p/>
 * a^b % c を計算するときは,
 * a,bが互いに素なら,a^(b % tot(n))
 * a,bが互いに素とは限らないなら,
 * b<tot(n) (真の値が)の時,a^b % c を求め,
 * b>=tot(n) の時,a^b % c = a^(b%tot(n) + tot(n)) % c を求めればよい.
 */
public class TotientFuction {
    /**
     * O(sqrt(n)).
     *
     * @param n
     * @return
     */
    public static int tot(int n) {
        int result = n;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) result -= result / i;
            while (n % i == 0) n /= i;
        }
        if (n > 1) result -= result / n;
        return result;
    }

    // func(A,B) = B==0 ? A : func(A^A,B-1)
    // を,mod C で求めるサンプル.
    //	GCJJ 2011 final B.
    private int solve(int A, int B, int C) {
        int[][] memo = new int[B + 1][C + 1];
        for (int i = 0; i < B + 1; i++) fill(memo[i], -1);
        return solve(A, B, C, memo);
    }
    private int solve(int A, int B, int C, int[][] memo) {
        if (B == 0) return A % C;
        if (memo[B][C] >= 0) return memo[B][C];
        int per = tot(C);
        int s = solve(A, B - 1, C, memo);
        int t = solve(A, B - 1, per, memo) - 1;
        if (t < 0) t += per;
        return memo[B][C] = s * pow(s, t, C) % C;
    }
    private int pow(int n, int p, int mod) {
        if (p == 0) return 1 % mod;
        int n2 = pow(n, p / 2, mod);
        return (p & 1) == 0 ? n2 * n2 % mod : n2 * n2 % mod * n % mod;
    }

    /**
     * O(N log N)
     * <p/>
     * Euler214
     *
     * @param upTo
     * @return
     */
    public static int[] generateTotientTable(int upTo) {
        int N = upTo + 1;
        int[] dp = new int[N];
        for (int i = 0; i < N; i++) dp[i] = i;
        for (int prime = 2; prime < N; prime++) {
            if (dp[prime] == prime) {
                for (int j = prime; j < N; j += prime) {
                    dp[j] -= dp[j] / prime;
                }
            }
        }
        return dp;
    }
}
