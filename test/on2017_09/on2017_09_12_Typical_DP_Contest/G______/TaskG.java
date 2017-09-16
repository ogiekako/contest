package on2017_09.on2017_09_12_Typical_DP_Contest.G______;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskG {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        long K = in.nextLong();
        int n = s.length();

        int[][] ids = new int[26][];
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            ids[i] = new int[cnt[i] + 1];
            ids[i][cnt[i]] = n;
            cnt[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int j = s.charAt(i) - 'a';
            ids[j][cnt[j]++] = i;
        }

        long INF = K + 1;
        long[] dp = new long[n + 1];

        for (int i = 0; i < 26; i++) {
            cnt[i] = ids[i].length - 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            for (int j = 0; j < 26; j++) {
                dp[i] += dp[ids[j][cnt[j]]];
                if (dp[i] >= INF) dp[i] = INF;
            }
            cnt[s.charAt(i) - 'a']--;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; K > 0; ) {
            int j;
            for (j = 0; j < 26; j++) {
                if (dp[ids[j][cnt[j]]] < K) {
                    K -= dp[ids[j][cnt[j]]];
                } else {
                    res.append((char)('a' + j));
                    K--;
                    break;
                }
            }
            if (j == 26) {
                out.println("Eel");
                return;
            }
            for(int ni = ids[j][cnt[j]]; i <= ni; i++) {
                cnt[s.charAt(i) - 'a']++;
            }
        }
        out.println(res);
    }
}
