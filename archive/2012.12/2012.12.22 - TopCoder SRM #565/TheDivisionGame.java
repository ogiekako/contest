package tmp;

import net.ogiekako.algorithm.math.MathUtils;

public class TheDivisionGame {
    public long countWinningIntervals(int L, int R) {
        R++;
        int N = R - L;
        int[] g = MathUtils.generateNumberOfPrimeDivisorsWithMultiplicity(L, R);
//        int[] g = new int[N];
//        PrimeDecomposition[] pds = MathUtils.factorize(L,R);
//        for (int i = 0; i < g.length; i++)g[i] = pds[i].numberOfPrimeDivisorsWithMultiplicity();
        long[] cnt = new long[100];
        long res = (long) N * (N + 1) / 2;
        int xor = 0;
        cnt[xor]++;
        for (int i = 0; i < N; i++) {
            xor ^= g[i];
            res -= cnt[xor];
            cnt[xor]++;
        }
        return res;
    }
}
