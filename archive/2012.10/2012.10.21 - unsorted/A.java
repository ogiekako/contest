package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), T = in.nextInt();
        int[] P = new int[N];
        for (int i = 0; i < N; i++) P[i] = in.nextInt();
        int[][] s = new int[N][];
        for (int i = 0; i < N; i++) {
            s[i] = new int[P[i]];
            for (int j = 0; j < P[i]; j++) s[i][j] = in.nextInt();
        }
        int[][] t = new int[N][];
        for (int i = 0; i < N; i++) {
            t[i] = new int[P[i]];
            for (int j = 0; j < P[i]; j++) t[i][j] = in.nextInt();
        }
        int res = 0;
        int[] cur = new int[N];
        while (T > 0) {
            int p = -1;
            for (int i = 0; i < N; i++)
                if (cur[i] < P[i] && (p == -1 || s[i][cur[i]] < s[p][cur[p]])) {
                    p = i;
                }
            if (p == -1) break;
            T -= t[p][cur[p]];
            if (T >= 0) {
                if (cur[p] > 0) res -= s[p][cur[p] - 1];
                res += s[p][cur[p]];
            }
            cur[p]++;
        }
        out.println(res);
    }
}
