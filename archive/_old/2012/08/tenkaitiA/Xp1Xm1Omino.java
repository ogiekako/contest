package tmp;

import net.ogiekako.algorithm.misc.game.Polyomino;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Xp1Xm1Omino {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int[][] res = solve(n);

        if (res != null) {
            for (int[] r : res) System.out.println(Arrays.toString(r));
        }
    }

//    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
//    }

    int[][] solve(int n) {
        List<Polyomino> list = Polyomino.generateAll(n + 1);
        System.out.println("#poly = " + list.size());
        int cnt = 1;
        for (Polyomino p : list) {
//            System.out.println(cnt++ + "/" + list.size());
            int[][] res = solve(n, p);
            if (res != null) {
                for (int[] r : res) System.out.println(Arrays.toString(r));
                System.out.println();
//                return res;
            }
        }
        return null;
    }

    private int[][] solve(int n, Polyomino polyomino) {
        int[][] field = new int[n][n];
        Polyomino[] ps = polyomino.generateAll();
        boolean can = dfs(field, ps, n - 1, n + 1, false);
        return can ? field : null;
    }

    private boolean dfs(int[][] field, Polyomino[] ps, int n, int cellCount, boolean put) {
        if (n == 0) return true;
        if (cut(field, cellCount)) return false;
//        debug(n, field);
        int[] xy = findPlace(field);
        int i = xy[0], j = xy[1];
//        for (int i = 0; i < field.length; i++)
//            for (int j = 0; j < field[0].length; j++)
        if (field[i][j] == 0) {
            for (Polyomino p : ps) {
                for (int x = 0; x < p.h; x++)
                    for (int y = 0; y < p.w; y++)
                        if ((p.mask[x] >> y & 1) == 1) {
                            if (canPut(field, i - x, j - y, p)) {
                                flip(field, i - x, j - y, p, n);
//                                        debug(p);
                                if (dfs(field, ps, n - 1, cellCount, put)) return true;
                                flip(field, i - x, j - y, p, n);
                            }
                        }
            }
            if (!put) {
                field[i][j] = -1;
                if (dfs(field, ps, n, cellCount, true)) {
                    return true;
                }
                field[i][j] = 0;
            }
//                    break;
        }
        return false;
    }

    private boolean cut(int[][] field, int cellCount) {
        boolean[][] vis = new boolean[field.length][field[0].length];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (!vis[i][j] && field[i][j] == 0) {
                    int cnt = dfs(field, vis, i, j);
                    if (cnt % cellCount > 1) return true;
                }
        return false;
    }

    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    private int dfs(int[][] field, boolean[][] vis, int x, int y) {
        if (x < 0 || x >= field.length || y < 0 || y >= field[0].length || field[x][y] != 0 || vis[x][y]) return 0;
        vis[x][y] = true;
        int res = 1;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < field.length && 0 <= ny && ny < field[0].length) {
                res += dfs(field, vis, nx, ny);
            }
        }
        return res;
    }


    private void flip(int[][] field, int i, int j, Polyomino p, int n) {
        for (int x = 0; x < p.h; x++)
            for (int y = 0; y < p.w; y++)
                if ((p.mask[x] >> y & 1) == 1) {
                    int nx = i + x;
                    int ny = j + y;
//                    debug("nx", nx, ny);
                    if (field[nx][ny] == n) field[nx][ny] = 0;
                    else if (field[nx][ny] == 0) field[nx][ny] = n;
                    else throw new AssertionError();
                }
    }

    private boolean canPut(int[][] field, int i, int j, Polyomino p) {
        for (int x = 0; x < p.h; x++)
            for (int y = 0; y < p.w; y++)
                if ((p.mask[x] >> y & 1) == 1) {
                    int nx = i + x;
                    int ny = j + y;
                    if (nx < 0 || nx >= field.length || ny < 0 || ny >= field[0].length || field[nx][ny] != 0)
                        return false;
                }
        return true;
    }

    int[] findPlace(int[][] field) {
        int x = 0, y = 0, cnt = 5;
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j] == 0) {
                    int nCnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (0 <= nx && nx < field.length && 0 <= ny && ny < field[0].length) {
                            if (field[nx][ny] == 0) nCnt++;
                        }
                    }
                    if (nCnt < cnt) {
                        cnt = nCnt;
                        x = i; y = j;
                    }
                }
        return new int[]{x, y};
    }
}
