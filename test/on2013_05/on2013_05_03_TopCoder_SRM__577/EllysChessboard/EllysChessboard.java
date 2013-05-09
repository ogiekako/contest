package on2013_05.on2013_05_03_TopCoder_SRM__577.EllysChessboard;


import net.ogiekako.algorithm.Builtin;
import net.ogiekako.algorithm.utils.ArrayUtils;
public class EllysChessboard extends Builtin {
    public int minCost(String[] board) {
        int n = 8;
        int N = n * 2;
        boolean[][] map = new boolean[N][N];
        for (int x = 0; x < n; x++)
            for (int y = 0; y < n; y++) {
                int X = x + y;
                int Y = x - y + n - 1;
                if (board[x].charAt(y) == '#') map[X][Y] = true;
            }

        // upperLeft, lowerRight+(1,1)
        int[][][][] dp = new int[N][N][N + 1][N + 1];
        ArrayUtils.fill(dp, Integer.MAX_VALUE);
        for (int X = 0; X < N; X++) for (int Y = 0; Y < N; Y++) dp[X][Y][X + 1][Y + 1] = 0;
        for (int size = 2; size <= N + N - 2; size++)
            for (int X0 = 0; X0 < N; X0++)
                for (int Y0 = 0; Y0 < N; Y0++)
                    for (int X1 = X0 + 1; X1 < N && X1 < X0 + size; X1++) {
                        int Y1 = Y0 + (size - (X1 - X0));
                        if (Y1 > N) continue;
                        if (X0 > 0) {
                            int add = 0;
                            for (int Y = Y0; Y < Y1; Y++) {
                                if (map[X0 - 1][Y]) add += max(X1 - 1 - (X0 - 1), Y1 - 1 - Y, Y - Y0);
                            }
                            dp[X0 - 1][Y0][X1][Y1] = Math.min(dp[X0 - 1][Y0][X1][Y1], dp[X0][Y0][X1][Y1] + add);
                        }
                        if (Y0 > 0) {
                            int add = 0;
                            for (int X = X0; X < X1; X++) {
                                if (map[X][Y0 - 1]) add += max(Y1 - 1 - (Y0 - 1), X1 - 1 - X, X - X0);
                            }
                            dp[X0][Y0 - 1][X1][Y1] = Math.min(dp[X0][Y0 - 1][X1][Y1], dp[X0][Y0][X1][Y1] + add);
                        }
                        if (X1 < N) {
                            int add = 0;
                            for (int Y = Y0; Y < Y1; Y++) {
                                if (map[X1][Y]) add += max(X1 - 1 - (X0 - 1), Y1 - 1 - Y, Y - Y0);
                            }
                            dp[X0][Y0][X1 + 1][Y1] = Math.min(dp[X0][Y0][X1 + 1][Y1], dp[X0][Y0][X1][Y1] + add);
                        }
                        if (Y1 < N) {
                            int add = 0;
                            for (int X = X0; X < X1; X++) {
                                if (map[X][Y1]) add += max(Y1 - 1 - (Y0 - 1), X1 - 1 - X, X - X0);
                            }
                            dp[X0][Y0][X1][Y1 + 1] = Math.min(dp[X0][Y0][X1][Y1 + 1], dp[X0][Y0][X1][Y1] + add);
                        }
                    }
        return dp[0][0][N - 1][N - 1];
    }
}
