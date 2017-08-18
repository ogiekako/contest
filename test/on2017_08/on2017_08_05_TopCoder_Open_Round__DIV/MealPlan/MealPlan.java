package on2017_08.on2017_08_05_TopCoder_Open_Round__DIV.MealPlan;



import java.util.Arrays;

public class MealPlan {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long countDistinct(int[] morningMeal, int[] noonMeal, int[] eveningMeal, int[] nightMeal) {
        int[][] A = new int[][]{morningMeal, noonMeal, eveningMeal, nightMeal};
        for (int i = 0; i < A.length; i++) {
            Arrays.sort(A[i]);
        }
        int M = 4010;
        int[] mask = new int[M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < 4; j++) {
                if (Arrays.binarySearch(A[j], i) >= 0) {
                    mask[i] |= 1 << j;
                }
            }
        }
        long[][] dp = new long[2][1 << 4];
        dp[0][0] = 1;
        int cur = 0, nxt = 1;
        for (int i = 0; i < 4005; i++) {
            Arrays.fill(dp[nxt], 0);
            for (int j = 0; j < 1 << 4; j++) {
                if (dp[cur][j] > 0) {
                    dp[nxt][j] += dp[cur][j];
                    for (int k = 1; k < 1 << 4; k++)
                        if ((k & j) == 0 && (mask[i + 1] & k) == k) {
                            dp[nxt][k | j] += dp[cur][j];
                        }
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
            debug(dp[cur]);
        }
        return dp[cur][(1 << 4) - 1];
    }
}
