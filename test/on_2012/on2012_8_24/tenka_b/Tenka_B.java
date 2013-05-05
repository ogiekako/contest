package on_2012.on2012_8_24.tenka_b;


import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class Tenka_B {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    int MOD = 1000000007;
    long N;
    boolean[][] bs = new boolean[100][100];

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int x = in.nextInt(), y = in.nextInt();
            bs[x][y] = true;
        }
        long res = N * (N - 1) * (N - 2) * (N - 3) / 24;
        for (int x = 0; x < 100; x++) {
            long m = 0;
            for (int y = 0; y < 100; y++) if (bs[x][y]) m++;
            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
        }
        for (int y = 0; y < 100; y++) {
            long m = 0;
            for (int x = 0; x < 100; x++) if (bs[x][y]) m++;
            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
        }
//        debug(res);

        for (int dx = 1; dx <= 100; dx++) {
            for (int dy = 1; dy <= 100; dy++) {
                if (MathUtils.gcd(dx, dy) == 1) {
                    for (int x = 0; x < dx; x++) {
                        for (int y = 0; y < 100; y++) {
                            long m = count(x, y, dx, dy);
                            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
                            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
                        }
                    }
                    for (int y = 0; y < dy; y++) {
                        for (int x = dx; x < 100; x++) {
                            long m = count(x, y, dx, dy);
                            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
                            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
                        }
                    }
                    for (int x = 0; x < dx; x++) {
                        for (int y = 0; y < 100; y++) {
                            long m = count(x, 99 - y, dx, -dy);
                            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
                            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
                        }
                    }
                    for (int y = 0; y < dy; y++) {
                        for (int x = dx; x < 100; x++) {
                            long m = count(x, 99 - y, dx, -dy);
                            res -= m * (m - 1) * (m - 2) / 6 * (N - m);
                            res -= m * (m - 1) * (m - 2) * (m - 3) / 24;
                        }
                    }
                }
            }
        }

        out.println(res % MOD);
    }

    private long count(int x, int y, int dx, int dy) {
        long res = 0;
        for (int d = 0; ; d++) {
            int nx = x + dx * d;
            int ny = y + dy * d;
            if (nx < 0 || ny < 0 || nx >= 100 || ny >= 100) break;
            if (bs[nx][ny]) res++;
        }
        return res;
    }
}
