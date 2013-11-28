package tmp;

import java.util.Arrays;

public class TheSwapsDivOne {
    public double find(String[] sequence, int k) {
        StringBuilder b = new StringBuilder();
        for (String s : sequence) b.append(s);
        String s = b.toString();
        double p = calc(s.length(), k);
        debug(p);
        double res = 0;
        int[] cnt = new int[10];
        for (char c : s.toCharArray()) cnt[c - '0']++;
        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            int d = s.charAt(i) - '0';
            double q = 0;
            q += choose2(i + 1) / choose2(n + 1);
            q += choose2(n - i) / choose2(n + 1);
            q = 1 - q;
            res += q * p * d;
            cnt[d]--;
            for (int j = 0; j < 10; j++) {
                res += q * (1 - p) / (n - 1) * cnt[j] * j;
            }
            cnt[d]++;
        }
        return res;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private double choose2(int n) {
        return (double) n * (n - 1) / 2;
    }

    private double calc(int n, int k) {
        double[] dp = new double[2];
        dp[0] = 1;
        double all = (double) n * (n - 1) / 2;
        double other = (double) (n - 1) * (n - 2) / 2;
        double p = other / all;
        for (int i = 0; i < k; i++) {
            double[] nDp = new double[2];
            nDp[0] = dp[0] * p + dp[1] / all;
            nDp[1] = dp[0] * (1 - p) + dp[1] * (all - 1) / all;
            dp = nDp;
        }
        return dp[0];
    }
}
