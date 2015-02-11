package src;

import java.math.BigInteger;

public class PulleyTautLine {
    double A, B, C, D;
    long[][][] dp;
    long INF = Long.MAX_VALUE / 4;
    long[][] choose;

    public double getLength(int _d, int _r, int n, long k) {
        k = (k + 1) / 2;
        double d = _d;
        double r = _r;
        A = Math.sqrt(d * d - r * r) + (Math.PI / 2 - Math.acos(r / d)) * r;
        B = d;
        C = 2 * (Math.sqrt(d * d / 4 - r * r) + (Math.PI / 2 - Math.acos(2 * r / d)) * r);
        D = Math.PI * r;
        if (n == 1) {
            return 2 * A + (k - 1) * 2 * D;
        }
        choose = new long[300][300];
        choose[0][0] = 1;
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j <= i; j++) {
                choose[i][j] = j == 0 ? 1 : Math.min(INF, choose[i - 1][j - 1] + choose[i - 1][j]);
            }
        }

        dp = new long[110][110][n]; // B/C, D+B/C, pos -> #ways
        dp[0][0][0] = 1;
        for (int i = 0; i < 109; i++)
            for (int j = 0; j < 109; j++)
                for (int p = 0; p < n; p++)
                    if (dp[i][j][p] > 0) {
                        int dir = j % 2 == 0 ? 1 : -1;
                        // B/C
                        if (0 <= p + dir && p + dir < n) {
                            dp[i + 1][j][p + dir] += dp[i][j][p];
                            dp[i + 1][j][p + dir] = Math.min(dp[i + 1][j][p + dir], INF);
                        }
                        // D + B/C
                        if (0 <= p - dir && p - dir < n) {
                            dp[i + 1][j + 1][p - dir] += dp[i][j][p];
                            dp[i + 1][j + 1][p - dir] = Math.min(dp[i + 1][j + 1][p - dir], INF);
                        }
                    }

        double res = 0;
        for (double x = 1e30; x > 1e-10; x /= 2) {
            long num = numLessThan(n, res + x);
            if (num < k) {
                res += x;
            }
        }
        return res;
    }

    private long numLessThan(int n, double th) {
        th -= 2 * A;
        if (th < 0) return 0;

        long res = 0;
        for (int nb = 0; nb < 105; nb++)
            for (int nc = 0; nb + nc < 105; nc++)
                for (int nd = 0; nd < nb + nc; nd += 2)
                    if (dp[nb + nc][nd][n - 1] > 0) {
                        double base = nb * B + nc * C + nd * D;
                        double rest = th - base;
                        if (rest < 0) continue;
                        if (rest / D / 2 > INF) {
                            res = INF;
                            break;
                        }
                        long d2 = (long) (rest / D / 2);

                        long val1 = choose(nb + nc, nb);
                        long val2 = choose(d2 + nb + nc + 1, nb + nc + 1);
                        long val3 = dp[nb + nc][nd][n - 1];
                        if ((double) val1 * val2 * val3 > INF) return INF;
                        res += val1 * val2 * val3;
                        if (res >= INF) return INF;
                    }
        return res;
    }

    private long choose(long n, int k) {
        if (n < 300 && k < 300) {
            return choose[((int) n)][k];
        }
        BigInteger res = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            res = res.multiply(BigInteger.valueOf(n - i));
            res = res.divide(BigInteger.valueOf(i + 1));
        }
        return res.longValue();
    }
}
