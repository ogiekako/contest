package tmp;

import java.util.Arrays;

public class Stamp {
    int n;
    String s;
    int L;
    String col = "RGB";

    public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
        s = desiredColor;
        n = s.length();
        long res = Integer.MAX_VALUE;
        for (L = 1; L <= n; L++) {
            res = Math.min(res, stampCost * L + (long) solve() * pushCost);
        }
        return (int) res;
    }

//    int[][] dp;

    private int solve() {
//        dp = new int[n][n + 1];
//        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
//        return rec(0, n);

        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < n; i++)
            if (dp[i] < INF) {
                for (int j = L; i + j <= n; j++) {
                    for (char c : col.toCharArray()) {
                        boolean ok = true;
                        for (int k = i; k < i + j; k++) {
                            if (s.charAt(k) != '*' && s.charAt(k) != c) ok = false;
                        }
                        if (ok) {
                            dp[i + j] = Math.min(dp[i + j], dp[i] + (j + L - 1) / L);
                        }
                    }
                }
            }
        return dp[n];
    }

    int INF = 100000;

//    private int rec(int from, int to) {
////        System.err.println(from + " " + to);
//        if (from == to) return 0;
//        int res = dp[from][to];
//        if (res >= 0) return res;
//        res = INF;
//        for (int i = from; i + L <= to; i++) {
//            for (char c : col.toCharArray()) {
//                for (int nTo = i; nTo < i + L; nTo++) {
//                    if (s.charAt(nTo) != '*' && s.charAt(nTo) != c) continue;
//                    int nFrom = nTo+1;
//                    while (nFrom <= i + L && (s.charAt(nFrom-1) == '*' || s.charAt(nFrom-1) == c)) {
//                        res = Math.min(res, 1 + rec(from, nTo) + rec(nFrom , to));
//                        nFrom++;
//                    }
//                }
//            }
//        }
//        return dp[from][to] = res;
//    }
}
