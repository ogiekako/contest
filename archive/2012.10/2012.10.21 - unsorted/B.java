package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class B {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    int H, W;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        H = in.nextInt(); W = in.nextInt();
        int[][] map = new int[H][W];
        for (int i = 0; i < H; i++) {
            String s = in.next();
            for (int j = 0; j < W; j++) map[i][j] = s.charAt(j) - '0';
        }
        boolean[][] kata = new boolean[H][W];
        for (int x = 0; x < H; x++)
            for (int y = 0; y < W; y++) {
                for (int d = 0; d < 2; d++) {
                    boolean con = true;
                    for (int e = 0; e < 4; e += 2) {
                        int nx = x + dx[d + e], ny = y + dy[d + e];
                        if (0 <= nx && nx < H && 0 <= ny && ny < W && map[x][y] == map[nx][ny]) {

                        } else con = false;
                    }
                    if (con) {
                        kata[x][y] = true;
                        for (int e = 0; e < 4; e += 2) {
                            int nx = x + dx[d + e], ny = y + dy[d + e];
                            if (0 <= nx && nx < H && 0 <= ny && ny < W && map[x][y] == map[nx][ny]) {
                                kata[nx][ny] = true;
                            }
                        }
                    }
                }
            }
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) if (!kata[i][j]) map[i][j] = -1;
        int res = 0;
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                if (map[i][j] >= 0) {
                    res++;
                    dfs(i, j, map, map[i][j]);
                }
        out.println(res);
    }

    private void dfs(int x, int y, int[][] map, int p) {
        if (map[x][y] != p) return;
        map[x][y] = -1;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d], ny = y + dy[d];
            if (0 <= nx && nx < H && 0 <= ny && ny < W) {
                dfs(nx, ny, map, p);
            }
        }
    }
}
