package on2013_10.on2013_10_06_Single_Round_Match_593.MayTheBestPetWin;


public class MayTheBestPetWin {
    public int calc(int[] A, int[] B) {
        int n = A.length;
        int[] C = new int[n];
        for (int i = 0; i < n; i++) C[i] = A[i] + B[i];
        boolean[] dp = new boolean[20010 * 50];
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = dp.length - 1; j >= 0; j--) if (dp[j]) dp[j + C[i]] = true;
        }
        int res = Integer.MAX_VALUE;
        int sumA = 0, sumB = 0;
        for (int a : A) sumA += a;
        for (int b : B) sumB += b;
        for (int i = 0; i < dp.length; i++) if (dp[i]) res = Math.min(res, Math.max(i - sumA, sumB - i));
        return res;
    }
}
