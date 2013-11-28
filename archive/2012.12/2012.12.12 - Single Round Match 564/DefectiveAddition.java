package tmp;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class DefectiveAddition {
    static int MOD = 1000000007;
    private static final long INV2 = (long) (5e8 + 4);

    public int count(int[] cards, int n) {
//        long[][] C = MathUtils.combinationMod(51,MOD);
        Arrays.sort(cards);
        ArrayUtils.reverse(cards);
        debug(cards);
        int xor = 0;
        for (int c : cards) xor ^= c;
        long res = 0;
        if (xor == n) res++;
        for (int i = 0; i < cards.length; i++)
            for (int h = 1; h <= cards[i]; h <<= 1)
                if ((cards[i] & h) > 0) {
                    int uM = Integer.MAX_VALUE ^ h - 1 ^ h;
                    int lM = h - 1;
                    int upper = 0;
                    for (int j = 0; j < cards.length; j++) {
                        upper ^= cards[j] & uM;
                    }
                    if (upper != (n & uM)) continue;
                    int d = n & h;
                    for (int j = 0; j < i; j++) d ^= cards[j] & h;
                    debug("d", d);
                    long add = 1;
                    for (int j = 0; j < i; j++) add = add * ((cards[j] & lM) + 1) % MOD;
                    debug("A", add);
                    long[][] dp = new long[2][2];
                    int cur = 0, nxt = 1;
                    dp[cur][0] = 1;
                    for (int j = i + 1; j < cards.length; j++) {
                        if ((cards[j] & h) > 0) {
                            Arrays.fill(dp[nxt], 0);
                            for (int k = 0; k < 2; k++) {
                                dp[nxt][k ^ 1] = (dp[nxt][k ^ 1] + dp[cur][k] * ((cards[j] & lM) + 1)) % MOD;
                                dp[nxt][k] = (dp[nxt][k] + dp[cur][k] * h) % MOD;
                            }
                            int tmp = cur; cur = nxt; nxt = tmp;
                        } else {
                            add *= (cards[j] & lM) + 1;
                            debug("add", cards[j], lM, add);
                            add %= MOD;
                        }
                    }
                    debug("B", add);
                    add *= dp[cur][d > 0 ? 1 : 0];
                    debug("dp", dp);
                    debug("C", add, dp[cur][d > 0 ? 1 : 0]);
                    add %= MOD;
                    debug(add);
                    res = (res + add) % MOD;
                }
        return (int) ((res % MOD + MOD) % MOD);
    }
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        int n = 2;
        Random rnd = new Random(10298L);
        for (int p = 0; p < 50; p++) {
            debug(p);
            int[] cs = new int[n];
            for (int i = 0; i < n; i++) cs[i] = rnd.nextInt(16);
            int N = rnd.nextInt(16);
            int exp = solveStupid(cs, 0, N);
            int res = new DefectiveAddition().neal(cs, N);
            if (exp != res) {
                debug(cs, N);
                throw null;
            }
        }
    }

    private static int solveStupid(int[] cs, int p, int n) {
        if (p == cs.length) return n == 0 ? 1 : 0;
        int res = 0;
        for (int i = 0; i <= cs[p]; i++) res += solveStupid(cs, p + 1, n ^ i);
        return res;
    }

    private static int neal(int[] cards, int goal) {
        int N = cards.length;
        long total = 0;
        for (int bit = 30; bit >= -1; bit--) {
            int shift = bit >= 0 ? (1 << bit) : 0;
            long even = 1, odd = 0, match = 1;
            int check = 0;
            for (int i = 0; i < N; i++) {
                if ((cards[i] & shift) > 0) {
                    long e = even, o = odd;
                    even = (e * shift + o * (cards[i] + 1 - shift)) % MOD;
                    odd = (e * (cards[i] + 1 - shift) + o * shift) % MOD;
                    match = match * (cards[i] + 1 - shift) % MOD;
                } else {
                    long e = even, o = odd;
                    even = e * (cards[i] + 1) % MOD;
                    odd = o * (cards[i] + 1) % MOD;
                    match = match * (cards[i] + 1) % MOD;
                }
                check ^= cards[i];
                cards[i] &= ~shift;
            }
            if (bit < 0) match = 0;
            long ways = (goal & shift) > 0 ? odd : even;
            if (((check ^ goal) & shift) == 0)
                ways = (ways - match + MOD) % MOD;
            for (int i = 0; i < bit; i++) ways = ways * INV2 % MOD;

            total = (total + ways) % MOD;
            if (((check ^ goal) & shift) > 0) break;
        }
        return (int) total;
    }
}
