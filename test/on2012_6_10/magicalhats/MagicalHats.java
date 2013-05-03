package on2012_6_10.magicalhats;



// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

/*
HHH
HHH
H.H

 */
public class MagicalHats {
    int n, m;
    int H, G;
    boolean[] valid;
    int[] x, y;
    int[] three;
    int[] memo;
    int INF = 1 << 28;

    public int findMaximumReward(String[] board, int[] coins, int numGuesses) {
        Arrays.sort(coins);
        int C = coins.length;
        G = numGuesses;
        n = board.length; m = board[0].length();
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) if (board[i].charAt(j) == 'H') H++;
        x = new int[H]; y = new int[H];
        H = 0;
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) if (board[i].charAt(j) == 'H') { x[H] = i; y[H] = j; H++; }
        valid = new boolean[1 << H];
        for (int i = 0; i < 1 << H; i++) if (Integer.bitCount(i) == C && isValid(i)) valid[i] = true;
        three = new int[H + 1];
        for (int i = 0; i <= H; i++) three[i] = i == 0 ? 1 : three[i - 1] * 3;
        memo = new int[three[H]];
        Arrays.fill(memo, -1);
        int r = recur(0, 0);
        if (r >= INF) return -1;
        int res = 0;
        for (int i = 0; i < r; i++) res += coins[i];
        return res;
    }

    private int recur(int key, int opened) {
        if (memo[key] >= 0) return memo[key];
        if (opened == H) {
            int coin = 0;
            for (int i = 0; i < H; i++) if (key / three[i] % 3 == 2) coin |= 1 << i;
            return memo[key] = valid[coin] ? 0 : INF;
        }
        int res = 0;
        for (int i = 0; i < H; i++) if (key / three[i] % 3 == 0) {
            res = Math.max(res, Math.min(
                    recur(key + three[i], opened + 1),
                    recur(key + three[i] * 2, opened + 1) + (opened < G ? 1 : 0)
            ));
        }
        return memo[key] = res;
    }

    private boolean isValid(int mask) {
        int[] row = new int[n], col = new int[m];
        for (int i = 0; i < H; i++)
            if ((mask >> i & 1) == 0) {
                row[x[i]]++;
                col[y[i]]++;
            }
        for (int i = 0; i < n; i++) if (row[i] % 2 == 1) return false;
        for (int i = 0; i < m; i++) if (col[i] % 2 == 1) return false;
        return true;
    }
}

