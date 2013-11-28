package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class KingXMagicSpells {
    public double expectedNumber(int[] ducks, int[] spellOne, int[] spellTwo, int K) {
        double res = 0;
        for (int bit = 0; bit < 32; bit++) {
            res += (1 << bit) * solve(ducks, spellOne, spellTwo, K, bit);
        }
        return res;
    }

    private double solve(int[] ducks, int[] spellOne, int[] spellTwo, int K, int bit) {
        int n = ducks.length;
        double[][] dp = new double[2][n];
        for (int i = 0; i < n; i++) dp[0][i] = ducks[i] >> bit & 1;
        int cur = 0, nxt = 1;
        for (int i = 0; i < K; i++) {
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < n; j++) {
                dp[nxt][spellTwo[j]] += dp[cur][j] * 0.5;
                dp[nxt][j] += 0.5 * ((spellOne[j] >> bit & 1) == 0 ? dp[cur][j] : (1 - dp[cur][j]));
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        return dp[cur][0];
    }


}

