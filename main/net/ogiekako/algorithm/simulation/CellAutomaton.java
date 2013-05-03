package net.ogiekako.algorithm.simulation;

public class CellAutomaton {
    /**
     * Euler349
     * @param step
     * @return
     */
    public static long blackCellCountOfLangtonAnt(long step) {
        int th = 11000;
        long res = 0;
        if (step > th) {
            int period = 104;
            int growth = 12;
            long numPeriod = (step - th) / period;
            res += numPeriod * growth;
            step -= numPeriod * period;
        }
        boolean[][] black = new boolean[100][100];
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int x = 50, y = 50;
        int dir = 0;
        for (int i = 0; i < step; i++) {
            if (black[x][y]) {
                black[x][y] = false;
                dir = (dir + 1) & 3;
                res--;
            } else {
                black[x][y] = true;
                dir = (dir + 3) & 3;
                res++;
            }
            x += dx[dir];
            y += dy[dir];
        }
        return res;
    }
}
