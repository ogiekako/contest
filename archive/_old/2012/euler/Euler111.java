package tmp;

import net.ogiekako.algorithm.misc.iteration.Iteration;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler111 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long res = 0;
        for (int d = 0; d <= 9; d++) {
            res += solve(n, d);
        }
        out.println(res);
    }

    private long solve(int n, int d) {
        for (int m = n; ; m--) {
            long res = solve(n, d, m);
            if (res > 0) {
                return res;
            }
        }
    }

    long res;
    private long solve(final int n, final int d, int m) {
        res = 0;
        Iteration.iterateBitCombination(n, m, new Iteration.IntFunc() {
            public void doIt(int comb) {
                res += dfs(n, comb, d, 0L, 0);
            }
        });
        return res;
    }

    private long dfs(int n, int msk, int d, long number, int i) {
        if (i == n) {
            if (MathUtils.isPrime(number)) {
                return number;
            }
            return 0;
        }
        long res = 0;
        for (int k = 0; k <= 9; k++) {
            if (k == 0 && i == 0) continue;
            if ((msk >> i & 1) == 1 && k != d) continue;
            res += dfs(n, msk, d, number * 10 + k, i + 1);
        }
        return res;
    }
}
