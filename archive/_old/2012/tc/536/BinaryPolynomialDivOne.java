package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;
import java.util.Random;

public class BinaryPolynomialDivOne {
    public int findCoefficient(int[] as, long m, long k) {
        m <<= 1;
        k <<= 1;
        boolean[][] dp = new boolean[56][1 << 7];
        dp[55][((int) (k >> 55))] = true;
        for (int i = 55; i > 0; i--) {
            for (int S = 0; S < 1 << 7; S++)
                if (dp[i][S]) {
                    if ((m >> i & 1) == 1) {
                        for (int a = 0; a < as.length; a++)
                            if (as[a] == 1) {
                                if (S >= a) {
                                    int nS = S - a;
                                    nS = (int) (nS << 1 | (k >> i - 1 & 1));
                                    if ((nS >> 7 & 1) == 0) {
                                        dp[i - 1][nS] ^= dp[i][S];
                                    }
                                }
                            }
                    } else {
                        int nS = (int) ((S << 1) | (k >> i - 1 & 1));
                        if ((nS >> 7 & 1) == 0) {
                            dp[i - 1][nS] ^= dp[i][S];
                        }
                    }
                }
        }
        return dp[0][0] ? 1 : 0;
    }

    public static void main(String[] args) {
        BinaryPolynomialDivOne ins = new BinaryPolynomialDivOne();
        int N = 100;
        int M = 100;
        Random rnd = new Random(1234098248L);
        for (; ; ) {
            int n = rnd.nextInt(N) + 1;
            int m = rnd.nextInt(M) + 1;
            int k = rnd.nextInt(n * m) + 1;
            int[] as = new int[n];
            for (int i = 0; i < n; i++) as[i] = rnd.nextInt(2);
            as[n - 1] = 1;
            int res = ins.findCoefficient(as, m, k);
            int exp = solveStupid(as, m, k);
            if (res != exp) {
                System.err.println(Arrays.toString(as) + " " + m + " " + k);
                throw new RuntimeException();
            }
        }
    }

    private static int solveStupid(int[] as, long m, long k) {
        int[] bs = new int[]{1};
        for (int i = 0; i < m; i++) {
            bs = mul(as, bs);
        }
        return k < bs.length ? bs[(int) k] : 0;
    }

    private static int[] mul(int[] as, int[] bs) {
        int[] cs = new int[as.length + bs.length - 1];
        for (int i = 0; i < as.length; i++)
            for (int j = 0; j < bs.length; j++) {
                cs[i + j] += as[i] * bs[j];
                if (cs[i + j] >= 2) cs[i + j] -= 2;
            }
        return cs;
    }
}

