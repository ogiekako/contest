package on2015_08.on2015_08_29_TopCoder_SRM__655.Nine;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class Nine {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);
    int[] nines;
    int N;
    int[] num;
    long[][] dp;
    long[][] memo;

    public int count(int N, int[] d) {
        this.N = N;
        int M = d.length;
        dp = new long[M + 1][10];
        dp[0][0] = 1;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    dp[i + 1][(j + k) % 9] = (dp[i + 1][(j + k) % 9] + dp[i][j]) % MOD;
                }
            }
        }
        num = new int[1 << N];
        for (int i : d) num[i]++;
        nines = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            nines[i] = i == 0 ? 1 : nines[i - 1] * 9;
        }
        memo = new long[nines[N]][1 << N];
        ArrayUtils.fill(memo, -1);
        return (int) solve(new int[N], 0);
    }

    private long solve(int[] rs, final int mask) {
        int id = 0;
        for (int r : rs) {
            id *= 9;
            id += r;
        }
        if (mask >= 1 << N) {
            return id == 0 ? 1 : 0;
        }
        if (memo[id][mask] >= 0) return memo[id][mask];
        long res = 0;
        for (int i = 0; i < 9; i++) {
            int[] nRs = rs.clone();
            for (int j = 0; j < N; j++) {
                if (mask << 31 - j >= 0) continue;
                nRs[j] = (rs[j] + i) % 9;
            }
            long way = solve(nRs, mask + 1) * dp[num[mask]][i] % MOD;
            res += way;
            if (res >= MOD) res -= MOD;
        }
        return memo[id][mask] = res;
    }
}
