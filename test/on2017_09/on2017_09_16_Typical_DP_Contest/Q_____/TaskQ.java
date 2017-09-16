package on2017_09.on2017_09_16_Typical_DP_Contest.Q_____;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskQ {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), L = in.nextInt();
        boolean[][] contains = new boolean[9][1 << 8];
        boolean[][] extendable = new boolean[9][1 << 8];

        for (int i = 0; i < N; i++) {
            String s = in.next();
            int mask = Integer.parseInt(s, 2);
            for (int j = 0; j < 1 << 8; j++) {
                if ((j & (1 << s.length()) - 1) == mask)
                    contains[s.length()][j] = true;
                for (int k = 1; k <= s.length(); k++) {
                    if ((j & (1 << s.length() - k) - 1) == (mask >> k))
                        extendable[s.length() - k][j] = true;
                }
            }
        }

        Mint.set1e9_7();
        Mint[][][] dp = new Mint[2][1 << 8][1 << 8];
        ArrayUtils.fill(dp, Mint.ZERO);
        int cur = 0, nxt = 1;
        dp[cur][0][1] = Mint.ONE;
        for (int i = 0; i < L; i++) {
            ArrayUtils.fill(dp[nxt], Mint.ZERO);
            for (int j = 0; j < 1 << 8; j++) {
                for (int k = 0; k < 1 << 8; k++) {
                    for (int d = 0; d <= 1; d++) {
                        int nj = (j << 1 | d) & 255;
                        int nk = 0;
                        for (int l = 0; l < 8; l++) {
                            if (k << 31 - l < 0) {
                                if (extendable[l + 1][nj]) {
                                    nk |= 1 << l + 1;
                                }
                                if (contains[l + 1][nj]) {
                                    nk |= 1;
                                }
                            }
                        }
                        dp[nxt][nj][nk] = dp[nxt][nj][nk].add(dp[cur][j][k]);
                    }
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        Mint res = Mint.ZERO;
        for (int i = 0; i < 1 << 8; i++) {
            for (int j = 0; j < 1 << 8; j++) {
                if ((j & 1) == 1) {
                    res = res.add(dp[cur][i][j]);
                }
            }
        }
        out.println(res);
    }
}
