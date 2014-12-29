package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        int tt=0;
        while (T-- > 0) {
            debug(++tt);
            int N = in.nextInt();
            int[] x = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = in.nextInt();
            }
            Arrays.sort(x);
            int res = solve(N, x);
            out.println(res);
        }
    }

    long[][] memo;
    int[] x;
    int P = 10007;
    int[] inv = new int[P];
    int[] L;

    private int solve(int N, int[] x) {
        for (int i = 1; i < P; i++) {
            inv[i] = (int) MathUtils.inverse(i, P);
        }
        this.x = x;
        L = new int[N];
        memo = new long[N + 1][N + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                memo[i][j] = -1;
            }
        }
        L[0] = 0;
        for (int i = 1; i < N; i++) {
            if (x[i] != x[i - 1]) L[i] = i;
            else L[i] = L[i - 1];
        }
        long res = recur(0, N);
        return (int) res;
    }

    void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private long recur(int l, int r) {
        if (r == l) return 0;
        if (memo[l][r] >= 0) {
            return memo[l][r];
        }
        int den = r - l;
        long num = 0;
        for (int i = l; i < r; i++) {
            num += recur(l, Math.max(l, L[i])) + recur(Math.max(l + 1, L[i] + 1), r);
        }
        long res = ((num * inv[den]) + (r - l)) % P;
        return memo[l][r] = res;
    }
}
