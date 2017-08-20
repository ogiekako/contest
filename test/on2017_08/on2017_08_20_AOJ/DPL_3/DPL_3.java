package on2017_08.on2017_08_20_AOJ.DPL_3;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.misc.MaxRectangle;

public class DPL_3 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        boolean[][] grid = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                grid[i][j] = in.nextInt() == 0;
            }
        }
        int res = MaxRectangle.maxRectangle(grid);
        out.println(res);
    }
}
