package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

public class TaskB {
    int MOD = (int) (1e9 + 7);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int h = in.nextInt(), w = in.nextInt(), k = in.nextInt();
        int[][] map = new int[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) map[i][j] = in.nextInt();
        int res = solve(map, k);
        out.println(res);
    }

    private int solve(int[][] map, int k) {
        int h = map.length, w = map[0].length;
        if (h + w - 1 > k) return 0;
        long[][] C = MathUtils.combinationMod(k + 1, MOD);
        return 0;
    }
}
