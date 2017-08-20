package on2017_08.on2017_08_20_TopCoder_Open_Round__3B.TwoByTwo;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TwoByTwo {
    private static final int INF = (int) 1e9;

    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int h, w;
    int[][] board;
    int[] dist = new int[16 * 16 * 16 * 16 * 16 * 16];
    Queue<State> que;

    class State implements Comparable<State> {
        int x, y, x1, y1, x2, y2, curDist;


        public State(int x, int y, int x1, int y1, int x2, int y2, int curDist) {
            this.x = x;
            this.y = y;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.curDist = curDist;
        }

        @Override
        public int compareTo(State o) {
            return curDist - o.curDist;
        }

        public int getId() {
            return TwoByTwo.this.getId(x, y, x1, y1, x2, y2);
        }
    }

    public int minimal(String[] _board) {
        h = _board.length;
        w = _board[0].length();
        board = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                board[i][j] = _board[i].charAt(j) == '#' ? 1 : 0;
            }
        }
        Arrays.fill(dist, INF);
        que = new PriorityQueue<>();
        offer(0, 0, 0, 0, 0, 0, board[0][0] + board[0][1] + board[1][0] + board[1][1]);

        int[] dir = {0, 1, 0, -1, 0};
        int[] dirF = {0, 2, 0, -1, 0};
        int[] dirT = {1, 2, 1, -1, 1};
        while (!que.isEmpty()) {
            State s = que.poll();
            if (dist[s.getId()] < s.curDist) continue;

            int x = s.x;
            int y = s.y;
            int x1 = s.x1;
            int y1 = s.y1;
            int x2 = s.x2;
            int y2 = s.y2;
            int curDist = s.curDist;

            if (x == h - 2 && y == w - 2) return curDist;

            for (int dx = 0; dx <= 1; dx++)
                for (int dy = 0; dy <= 1; dy++) {
                    int nx1 = x + dx;
                    int ny1 = y + dy;
                    offer(x, y, nx1, ny1, x2, y2, curDist);
                    int nx2 = x + dx;
                    int ny2 = y + dy;
                    offer(x, y, x1, y1, nx2, ny2, curDist);
                }

            for (int d = 0; d < 4; d++) {
                int nx = x + dir[d];
                int ny = y + dir[d + 1];
                if (!in(0, nx, h - 1) || !in(0, ny, w - 1)) continue;

                int cost = 0;
                for (int dx = dirF[d]; dx <= dirT[d]; dx++)
                    for (int dy = dirF[d + 1]; dy <= dirT[d + 1]; dy++) {
                        int nnx = x + dx;
                        int nny = y + dy;
                        if (nnx == x1 && nny == y1) continue;
                        if (nnx == x2 && nny == y2) continue;
                        cost += board[nnx][nny];
                    }
                offer(nx, ny, x1, y1, x2, y2, curDist + cost);
            }
        }
        throw new AssertionError();
    }

    private int getId(int x, int y, int x1, int y1, int x2, int y2) {
        int id = x;
        id *= 16;
        id += y;
        id *= 16;
        id += x1;
        id *= 16;
        id += y1;
        id *= 16;
        id += x2;
        id *= 16;
        id += y2;
        return id;
    }

    private void offer(int x, int y, int x1, int y1, int x2, int y2, int curDist) {
        if (x1 * w + y1 > x2 * w + y2) {
            offer(x, y, x2, y2, x1, y1, curDist);
            return;
        }
        if (dist[getId(x, y, x1, y1, x2, y2)] > curDist) {
            dist[getId(x, y, x1, y1, x2, y2)] = curDist;
            que.offer(new State(x, y, x1, y1, x2, y2, curDist));
        }
    }

    private boolean in(int l, int x, int r) {
        return l <= x && x < r;
    }
}
