package on2012_8_15.leftrightdigitsgame2;



// Paste me into the FileEdit configuration dialog

public class LeftRightDigitsGame2 {
    public String minNumber(String digits, String lowerBound) {
        this.digits = digits; this.lowerBound = lowerBound;
        N = digits.length();
        vis = new boolean[N][N + 1];
        dp = new String[N][N + 1][3];
        for (int i = 0; i < N; i++) for (int j = 0; j < N + 1; j++) for (int k = 0; k < 3; k++) dp[i][j][k] = "";
        rec(0, N);
        if (dp[0][N][1].length() > 0) return dp[0][N][1];
        return dp[0][N][2];
    }

    private void rec(int l, int r) {
        if (vis[l][r]) return;
        vis[l][r] = true;
        if (r - l == 1) {
            char a = digits.charAt(0), b = lowerBound.charAt(l);
            if (a < b) {
                dp[l][r][0] = "" + a;
            } else if (a == b) {
                dp[l][r][1] = "" + a;
            } else {
                dp[l][r][2] = "" + a;
            } return;
        }
        rec(l + 1, r);
        rec(l, r - 1);
        // left
        char a = digits.charAt(r - l - 1);
        char b = lowerBound.charAt(l);
        if (a < b) {
            for (int k = 0; k < 3; k++) {
                String sub = dp[l + 1][r][k];
                if (sub.length()>0) {
                    dp[l][r][0] = min(dp[l][r][0], a + dp[l + 1][r][k]);
                }
            }
        } else if (a == b) {
            for (int k = 0; k < 3; k++) {
                String sub = dp[l + 1][r][k];
                if (sub.length()>0) {
                    dp[l][r][k] = min(dp[l][r][k], a + dp[l + 1][r][k]);
                }
            }
        } else {
            for (int k = 0; k < 3; k++) {
                String sub = dp[l + 1][r][k];
                if (sub.length()>0) {
                    dp[l][r][2] = min(dp[l][r][2], a + dp[l + 1][r][k]);
                }
            }
        }
        // right
        a = digits.charAt(r - l - 1);
        b = lowerBound.charAt(r - 1);
        for (int k = 0; k < 3; k++) {
            String sub = dp[l][r - 1][k];
            if (sub.length()>0) {
                if (k != 1)
                    dp[l][r][k] = min(dp[l][r][k], sub + a);
                else {
                    if (a < b) {
                        dp[l][r][0] = min(dp[l][r][0], sub + a);
                    } else if (a == b) {
                        dp[l][r][1] = min(dp[l][r][1], sub + a);
                    } else {
                        dp[l][r][2] = min(dp[l][r][2], sub + a);
                    }
                }
            }
        }
    }

    private String min(String a, String b) {
        if (a.length()==0) return b;
        if (b.length()==0) return a;
        if (a.length() != b.length()) throw new AssertionError();
        return a.compareTo(b) < 0 ? a : b;
    }

    int N;
    String digits, lowerBound;
    String[][][] dp;
    boolean[][] vis;


}

