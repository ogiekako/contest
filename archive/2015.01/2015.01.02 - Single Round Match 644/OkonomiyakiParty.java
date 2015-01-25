package src;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class OkonomiyakiParty {
    int MOD = (int) (1e9+7);
    public int count(int[] osize, int M, int K) {
        int N = osize.length;
        long[][] C = MathUtils.combinationMod(N + 1, MOD);
        Arrays.sort(osize);
        long res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (osize[j] - osize[i] <= K) {
                    res = (res + C[j-i-1][M-2]) % MOD;
                }
            }
        }
        return (int)res;
    }
}
