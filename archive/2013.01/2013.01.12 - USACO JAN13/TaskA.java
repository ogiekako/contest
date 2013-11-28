package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int a = in.nextInt(), b = in.nextInt();
        int[] x = new int[n + 1], y = new int[n + 1]; char[] dir = new char[n + 1];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt(); y[i] = in.nextInt(); dir[i] = in.nextChar();
        }
        x[n] = a; y[n] = b;
        dir[n] = 'G';
        if (valid(0, 0, 0, x, y, dir)) {
            out.println(0);
            return;
        }
        for (int i = 0; i < n; i++) {
            dir[i] = dir[i] == '/' ? '\\' : '/';
            if (valid(0, 0, 0, x, y, dir)) {
                out.println(i + 1); return;
            }
            dir[i] = dir[i] == '/' ? '\\' : '/';
        }
        out.println(-1);
    }
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    int INF = (int) 1e9;

    private boolean valid(int i, int j, int d, int[] x, int[] y, char[] dir) {
        int best = INF;
        int p = -1;
        for (int k = 0; k < x.length; k++) {
            int count = count(i, j, d, x[k], y[k]);
            if (best > count) {
                best = count;
                p = k;
            }
        }
        if (best == INF) return false;
        if (dir[p] == 'G') return true;
        i += dx[d] * best;
        j += dy[d] * best;
        if (dir[p] == '/') d ^= 1;
        else d ^= 3;
        return valid(i, j, d, x, y, dir);
    }

    private int count(int i, int j, int d, int x, int y) {
        if (dx[d] == 0) {
            if (i == x && (y - j) * dy[d] > 0) return (y - j) * dy[d];
        } else {
            if (j == y && (x - i) * dx[d] > 0) return (x - i) * dx[d];
        }
        return INF;
    }
}
