package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Random;

public class Tenka_D {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long start = System.currentTimeMillis();
        N = in.nextInt();
        n = N / 3;
        x = new long[N];
        y = new long[N];
        for (int i = 0; i < N; i++) {
            x[i] = in.nextLong(); y[i] = in.nextLong();
        }
        choose = new int[n][3];
        for (int i = 0; i < N; i++) choose[i / 3][i % 3] = i;
        Random rnd = new Random();
        while (System.currentTimeMillis() - start < 1300) {
            for (int k = 0; k < 100; k++) {
                int a = rnd.nextInt(n), b = rnd.nextInt(n);
                if (a == b) continue;
                int i = rnd.nextInt(3), j = rnd.nextInt(3);
                long cur = calc(choose[a][0], choose[a][1], choose[a][2]) + calc(choose[b][0], choose[b][1], choose[b][2]);
                int tmp = choose[a][i]; choose[a][i] = choose[b][j]; choose[b][j] = tmp;
                long nxt = calc(choose[a][0], choose[a][1], choose[a][2]) + calc(choose[b][0], choose[b][1], choose[b][2]);
                if (cur > nxt) {
                    tmp = choose[a][i]; choose[a][i] = choose[b][j]; choose[b][j] = tmp;
                }
            }
        }

//        long sum=0;
        for (int i = 0; i < n; i++) {
            out.printf("%d %d %d\n", choose[i][0], choose[i][1], choose[i][2]);

//            sum += calc(choose[i][0],choose[i][1],choose[i][2]);
        }
//        System.err.println(sum);

    }

    private long calc(int a, int b, int c) {
        long x1 = x[b] - x[a], y1 = y[b] - y[a];
        long x2 = x[c] - x[a], y2 = y[c] - y[a];
        long res = Math.abs(x1 * y2 - y1 * x2);
        return res;
    }

    int N, n;
    int[][] choose;
    long[] x, y;
}
