package on2013_09.on2013_09_27_Single_Round_Match_592.LittleElephantAndPermutationDiv1;


import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Map;
import java.util.TreeMap;
public class LittleElephantAndPermutationDiv1 {
    int MOD = (int) (1e9 + 7);
    public int getNumber(int N, int K) {
        K -= N;
        int[][] dp = new int[N + 1][2500 + 110];
        dp[0][0] = 1;
        for (int i = 0; i < N; i++) {
            int[][] nDp = new int[N + 1][2500 + 110];
            for (int j = 0; j <= N; j++)
                for (int k = 0; k <= 2500; k++)
                    if (dp[j][k] > 0) {
                        nDp[j][k + i] += dp[j][k];
                        if (nDp[j][k + i] >= MOD) nDp[j][k + i] -= MOD;

                        for (int d1 = -1; d1 <= 1; d1 += 2)
                            for (int d2 = -1; d2 <= 1; d2 += 2) {
                                if (j == 0 && (d1 == -1 || d2 == -1)) continue;
                                int nj = j + (d1 + d2) / 2;
                                long way = dp[j][k];
                                if (d1 == -1) way *= j;
                                if (d2 == -1) way *= j;
                                int nk = k;
                                if (d1 == -1) nk += i;
                                if (d2 == -1) nk += i;
                                if (0 <= nj && nj <= N) {
                                    nDp[nj][nk] = (int) ((nDp[nj][nk] + way) % MOD);
                                }
                            }
                    }
            dp = nDp;
        }
        long res = 0;
        for (int k = Math.max(K, 0); k <= 2500; k++) {
            res += dp[0][k];
            res %= MOD;
        }

        for (int i = 0; i < N; i++) res = (res * (i + 1)) % MOD;
        return (int) res;
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 8; n++) {
            int[] is = new int[n];
            for (int i = 0; i < n; i++) is[i] = i;
            TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
            do {
                int val = 0;
                for (int i = 0; i < n; i++) {
                    val += Math.max(1 + i, 1 + is[i]);
                }
                if (!map.containsKey(val)) map.put(val, 0);
                map.put(val, map.get(val) + 1);
            } while (ArrayUtils.nextPermutation(is));
            System.err.println("n = " + n);
            int sum = 0;
            for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                sum += e.getValue();
                System.err.printf("%d -> %d, %d\n", e.getKey(), e.getValue(), sum);
            }

        }
    }
}
