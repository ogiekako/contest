package src;

public class ChocolateDividingEasy {
    int[][] S;

    public int findBest(String[] chocolate) {
        int h = chocolate.length, w = chocolate[0].length();
        int[][] map = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i][j] = chocolate[i].charAt(j) - '0';
            }
        }
        S = new int[h + 1][w + 1];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                S[i + 1][j + 1] = S[i + 1][j] + S[i][j + 1] - S[i][j] + map[i][j];
            }
        }
        int res = 0;
        for (int i2 = 1; i2 < h; i2++) {
            for (int i1 = 1; i1 < i2; i1++) {
                for (int j2 = 1; j2 < w; j2++) {
                    for (int j1 = 1; j1 < j2; j1++) {
                        res = Math.max(res, min(
                                f(0,  0, i1, j1), f(0,  j1, i1, j2), f(0,  j2, i1, w),
                                f(i1, 0, i2, j1), f(i1, j1, i2, j2), f(i1, j2, i2, w),
                                f(i2, 0,  h, j1), f(i2, j1,  h, j2), f(i2, j2,  h, w)
                        ));
                    }
                }
            }
        }
        return res;
    }


    int min(int... is) {
        int res = Integer.MAX_VALUE;
        for (int i : is) res = Math.min(i, res);
        return res;
    }

    private int f(int i1, int j1, int i2, int j2) {
        return S[i2][j2] - S[i2][j1] - S[i1][j2] + S[i1][j1];
    }
}
