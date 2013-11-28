package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        r = in.nextInt();
        dp = new int[n + 1][n + 1][n + 1][n + 1][m + 1][m + 1];
        for (int i = 0; i < n + 1; i++)
            for (int j = 0; j < n + 1; j++)
                for (int k = 0; k < n + 1; k++)
                    for (int l = 0; l < n + 1; l++)
                        for (int o = 0; o < m + 1; o++) for (int p = 0; p < m + 1; p++) dp[i][j][k][l][o][p] = U;
        try {
            int res = solve(1, 1, 1, 1, m, m);
            if (res >= 1) out.printf("First\n%d\n", res);
            else out.printf("Second\n%d\n", -res);
        } catch (Loop e) {
            out.println("Infinite");
        }
    }

    private int solve(int x1, int y1, int x2, int y2, int m1, int m2) {
        if (dp[x1][y1][x2][y2][m1][m2] == V) throw new Loop();
        if (dp[x1][y1][x2][y2][m1][m2] != U) return dp[x1][y1][x2][y2][m1][m2];
        dp[x1][y1][x2][y2][m1][m2] = V;
        if (x1 == 0 && y1 == 0) {
            return dp[x1][y1][x2][y2][m1][m2] = 0;
        }
        int res = 0;
        if (x1 != 0 && x2 != 0) {
            int tmp = solve(f(x2 + x1), y2, x1, y1, m2, m1);
            res = g(res, tmp);
        }
        if (x1 != 0 && y2 != 0) {
            int tmp = solve(f(y2 + x1), x2, x1, y1, m2, m1);
            res = g(res, tmp);
        }
        if (y1 != 0 && x2 != 0) {
            int tmp = solve(f(x2 + y1), y2, x1, y1, m2, m1);
            res = g(res, tmp);
        }
        if (y1 != 0 && y2 != 0) {
            int tmp = solve(f(y2 + y1), x2, x1, y1, m2, m1);
            res = g(res, tmp);
        }

        if (m1 > 0 && x1 == 0) {
            for (int x = 1; x < y1; x++) {
                int tmp = solve(y2, x2, x, y1 - x, m2, m1 - 1);
                res = g(res, tmp);
            }
        }
        if (m1 > 0 && y1 == 0) {
            for (int x = 1; x < x1; x++) {
                int tmp = solve(y2, x2, x, x1 - x, m2, m1 - 1);
                res = g(res, tmp);
            }
        }

        return dp[x1][y1][x2][y2][m1][m2] = res;
    }

    private int g(int res, int tmp) {
        if (tmp <= 0) {
            tmp = -tmp + 1;
        } else {
            tmp = -tmp - 1;
        }
        if (res <= 0 && (tmp < res || tmp > 0)) res = tmp;
        else if (res > 0 && 0 < tmp && tmp < res) res = tmp;
        return res;
    }

    private int f(int X) {
        if (r == 0) return X >= n ? 0 : X;
        return X % n;
    }

    class Loop extends RuntimeException {
    }

    int n, m, r;
    int U = 1 << 28;
    int V = U + 1;
    int[][][][][][] dp;
}
