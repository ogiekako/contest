package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class TaskB {
    int MOD = (int) (1e9 + 7);
    long[][] C = MathUtils.combinationMod(11, MOD);
    long[] pow2 = MathUtils.generatePowers(2, 11, MOD);
    long[] pow8 = MathUtils.generatePowers(8, 11, MOD);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int m = in.nextInt() + 1;
        int[] ms = new int[10];
        for (int i = 0; i < 10; i++) {
            ms[i] = m % 10; m /= 10;
        }
        int[] cnt = count(ms, 9);
        cnt[0]--;
        long res = 0;
        for (int c = 9; c >= 0; c--) {
            res += calc(cnt, c - 1, 6, c) * cnt[c];
        }
        res %= MOD;
        out.println(res);
    }

    private long calc(int[] cnt, int index, int rest, int capacity) {
        if (index < 0) {
            return rest == 0 && capacity > 0 ? 1 : 0;
        } else {
            long res = 0;
            long way = 1;
            for (int use = 0; use < rest + 1; use++) {
                res += calc(cnt, index - 1, rest - use, capacity - index * use) * way % MOD * C[rest][use] % MOD;
                if (res >= MOD) res -= MOD;
                way *= cnt[index] - use + MOD;
                way %= MOD;
            }
            return res;
        }
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int[] count(int[] m, int d) {// [m9..md+1 0..0, m9..md+1 md .. m0)
        if (d < 0) return new int[10];
        int[] res = count(m, d - 1);
        // [m9..md+1 0..0, m9..md+1 md 0..0)
        int c1 = 0;
        for (int i = 9; i > d; i--) c1 += cnt47(m[i]);
        for (int md = 0; md < m[d]; md++) {
            // md-1..m0
            int c2 = cnt47(md);
            for (int c3 = 0; c3 <= d; c3++) {
                long way = C[d][c3] * pow2[c3] % MOD * pow8[d - c3] % MOD;
                res[c1 + c2 + c3] += way;
                if (res[c1 + c2 + c3] >= MOD) res[c1 + c2 + c3] -= MOD;
            }
        }
        return res;
    }

    private int cnt47(int i) {
        return i == 4 || i == 7 ? 1 : 0;
    }
}
