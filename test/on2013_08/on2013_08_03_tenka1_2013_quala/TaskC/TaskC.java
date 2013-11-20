package on2013_08.on2013_08_03_tenka1_2013_quala.TaskC;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC {
    int h, w;
    int[][] map;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        h = in.nextInt(); w = in.nextInt();
        if (h > 24) h = (h % 4 + 24);
        if (w > 24) w = (w % 4 + 24);
        map = new int[h][w];
        int res = solve(0, 0);
        out.println(res);
    }
    private int solve(int x, int y) {
//        debug(x,y);
        if (x == h) return 1;
        if (y == w) return solve(x + 1, 0);
        int res = 0;
        for (int d = 1; d <= 3; d++) {
            map[x][y] = d;
            boolean valid = true;
            for (int dx = 1; dx <= d; dx++) {
                int nx = x - dx, ny = y;
                if (0 <= nx && nx < h && 0 <= ny && ny < w && map[nx][ny] == d) valid = false;
            }
            for (int dy = 1; dy <= d; dy++) {
                int nx = x, ny = y - dy;
                if (0 <= nx && nx < h && 0 <= ny && ny < w && map[nx][ny] == d) valid = false;
            }
            if (valid) res += solve(x, y + 1);
        }
        return res;
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
