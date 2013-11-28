package tmp;

public class BallRemoval {
    boolean[][] dp = new boolean[60][60];
    boolean[][] vis = new boolean[60][60];
    String label;
    int n;
    public String canLeave(String label) {
        this.label = label;
        n = label.length();
        String res = "";
        for (int i = 0; i < n; i++) {
            if (rec(0, i) && rec(i + 1, n)) res += 'o';
            else res += '.';
        }
        return res;
    }

    private boolean rec(int s, int t) {
        if (vis[s][t]) return dp[s][t];
        vis[s][t] = true;
        if (t == s) return dp[s][t] = true;
        for (int i = s; i < t; i++)
            for (int j = i + 1; j < t; j++) {
                if ((i > 0 && label.charAt(i) == '>' || j < n - 1 && label.charAt(j) == '<') && rec(s, i) && rec(i + 1, j) && rec(j + 1, t))
                    dp[s][t] = true;
            }
        return dp[s][t];
    }
}
