package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Cast;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class TaskC {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    String DIR = "DRUL";
    long INF = (long) 1e60;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        TreeSet<Long> xSet = new TreeSet<Long>();
        TreeSet<Long> ySet = new TreeSet<Long>();
        char[] dirs = new char[n];
        long[] values = new long[n];
        xSet.add(INF); xSet.add(-INF);
        ySet.add(INF); ySet.add(-INF);
        {
            long x = 0, y = 0;
            xSet.add(x); ySet.add(y);
            xSet.add(x + 1); ySet.add(y + 1);
            for (int i = 0; i < n; i++) {
                dirs[i] = in.nextChar();
                values[i] = in.nextInt();
                int d = DIR.indexOf(dirs[i]);
                x += values[i] * dx[d];
                y += values[i] * dy[d];
                xSet.add(x); ySet.add(y);
                xSet.add(x + 1); ySet.add(y + 1);
            }
        }
        long[] xs = Cast.toLong(xSet);
        long[] ys = Cast.toLong(ySet);
        int numX = xs.length - 1;
        int numY = ys.length - 1;
        int[][] map = new int[numX][numY];
        {
            long x = 0, y = 0;
            for (int i = 0; i < n; i++) {
                int d = DIR.indexOf(dirs[i]);
                long nx = x + values[i] * dx[d];
                long ny = y + values[i] * dy[d];
                if (x == nx) {
                    int ix = Arrays.binarySearch(xs, x);
                    int a = Arrays.binarySearch(ys, y);
                    int b = Arrays.binarySearch(ys, ny);
                    int from = Math.min(a, b);
                    int to = Math.max(a, b);
                    for (int iy = from; iy <= to; iy++) map[ix][iy] = 1;
                } else {
                    int iy = Arrays.binarySearch(ys, y);
                    int a = Arrays.binarySearch(xs, x);
                    int b = Arrays.binarySearch(xs, nx);
                    int from = Math.min(a, b);
                    int to = Math.max(a, b);
                    for (int ix = from; ix <= to; ix++) map[ix][iy] = 1;
                }
                x = nx; y = ny;
            }
        }
        dfs(map, 0, 0);
        dfs(map, 0, numY - 1);
        dfs(map, numX - 1, 0);
        dfs(map, numX - 1, numY - 1);
        long res = 0;
        for (int i = 0; i < numX; i++)
            for (int j = 0; j < numY; j++) if (map[i][j] < 2) res += (xs[i + 1] - xs[i]) * (ys[j + 1] - ys[j]);
        out.println(res);
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private void dfs(int[][] map, int x, int y) {
        if (map[x][y] != 0) return;
        map[x][y] = 2;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < map.length && 0 <= ny && ny < map[0].length) {
                dfs(map, nx, ny);
            }
        }
    }
}
