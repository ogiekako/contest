package on2014_01.on2014_01_11_TopCoder_SRM__603.PairsOfStrings;



import net.ogiekako.algorithm.math.MathUtils;
public class PairsOfStrings {
    int MOD = (int) (1e9 + 7);
    public int getNumber(int n, int k) {
        long[] ds = MathUtils.divisors(n);
        long res = 0;
        for (long d : ds)
            for (long e : ds)
                if (d % e == 0) {
                    res = (res + d * MathUtils.powMod(k, e, MOD) * MathUtils.mobius(d / e)) % MOD;
                }
        return (int) (res + MOD) % MOD;
    }
}
