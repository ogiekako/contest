package tmp;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Euler161 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int w = in.nextInt(), h = in.nextInt();
        long[][] dp = new long[2][1 << 2 * w];
        dp[0][0] = 1;
        int cur = 0, nxt = 1;
        int[] dx = {0, 2, 1, 1, 1, -1};
        int[] msk = {
                1 << 0 | 1 << w | 1 << 2 * w,
                1 << 0 | 1 << 1 | 1 << 2,
                1 << 0 | 1 << 1 | 1 << w,
                1 << 0 | 1 << 1 | 1 << w + 1,
                1 << 0 | 1 << w | 1 << w + 1,
                1 << 0 | 1 << w - 1 | 1 << w
        };
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                Arrays.fill(dp[nxt], 0);
                for (int k = 0; k < 1 << 2 * w; k++) {
                    if ((k & 1) == 1) {
                        dp[nxt][k >> 1] += dp[cur][k];
                    } else {
                        for (int l = 0; l < dx.length; l++) {
                            int x = j + dx[l];
                            if (x < 0 || x >= w) continue;
                            if ((msk[l] & k) > 0) continue;
                            int nk = msk[l] | k;
                            nk >>= 1;
                            dp[nxt][nk] += dp[cur][k];
                        }
                    }
                }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
        out.println(dp[cur][0]);
    }
}
