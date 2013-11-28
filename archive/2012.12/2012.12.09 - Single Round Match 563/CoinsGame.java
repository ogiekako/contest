package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CoinsGame {
    int h, w;
    String[] board;
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    int MOD = (int) (1e9 + 9);

    public int ways(String[] board) {
        h = board.length; w = board[0].length();
        this.board = board;
        Queue<Integer> que = new LinkedList<Integer>();
        boolean[][][][] canGoal = new boolean[h][w][h][w];
        int numCells = 0;
        for (int i1 = 0; i1 < h; i1++)
            for (int j1 = 0; j1 < w; j1++)
                if (board[i1].charAt(j1) == '.') {
                    numCells++;
                    for (int x = 0; x < h; x++) {
//                        que.offer(i1<<10 | j1); que.offer(x<<10 | 1023);
//                        que.offer(i1<<10 | j1); que.offer(x << 10 | w);

                        que.offer(i1 << 24 | j1 << 16 | x << 8 | 255);
                        que.offer(i1 << 24 | j1 << 16 | x << 8 | w);
                    }
                    for (int y = 0; y < w; y++) {
//                        que.offer(i1 << 10 | j1); que.offer(1023<<10 | y);
//                        que.offer(i1<<10 | j1); que.offer(h<<10 | y);
                        que.offer(i1 << 24 | j1 << 16 | 255 << 8 | y);
                        que.offer(i1 << 24 | j1 << 16 | h << 8 | y);
                    }
                }
        int[] b1s = new int[2], b2s = new int[2];
        while (!que.isEmpty()) {
//            int p1 = que.poll(),  p2 = que.poll();
            int p = que.poll();
            int x1 = p >> 24 & 255;
            int y1 = p >> 16 & 255;
            int x2 = p >> 8 & 255;
            int y2 = p & 255;
//            int x1 = p1>>10, y1 = p1 & 1023;
//            int x2 = p2>>10, y2 = p2 & 1023;
            if (x1 == 255) x1 = -1;
            if (y1 == 255) y1 = -1;
            if (x2 == 255) x2 = -1;
            if (y2 == 255) y2 = -1;

            for (int d = 0; d < 4; d++) {
                before(x1, y1, d, b1s);
                before(x2, y2, d, b2s);
//                debug(b1s,b2s);
                for (int b1 : b1s)
                    if (b1 != -1)
                        for (int b2 : b2s)
                            if (b2 != -1) {
                                int nx1 = b1 >> 10, ny1 = b1 & 1023;
                                int nx2 = b2 >> 10, ny2 = b2 & 1023;
                                if (canGoal[nx1][ny1][nx2][ny2]) continue;
                                canGoal[nx1][ny1][nx2][ny2] = true;
                                canGoal[nx2][ny2][nx1][ny1] = true;
//                        que.offer(nx1<<10|ny1); que.offer(nx2<<10| ny2);
                                que.offer(nx1 << 24 | ny1 << 16 | nx2 << 8 | ny2);
                            }
            }
        }

        int[] mod = new int[h * w];
        for (int i = 0; i < mod.length; i++) mod[i] = i % w;
        UnionFind uf = new UnionFind(h * w);
        for (int p1 = 0; p1 < h * w; p1++)
            for (int p2 = 0; p2 < p1; p2++) {
                int x1 = p1 / w, y1 = mod[p1];
                int x2 = p2 / w, y2 = mod[p2];
                if (board[x1].charAt(y1) == '.' && board[x2].charAt(y2) == '.') {
                    if (!canGoal[x1][y1][x2][y2])
                        uf.union(p1, p2);

//                    if(canGoal[x1][y1][x2][y2]){
//                        debug(x1,y1,x2,y2);
//                    }
                }
            }

        List<Integer> list = new ArrayList<Integer>();
        for (int p1 = 0; p1 < h * w; p1++) {
            int x1 = p1 / w, y1 = p1 % w;
            if (board[x1].charAt(y1) == '.' && uf.root(p1) == p1) {
                list.add(uf.size(p1));
            }
        }
//        debug("list",list);
        long res = 1;
        for (int i = 0; i < numCells; i++) res = res * 2 % MOD;
        res--;// no
        if (res < 0) res += MOD;
        for (int l : list) {
            long sub = 1;
            for (int i = 0; i < l; i++) sub = sub * 2 % MOD;
            sub--;
            res -= sub;
            if (res < 0) res += MOD;
        }
        return (int) res;
    }
//    static void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
//    }

    private void before(int x, int y, int d, int[] res) {
        int nx = x + dx[d];
        int ny = y + dy[d];
        res[0] = res[1] = -1;
        if (0 <= nx && nx < h && 0 <= ny && ny < w && 0 <= x && x < h && 0 <= y && y < w && board[nx].charAt(ny) == '#') {
            res[1] = x << 10 | y;
        }
        nx = x + dx[d ^ 2];
        ny = y + dy[d ^ 2];
        if (0 <= nx && nx < h && 0 <= ny && ny < w && board[nx].charAt(ny) == '.') {
            res[0] = nx << 10 | ny;
        }
    }

}
