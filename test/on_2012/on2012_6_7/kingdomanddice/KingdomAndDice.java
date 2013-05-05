package on_2012.on2012_6_7.kingdomanddice;


// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class KingdomAndDice {
    public double newFairness(int[] firstDie, int[] secondDie, int X) {
        Arrays.sort(secondDie);
        int N = firstDie.length;
        int[] canUse = new int[N + 1];
        canUse[0] = N;
        for (int i = 0; i < N; i++) {
            for (int x = secondDie[i] + 1; ; x++) {
                if (contains(secondDie, x) || x > X) break;
                if (contains(firstDie, x)) continue;
                canUse[i + 1]++;
                if (canUse[i + 1] > N) break;
            }
        }
        int win = 0;
        int zero = 0;
        for (int i = 0; i < N; i++) {
            if (firstDie[i] == 0) zero++;
            for (int j = 0; j < N; j++) if (firstDie[i] > secondDie[j]) win++;
        }
        boolean[][][] dp = new boolean[2][zero + N + 2][N * N * 2 + 10];
        dp[0][0][win] = true;
        int cur = 0, nxt = 1;
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < zero + 1; j++) for (int k = 0; k < N * N + 1; k++) dp[nxt][j][k] = false;
            for (int j = 0; j < zero + 1; j++)
                for (int k = 0; k < N * N + 1; k++)
                    if (dp[cur][j][k]) {
                        for (int l = 0; l < canUse[i] + 1; l++) dp[nxt][j + l][k + l * i] = true;
                    }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        for (int i = 0; ; i++) {
            int t = N * N - i;
            if (t >= 0 && t % 2 == 0 && dp[cur][zero][t / 2]) return (double) t / 2 / N / N;
            t = N * N + i;
            if (t % 2 == 0 && dp[cur][zero][t / 2]) return (double) t / 2 / N / N;
        }
    }

    private boolean contains(int[] xs, int x) {
        for (int y : xs) if (x == y) return true;
        return false;
    }


}

