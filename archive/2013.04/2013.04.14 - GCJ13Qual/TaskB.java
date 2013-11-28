package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
// 15'31
public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        int[][] map = new int[h][w];
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) map[i][j] = in.nextInt();
        boolean res = true;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                boolean ok = true;
                for (int k = 0; k < h; k++) if (map[k][j] > map[i][j]) ok = false;
                boolean ok2 = true;
                for (int k = 0; k < w; k++) if (map[i][k] > map[i][j]) ok2 = false;
                if (!ok && !ok2) res = false;
            }
        out.printFormat("Case #%d: %s\n", testNumber, res ? "YES" : "NO");
    }
}
