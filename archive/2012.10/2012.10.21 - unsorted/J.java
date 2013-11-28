package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class J {
    int MOD = 1000003;
    long[] inv = new long[100];
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int i = 1; i < 100; i++) inv[i] = MathUtils.inverse(i, MOD);
        int H = in.nextInt(), T = in.nextInt();
        int[] ts = new int[100], hs = new int[100];
        for (int i = 2; i < 100; i++) {
            ts[i] = (int) Math.sqrt(i + 1e-9);
            hs[i] = i - ts[i];
        }
        long[][][] way1 = new long[100][100][H];
        way1[0][0][0] = 1;
        for (int t = 0; t < 100; t++)
            for (int k = 0; k < 100; k++)
                for (int j = 0; j < H; j++)
                    if (way1[t][k][j] > 0) {
                        for (int D = 2; D < ts.length; D++) {
                            int ni = t + ts[D];
                            int nj = j + hs[D];
                            if (nj < H) {
                                way1[ni][k + 1][nj] += way1[t][k][j];
                                if (way1[ni][k + 1][nj] >= MOD) way1[ni][k + 1][nj] -= MOD;
                            }
                        }
                    }
        long[][] way = new long[100][100];
        for (int i = 0; i < 100; i++)
            for (int k = 0; k < 100; k++)
                for (int j = 0; j < H; j++) {
                    way[i][k] += way1[i][k][j];
                    if (way[i][k] >= MOD) way[i][k] -= MOD;
                }

        long res = 0;
        for (int t = 0; t <= T && t < 100; t++)
            for (int k = 0; k < 100; k++)
                if (way[t][k] > 0) {
//            debug(t,k,way[t][k]);
//            debug(T-t+1,k+1,comb(T-t+1,k+1));
                    res = (res + comb(T - t + k + 1, k + 1) * way[t][k]) % MOD;
                }
        out.println(res);
    }
//    static void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
//    }

    private long comb(int n, int k) {
//        if(n<k)return 1;
        long res = 1;
        for (int i = 0; i < k; i++) {
            res = res * (n - i) % MOD;
            res = res * inv[i + 1] % MOD;
        }
        return res;
    }
}
