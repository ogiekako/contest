package on2013_07.on2013_07_06_kupc2013.TaskC;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        int[][] a = new int[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                a[i][j] = in.nextInt();
                if (i > 0) a[i][j] = 1 - a[i][j];
            }
        int res = 0;

        if (w == 1) {
            for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) res += a[i][j];
            out.println(res); return;
        }

        for (int i = 0; i < h; i++) {
            int best = 0;
            boolean[] flipped = new boolean[w];
            for (int j = 1; j < w - 1; j++) flipped[j] = true;
            for (int k = 0; k < w; k++) {
                flipped[k] ^= true;
                int cnt = 0;
                for (int j = 0; j < w; j++) cnt += flipped[j] ? 1 - a[i][j] : a[i][j];
                best = Math.max(best, cnt);
                flipped[k] ^= true;
            }
            res += best;
        }

        out.println(res);
    }
}
