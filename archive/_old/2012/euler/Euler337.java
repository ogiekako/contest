package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.io.PrintWriter;

public class Euler337 {
    int D = 9;
    long MOD = MathUtils.power(10, D);

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long res = 0;
        for (int i = 0; i < n; i++) {
            long N = in.nextLong();
            res += solve(N);
            if (res >= MOD) res -= MOD;
        }
        out.println(res);
    }

    private long solve(long n) {
        int MX = 100;
        long[][][] dp = new long[D + 1][10][MX];// id,last,sum -> way
        dp[0][0][0] = 1;
        long res = 0;
        long ten = 1;
        long[] way = new long[MX];
        for (int i = 0; i < MX; i++) {
            way[i] = way(n - i);
        }
        for (int i = 0; i < D; i++) {
            for (int last = 0; last <= 9; last++) {
                for (int sum = 0; sum < MX; sum++)
                    if (dp[i][last][sum] > 0) {
                        for (int nLast = 1; nLast <= 9; nLast++) {
                            dp[i + 1][nLast][sum + nLast] += dp[i][last][sum];
                            if (dp[i + 1][nLast][sum + nLast] >= MOD) dp[i + 1][nLast][sum + nLast] -= MOD;
                        }
                    }
            }
            for (int last = 0; last <= 9; last++)
                for (int sum = 0; sum < MX; sum++)
                    if (dp[i + 1][last][sum] > 0) {
                        res += way[sum] * dp[i + 1][last][sum] % MOD * ten % MOD * last % MOD;
                        if (res >= MOD) res -= MOD;
                    }
            ten *= 10;
        }
        return res;
    }

    private long way(long n) {
        if (n < 0) return 0;
        long[][] A = new long[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                A[i][j] = i == 0 ? 1 : j + 1 == i ? 1 : 0;
            }
        return Matrix.powered(A, n, (int) MOD)[0][0];
    }
}
