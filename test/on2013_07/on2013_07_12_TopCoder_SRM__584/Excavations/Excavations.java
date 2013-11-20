package on2013_07.on2013_07_12_TopCoder_SRM__584.Excavations;


import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;
public class Excavations {
    public long count(int[] kind, int[] depth, final int[] found, int K) {
        Arrays.sort(found);
        int n = kind.length;
        long res = 0;
        long[][] C = MathUtils.combination(60);
        for (int i = 0; i < n; i++) {
            if (Arrays.binarySearch(found, kind[i]) < 0) continue;
            int[] count = new int[found.length];
            long[] freeWay = new long[K];
            freeWay[0] = 1;
            for (int j = 0; j < n; j++)
                if (i != j) {
                    int p = Arrays.binarySearch(found, kind[j]);
                    if (p < 0) {
                        if (depth[j] > depth[i]) {
                            int free = 0;
                            for (int k = 0; k < n; k++) {
                                if (depth[k] > depth[j] || depth[k] == depth[j] && k < j || depth[k] == depth[j] && Arrays.binarySearch(found, kind[k]) >= 0)
                                    free++;
                            }
                            for (int k = 0; k < K - 1; k++) {
                                freeWay[k + 1] += C[free][k];
                            }
                        }
                    } else {
                        if (depth[j] < depth[i] || depth[j] == depth[i] && j < i) {
                            count[p]++;
                        }
                    }
                }
            int m = found.length;
            long[][] dp = new long[m + 1][K];
            dp[0][0] = 1;
            for (int j = 0; j < m; j++)
                for (int k = 0; k < K; k++) {
                    int from = 1;
                    if (found[j] == kind[i]) from--;
                    for (int l = from; l <= count[j] && k + l < K; l++) {
                        dp[j + 1][k + l] += dp[j][k] * C[count[j]][l];
                    }
                }

            for (int k = 0; k < K; k++) res += dp[m][k] * freeWay[K - 1 - k];
        }
        return res;
    }
}
