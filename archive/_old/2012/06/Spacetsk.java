package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

public class Spacetsk {
    int M = (int) (1e9 + 7);
    public int countsets(int L, int H, int K) {
        if (K == 1) {
            return (int) ((long) (L + 1) * (H + 1) % M);
        }
        long[][] C = new long[2500][2500];
        for (int i = 0; i < 2500; i++)
            for (int j = 0; j < i + 1; j++) {
                C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (C[i][j] >= M) C[i][j] -= M;
            }
        long res = 0;
        long before = 0;
        for (int y0 = 1; y0 <= L; y0++) {
            for (int x0 = 1; x0 <= H; x0++) {
                int cnt = (int) (MathUtils.gcd(x0, y0) + 1);
                before += C[cnt - 1][K - 1];
                if (before >= M) before -= M;
            }
            res += before;
            if (res >= M) res -= M;
        }
        res = res * 2 % M;

        res += C[H + 1][K] * (L + 1);
        return (int) (res % M);
    }
}

