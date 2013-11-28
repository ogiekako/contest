package src;

public class LISNumber {
    int MOD = (int) (1e9 + 7);
    public int count(int[] cardsnum, int K) {
        long[] A = new long[K + 1];
        long[][] C = generateCombinationMod(1500, 1500, MOD);
        int all = 0;
        for (int num : cardsnum) all += num;
        if (all < K) return 0;        // !!!!!

        for (int i = 1; i <= K; i++) {// 1
            A[i] = 1;
            for (int num : cardsnum) {
                A[i] = (A[i] * C[i][num]) % MOD;
            }
        }
        for (int i = 1; i <= K; i++) {// 2
            for (int j = 1; j < i; j++) {
                A[i] -= A[j] * C[i][j];
                A[i] %= MOD;
            }
            if (A[i] < 0) A[i] += MOD;
        }
        for (int i = 1; i <= K; i++) {// 3
            for (int j = 1; j < i; j++) {
                A[i] -= A[j] * C[all - j][i - j];
                A[i] %= MOD;
            }
            if (A[i] < 0) A[i] += MOD;
        }
        return (int) A[K];
    }

    public static long[][] generateCombinationMod(int h, int w, int MOD) {
        long[][] C = new long[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w && j <= i; j++) {
                long value = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (value >= MOD) value -= MOD;
                C[i][j] = value;
            }
        return C;
    }
}
