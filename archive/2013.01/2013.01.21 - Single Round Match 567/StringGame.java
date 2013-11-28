package tmp;

public class StringGame {
    public int[] getWinningStrings(String[] S) {
        boolean[] res = new boolean[S.length];
        int len = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = solve(S, i);
            if (res[i]) len++;
        }
        int[] res2 = new int[len];
        len = 0;
        for (int i = 0; i < res.length; i++) if (res[i]) res2[len++] = i;
        return res2;
    }

    private boolean solve(String[] S, int x) {
        int n = S.length;
        int m = S[0].length();
        int[][] cnt = new int[n][26];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) cnt[i][S[i].charAt(j) - 'a']++;
        boolean[] win = new boolean[n];
        boolean[] used = new boolean[26];
        win[x] = true;
        for (; ; ) {
            boolean ok = true;
            for (boolean b : win) if (!b) ok = false;
            if (ok) return true;
            boolean changed = false;
            for (int i = 0; i < 26; i++)
                if (!used[i]) {
                    ok = true;
                    for (int j = 0; j < n; j++)
                        if (!win[j]) {
                            if (cnt[x][i] < cnt[j][i]) ok = false;
                        }
                    if (ok) {
                        changed = true;
                        used[i] = true;
                        for (int j = 0; j < n; j++)
                            if (!win[j]) {
                                if (cnt[x][i] > cnt[j][i]) win[j] = true;
                            }
                    }
                }
            if (!changed) return false;
        }
    }
}
