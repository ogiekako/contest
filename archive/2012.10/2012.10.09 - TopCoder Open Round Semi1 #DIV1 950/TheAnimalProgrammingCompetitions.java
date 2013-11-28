package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class TheAnimalProgrammingCompetitions {
    // 2012/10/09 3:04 ~
    int MOD = 1234567891;
    int[][][] dp = new int[50][400][400];
    int n;
    int[] a;
    long[][] C = MathUtils.combinationMod(400, MOD);
    long[] fact = MathUtils.factorialMod(400, MOD);

    public int find(int[] rabbits) {
        n = rabbits.length;
        a = rabbits;
        for (int i = 0; i < dp.length; i++) for (int j = 0; j < dp[i].length; j++) Arrays.fill(dp[i][j], -1);
        return (int) recur(0, 0, 0);
    }

    private long recur(int pos, int rem0, int rem1) {
        if (pos == n && rem0 == 0) return 1;
        if (pos == n) return 0;
        int res = dp[pos][rem0][rem1];
        if (res >= 0) return res;
        res = 0;
        for (int usePos = 0; usePos < Math.min(rem1, a[pos]) + 1; usePos++)
            for (int useRem0 = 0; useRem0 < Math.min(4, rem0) + 1; useRem0++) {
                long t = 1;
                t = t * C[a[pos]][usePos] % MOD * C[rem1][usePos] * fact[usePos] % MOD;
                t = t * C[4][useRem0] % MOD * C[rem0][useRem0] * fact[useRem0] % MOD;
                res = (int) ((res + recur(pos + 1, rem0 - useRem0 + a[pos] - usePos, rem1 - usePos + 4 - useRem0) * t) % MOD);
            }
        return dp[pos][rem0][rem1] = res;
    }
}
