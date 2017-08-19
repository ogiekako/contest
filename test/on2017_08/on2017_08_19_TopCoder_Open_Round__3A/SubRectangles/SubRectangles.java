package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.SubRectangles;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class SubRectangles {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    public int countWays(int H, int W, int H2, int W2) {
        long[] sameRow = sameRow(H, W, H2, W2, false);
        long[] sameCol = sameRow(W, H, W2, H2, true);
        long res = 0;
        for (int i = 0; i < 1 << H2 * W2; i++) {
            for (int ind = i; ; ind = (ind - 1) & i) {
                int row = (1 << H2 * W2) - 1 ^ i;
                int col = i ^ ind;

                int c = Integer.bitCount(ind);
                int coeff = 1 << c;
                if (c % 2 != 0) {
                    coeff = -coeff;
                }
                res = (res + coeff * sameRow[row] % MOD * sameCol[col]) % MOD;

                if (ind == 0) break;
            }
        }
        return (int) ((res + MOD) % MOD);
    }

    long[][] C = MathUtils.combinationMod(5, MOD);

    private long[] sameRow(int h, int w, int h2, int w2, boolean flip) {
        long[] ways = new long[1 << h2 * w2];
        for (int i = 0; i < 1 << h2 * w2; i++) {
            ways[i] = 1;
            for (int j = 0; j < h2; j++) {
                int cnt = 0;
                for (int k = 0; k < w2; k++) {
                    if (i << 31 - (!flip ? j * w2 + k : k * h2 + j) < 0) {
                        cnt++;
                    }
                }
                long val = 0;
                for (int sum = 0; sum < cnt + 1; sum++) {
                    val += MathUtils.powMod(C[cnt][sum], (h - j + h2 - 1) / h2, MOD);
                }
                val %= MOD;
                ways[i] = ways[i] * val % MOD;
            }
        }
        return ways;
    }
}
