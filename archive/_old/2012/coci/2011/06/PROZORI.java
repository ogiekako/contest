package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class PROZORI {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int h = in.nextInt(), w = in.nextInt();
        int[][] cnt = new int[h][w];
        String[] ss = new String[5 * h];
        for (int i = 0; i < 5 * h; i++) {
            ss[i] = in.next();
        }
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                for (int k = 0; k < 4; k++) {
                    cnt[i][j] += ss[i * 5 + 1 + k].charAt(j * 5 + 1) == '*' ? 1 : 0;
                }
        int[] res = new int[5];
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) res[cnt[i][j]]++;
        out.printf("%d %d %d %d %d\n", res[0], res[1], res[2], res[3], res[4]);
    }
}
