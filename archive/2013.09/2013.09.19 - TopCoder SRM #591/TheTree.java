package src;

import java.util.Arrays;
public class TheTree {
    public int maximumDiameter(int[] cnt) {
        for (int i = 0; i < cnt.length; i++) cnt[i] = Math.min(2, cnt[i]);
        int n = 0;
        for (int c : cnt) n += c;
        n++;
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(g[i], Integer.MAX_VALUE / 2);
        for (int i = 0; i < n; i++) {
            g[i][i] = 0;
        }
        int p = 0;
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] == 1) {
                g[p][p + 1] = g[p + 1][p] = 1;
                p++;
            } else {
                if (i == 0 || cnt[i - 1] == 1) {
                    g[p][p + 1] = g[p + 1][p] = g[p][p + 2] = g[p + 2][p] = 1;
                } else {
                    g[p - 1][p + 1] = g[p + 1][p - 1] = 1;
                    g[p][p + 2] = g[p + 2][p] = 1;
                }
                p += 2;
            }
        }
        if (p != n - 1) throw new AssertionError();
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    g[i][j] = Math.min(g[i][j], g[i][k] + g[k][j]);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) res = Math.max(res, g[i][j]);
        return res;
    }
}
