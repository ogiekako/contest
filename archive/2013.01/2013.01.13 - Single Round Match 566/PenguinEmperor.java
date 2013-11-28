package tmp;

import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

public class PenguinEmperor {
    int MOD = (int) (1e9 + 7);
    public int countJourneys(int numCities, long daysPassed) {
        long[][] dp = new long[numCities + 1][numCities];
        dp[0][0] = 1;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                int nj = (j + i + 1) % numCities;
                dp[i + 1][nj] += dp[i][j];
                if (dp[i + 1][nj] >= MOD) dp[i + 1][nj] -= MOD;
                int nk = (j - i - 1 + numCities) % numCities;
                if (nj == nk) continue;
                dp[i + 1][nk] += dp[i][j];
                if (dp[i + 1][nk] >= MOD) dp[i + 1][nk] -= MOD;
            }
        }
        long k = daysPassed / numCities;
        daysPassed -= k * numCities;
        long[][] mat = new long[numCities][numCities];
        for (int i = 0; i < numCities; i++)
            for (int j = 0; j < numCities; j++) {
                mat[j][i] = dp[numCities][(j - i + numCities) % numCities];
            }
        long[] x = new long[numCities];
        x[0] = 1;
        x = Matrix.powered(mat, k, x, MOD);
        for (int i = 0; i < numCities; i++)
            for (int j = 0; j < numCities; j++) {
                mat[j][i] = dp[((int) daysPassed)][(j - i + numCities) % numCities];
            }
        x = Matrix.mul(mat, x, MOD);
        return (int) x[0];
    }
}
