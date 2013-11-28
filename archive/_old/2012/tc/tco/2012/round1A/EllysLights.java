package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class EllysLights {
    int N;
    int[] color;
    boolean[] on, off;
    boolean[][] same, diff;

    public int getMinimum(String initial, String[] switches) {
        N = switches.length;
        on = new boolean[N]; off = new boolean[N]; same = new boolean[N][N]; diff = new boolean[N][N];
        for (int i = 0; i < initial.length(); i++) {
            int a = -1, b = -1;
            for (int j = 0; j < N; j++)
                if (switches[j].charAt(i) == 'Y') {
                    if (a == -1) a = j;
                    else b = j;
                }
            if (a == -1) {
                if (initial.charAt(i) == 'Y') return -1;
            } else if (b == -1) {
                if (initial.charAt(i) == 'Y') on[a] = true;
                else off[a] = true;
            } else {
                if (initial.charAt(i) == 'Y') {
                    diff[a][b] = diff[b][a] = true;
                } else {
                    same[a][b] = same[b][a] = true;
                }
            }
        }
        color = new int[N];
        Arrays.fill(color, -1);
        for (int i = 0; i < N; i++) if (off[i]) dfs(i, 0);
        for (int i = 0; i < N; i++) if (on[i]) dfs(i, 1);
        int C = 2;
        for (int i = 0; i < N; i++)
            if (color[i] == -1) {
                dfs(i, C);
                C += 2;
            }
        for (int i = 0; i < N; i++) if (off[i] && color[i] != 0) return -1;
        for (int i = 0; i < N; i++) if (on[i] && color[i] != 1) return -1;
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) if (same[i][j] && color[i] != color[j]) return -1;
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) if (diff[i][j] && color[i] == color[j]) return -1;
        int[] cnt = new int[C];
        for (int i = 0; i < N; i++) cnt[color[i]]++;
        int res = cnt[1];
        for (int i = 2; i < C; i += 2) res += Math.min(cnt[i], cnt[i + 1]);
        return res;
    }

    private void dfs(int i, int c) {
        if (color[i] != -1) return;
        color[i] = c;
        for (int j = 0; j < N; j++) {
            if (same[i][j]) dfs(j, c);
            if (diff[i][j]) dfs(j, c ^ 1);
        }
    }
}
