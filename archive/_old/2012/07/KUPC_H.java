package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KUPC_H {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int H = in.nextInt();
        int W = in.nextInt();
        boolean[][] bs = new boolean[H][W];
        for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) bs[i][j] = in.nextInt() == 1;
        boolean[] R = new boolean[W];
        for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) R[j] ^= bs[i][j];
        boolean[][] res = new boolean[H][W];
        for (int i = 0; i < H; i++) {
            boolean odd = false;
            for (int j = 0; j < W; j++) odd ^= bs[i][j];
            for (int j = 0; j < W; j++) {
                res[i][j] = R[j] ^ bs[i][j] ^ odd;
            }
        }
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                out.printf("%d%c", res[i][j] ? 0 : 1, j == W - 1 ? '\n' : ' ');
            }
    }
}
