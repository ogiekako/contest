package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class ConnectingSoldiers {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int M = in.nextInt();
        int[][] dp = new int[N + 1][];
        dp[0] = new int[]{0};
        dp[1] = new int[]{2};
        for (int i = 2; i < N + 1; i++) {
            boolean[] nxt = new boolean[M + 1];
            for (int j = 0; j < i; j++) {
                int k = i - 1 - j;
                for (int l : dp[j])
                    for (int p : dp[k]) {
                        int t = l + p + i + 1;
                        if (t <= M) nxt[t] = true;
                    }
            }
            int cnt = 0;
            for (boolean b : nxt) if (b) cnt++;
            dp[i] = new int[cnt];
            cnt = 0;
            for (int j = 0; j < M + 1; j++) if (nxt[j]) dp[i][cnt++] = j;
        }
        if (dp[N].length > 0) {
            out.println(M - dp[N][dp[N].length - 1]); return;
        }
        out.println(-1); return;
    }
}
