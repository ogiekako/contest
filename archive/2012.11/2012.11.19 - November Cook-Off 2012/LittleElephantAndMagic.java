package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class LittleElephantAndMagic {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        int[][] map = new int[h][w];
        for (int i = 0; i < h; i++) {
            String s = in.next();
            for (int j = 0; j < w; j++) map[i][j] = s.charAt(j) - '0';
        }
        int res = 0;
        for (int P = 0; P < 10; P++) {
            res = Math.max(res, solve(h, w, map, P));
        }
        out.println(res);
    }

    private int solve(int h, int w, int[][] map, int P) {
        int[][][][][] dp = new int[h][w][10][2][2];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                int cur = (map[0][0] + i * P + j * P) % 10;
                dp[0][0][cur][i][j] = 1;
            }
        int[] dx = {1, 0};
        int[] dy = {0, 1};
        for (int x = 0; x < h; x++)
            for (int y = 0; y < w; y++)
                for (int p = 0; p < 10; p++)
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 2; j++) {
                            for (int d = 0; d < 2; d++) {
                                int nx = x + dx[d];
                                int ny = y + dy[d];
                                if (0 <= nx && nx < h && 0 <= ny && ny < w) {
                                    for (int k = 0; k < 2; k++) {
                                        int ni = (i + (k & (1 - d))) & 1;
                                        int nj = (j + (k & d)) & 1;
                                        dp[nx][ny][p][ni][nj] = Math.max(dp[nx][ny][p][ni][nj], dp[x][y][p][i][j]);
                                        int np = (map[nx][ny] + ni * P + nj * P);
                                        while (np >= 10) np -= 10;
                                        if (p >= np)
                                            dp[nx][ny][np][ni][nj] = Math.max(dp[nx][ny][np][ni][nj], dp[x][y][p][i][j] + 1);
                                    }
                                }
                            }
                        }
        int res = 0;
        for (int p = 0; p < 10; p++)
            for (int i = 0; i < 2; i++) for (int j = 0; j < 2; j++) res = Math.max(res, dp[h - 1][w - 1][p][i][j]);
        return res;
    }
}
