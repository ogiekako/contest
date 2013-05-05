package on_2012.on2012_8_1.taskd;


import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import org.junit.Assert;

import java.io.PrintWriter;
import java.util.Random;

public class TaskD {
    int MOD = (int) (1e9 + 7);
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        long res = solve(h, w);
        out.println(res);
    }

    private long solve(int h, int w) {
        h++; w++;
        long[][] num = new long[2][2];
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) num[i % 2][j % 2]++;
        long numInt = 0;
        for (int x0 = 0; x0 < 2; x0++)
            for (int y0 = 0; y0 < 2; y0++)
                for (int x1 = 0; x1 < 2; x1++)
                    for (int y1 = 0; y1 < 2; y1++)
                        for (int x2 = 0; x2 < 2; x2++)
                            for (int y2 = 0; y2 < 2; y2++) {
                                int dx1 = (x1 - x0) & 1, dy1 = (y1 - y0) & 1;
                                int dx2 = (x2 - x0) & 1, dy2 = (y2 - y0) & 1;
                                if ((dx1 * dy2 - dx2 * dy1 & 1) == 0) {
                                    numInt = (numInt + num[x0][y0] * num[x1][y1] * num[x2][y2]) % MOD;
                                }
                            }

        long zero = 0;
        long S = h * w;
        zero += S;
        zero += S * (S - 1) * 3; zero %= MOD;

        zero += h * (h - 1) % MOD * (h - 2) * w;
        zero += w * (w - 1) % MOD * (w - 2) * h;
        zero %= MOD;

        for (int x = 1; x < h; x++)
            for (int y = 1; y < w; y++) {
                int g = MathUtils.gcd(x, y);
                zero = (zero + (g - 1) * (h - x) * (w - y) * 12) % MOD;
            }
        return (numInt - zero + MOD) % MOD;
    }

    public static void main(String[] args) {
        TaskD instance = new TaskD();
        Random rnd = new Random(14091284L);
        for (int iteration = 0; iteration < 1000; iteration++) {
            System.out.print(".");
            int h = rnd.nextInt(30) + 1;
            int w = rnd.nextInt(30) + 1;
            long res = instance.solve(h, w);
            long exp = instance.solveStupid(h, w);
            Assert.assertEquals(exp, res);
        }
    }

    private long solveStupid(int h, int w) {
        long res = 0;
        for (int x1 = 0; x1 < h + 1; x1++)
            for (int y1 = 0; y1 < w + 1; y1++)
                for (int x2 = 0; x2 < h + 1; x2++)
                    for (int y2 = 0; y2 < w + 1; y2++)
                        for (int x3 = 0; x3 < h + 1; x3++)
                            for (int y3 = 0; y3 < w + 1; y3++) {
                                int dx1 = x2 - x1, dy1 = y2 - y1, dx2 = x3 - x1, dy2 = y3 - y1;
                                int S = dx1 * dy2 - dx2 * dy1;
                                if (S < 0) S = -S;
                                if (S > 0 && S % 2 == 0) {
                                    res++;
                                }
                            }
        return res;
    }
}
