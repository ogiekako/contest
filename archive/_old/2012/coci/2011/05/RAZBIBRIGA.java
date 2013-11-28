package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class RAZBIBRIGA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long[][] cnt = new long[26][26];
        for (int i = 0; i < n; i++) {
            String s = in.next();
            cnt[s.charAt(0) - 'A'][s.charAt(s.length() - 1) - 'A']++;
        }
        int[][] cnt2 = new int[26][26];
        long sum = 0;
        for (int a = 0; a < 26; a++)
            for (int b = 0; b < 26; b++)
                for (int c = 0; c < 26; c++)
                    for (int d = 0; d < 26; d++) {
                        cnt2[a][b] = cnt2[a][c] = cnt2[b][d] = cnt2[c][d] = 0;

                        long res = 1;
                        res *= cnt[a][b] - cnt2[a][b];
                        cnt2[a][b]++;
                        res *= cnt[a][c] - cnt2[a][c];
                        cnt2[a][c]++;
                        res *= cnt[b][d] - cnt2[b][d];
                        cnt2[b][d]++;
                        res *= cnt[c][d] - cnt2[c][d];
                        cnt2[c][d]++;

                        sum += res;
                    }
        out.println(sum);
    }
}
