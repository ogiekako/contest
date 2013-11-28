package src;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;
public class GoodNumbers {
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
    public int count(int[] a, int[] b, int N) {
        int m = a.length;
        int M = 1 << m;
        int[][] g = new int[M][M];
        long[] lcmA = new long[M];
        long[] lcmB = new long[M];
        lcm(a, m, M, lcmA);
        lcm(b, m, M, lcmB);
        for (int S = 0; S < M; S++) {
            for (int T = 0; T < M; T++) {
                long lcm = MathUtils.lcm(lcmA[S], lcmB[T]);
                if (lcm > Integer.MAX_VALUE) lcm = Integer.MAX_VALUE;
                g[S][T] = (int) (N / lcm);
            }
        }
        for (int S = 0; S < M; S++)
            for (int i = 0; i < m; i++)
                for (int T = 0; T < M; T++)
                    if ((T >> i & 1) == 0)
                        g[S][T] -= g[S][T | (1 << i)];
        for (int T = 0; T < M; T++)
            for (int i = 0; i < m; i++)
                for (int S = 0; S < M; S++)
                    if ((S >> i & 1) == 0)
                        g[S][T] -= g[S | (1 << i)][T];
        int res = 0;
        for (int S = 0; S < M; S++) {
            for (int T = 0; T < M; T++) {
                if ((S & ~T) >  0) res += g[S][T];
            }
        }
        return res;
    }
    private void lcm(int[] a, int m, int M, long[] lcmA) {
        for (int S = 0; S < M; S++) {
            lcmA[S] = 1;
            for (int i = 0; i < m; i++)
                if ((S >> i & 1) == 1) {
                    lcmA[S] = MathUtils.lcm(a[i], lcmA[S]);
                    if (lcmA[S] > Integer.MAX_VALUE) lcmA[S] = Integer.MAX_VALUE;
                }
        }
    }
}
