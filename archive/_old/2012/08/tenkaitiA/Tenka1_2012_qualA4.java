package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class Tenka1_2012_qualA4 {
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }
    int N;
    char[][] map;
    int[][] count;
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    double[][] C;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        N = in.nextInt();
        map = new char[N][N];
        for (int i = 0; i < N; i++) map[i] = in.next().toCharArray();
        double res = solve();
        out.printf("%.12f\n", res);
    }

    private double solve() {
        count = new int[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) count[i][j] = -2;
        C = new double[N * N + 2][N * N + 2];
        for (int i = 0; i < C.length; i++)
            for (int j = 0; j < i + 1; j++) C[i][j] = j == 0 ? 1 : (C[i - 1][j - 1] + C[i - 1][j]) / 2;
        double[] ps = calc(0, 0, 0);
        double res = 0;
        for (int i = 0; i < ps.length; i++) res += ps[i] * i;
        return res;
    }

    private double[] calc(int x, int y, int d) {
        debug("calc", x, y, d);
        if (map[x][y] == 'G') {
            double[] res = new double[N * N + 1];
            res[1] = 1;
            return res;
        }
        int offset = count(x + dx[d], y + dy[d], d);
        if (offset == -1) return calc(x + dx[d], y + dy[d], d);
        int slot; double[] curProb;
        slot = count(x + dx[d + 1 & 3], y + dy[d + 1 & 3], d + 1 & 3);
        if (slot == -1) {
            slot = count(x + dx[d + 3 & 3], y + dy[d + 3 & 3], d + 3 & 3);
            if (slot == -1) throw new AssertionError();
            curProb = calc(x + dx[d + 1 & 3], y + dy[d + 1 & 3], d + 1 & 3);
        } else {
            curProb = calc(x + dx[d + 3 & 3], y + dy[d + 3 & 3], d + 3 & 3);
        }
        double[] nextProb = new double[curProb.length];
        double[][] probSlot = new double[2][slot + 1];
        probSlot[0][0] = 1;
        int cur = 0, nxt = 1;
        for (int enter = 1; offset + enter < nextProb.length; enter++) {
            for (int goGoal = 1; goGoal <= enter; goGoal++) {
                int goSlot = enter - goGoal;
                if (goSlot > slot) continue;
                double lastProb = goSlot == slot ? 1 : 0.5;
                nextProb[offset + enter] += probSlot[cur][goSlot] * lastProb * curProb[goGoal];
            }
            Arrays.fill(probSlot[nxt], 0);
            for (int curSlot = 0; curSlot <= slot; curSlot++) {
                if (curSlot < slot) {
                    probSlot[nxt][curSlot] += probSlot[cur][curSlot] / 2;
                    probSlot[nxt][curSlot + 1] += probSlot[cur][curSlot] / 2;
                } else {
                    probSlot[nxt][curSlot] += probSlot[cur][curSlot];
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        debug("calc res", x, y, d, nextProb);
        return nextProb;
    }

    private int count(int x, int y, int d) {
//        debug("count",x,y,d);
        if (x < 0 || x >= N || y < 0 || y >= N) return 0;
        if (map[x][y] == '#') return 0;
        if (map[x][y] == 'G') return -1;
        if (count[x][y] != -2) return count[x][y];
        int res = 1;
        for (int nd = 0; nd < 4; nd++)
            if ((d ^ 2) != nd) {
                int nc = count(x + dx[nd], y + dy[nd], nd);
                if (nc == -1) {
                    return count[x][y] = -1;
                }
                res += nc;
            }
        return count[x][y] = res;
    }
}
