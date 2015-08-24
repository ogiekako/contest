package on2015_08.on2015_08_24_TopCoder_SRM__603.PairsOfStrings;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class PairsOfStrings {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    public int getNumber(int n, int k) {
        long[] ds = MathUtils.divisors(n);
        long[] g = new long[ds.length];
        long[] u = new long[ds.length];
        for (int i = 0; i < ds.length; i++) {
            g[i] = MathUtils.powMod(k, ds[i], MOD);
            u[i] = MathUtils.mobius(ds[i]);
        }
        long res = 0;
        for (long q : ds) {
            long f = 0;
            for (long p : ds) {
                if (q % p != 0) continue;
                f = (f + g[Arrays.binarySearch(ds, p)] * u[Arrays.binarySearch(ds, q/p)]) % MOD;
                if (f < 0) f += MOD;
            }
            res = (res + f * q) % MOD;
        }
        return (int) res;
    }
}
