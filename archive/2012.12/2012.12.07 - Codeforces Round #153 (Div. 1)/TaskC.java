package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    // 360360
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        lcm = 1;
        long a = in.nextLong(), b = in.nextLong();
        k = in.nextInt();
        for (int i = 1; i < k + 1; i++) lcm = (int) MathUtils.lcm(lcm, i);
        if (a / lcm == b / lcm) {
            long res = solve((int) (a % lcm), (int) (b % lcm));
            out.println(res);
        } else {
            long res = solve((int) (a % lcm), 0);
            res += solve(lcm, 0) * (a / lcm - b / lcm - 1);
            res += solve(lcm, (int) (b % lcm));
            out.println(res);
        }

    }

    private long solve(int a, int b) {
        int[] dp = new int[a + 1];
        Arrays.fill(dp, (int) 1e9);
        dp[b] = 0;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.min(dp[i], dp[i - 1] + 1);
            for (int j = 2; j <= k; j++) {
                dp[i] = Math.min(dp[i], dp[i - i % j] + 1);
            }
        }
        return dp[a];
    }

    int lcm;
    int k;
}
