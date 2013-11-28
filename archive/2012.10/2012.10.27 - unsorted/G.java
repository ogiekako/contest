package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class G {
    int INF = 1000;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        String s = in.next();
        int[][] mx = new int[n + 1][2];
        int[][] mn = new int[n + 1][2];
        for (int i = 0; i <= n; i++) Arrays.fill(mx[i], 0);
        for (int i = 0; i <= n; i++) Arrays.fill(mn[i], INF);
        mn[0][0] = mn[0][1] = 1;
        mx[0][0] = mx[0][1] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++) {
                    char c = k == 0 ? '+' : '-';
                    if (s.charAt(i) == c || s.charAt(i) == '?') {
                        mn[i + 1][k] = Math.min(mn[i + 1][k], mn[i][j] + (j == k ? 0 : 1));
                        mx[i + 1][k] = Math.max(mx[i + 1][k], mx[i][j] + (j == k ? 0 : 1));
                    }
                }
        }
        int res1 = Math.max(mx[n][0], mx[n][1]);
        int res2 = Math.min(mn[n][0], mn[n][1]);
        out.printf("%d %d\n", res2, res1);
    }
}
