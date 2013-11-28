package tmp;

import java.util.LinkedList;
import java.util.Queue;

public class ArcadeManao {
    public int shortestLadder(String[] level, int coinRow, int coinColumn) {
        for (int L = 0; ; L++) {
            if (possible(level, coinRow, coinColumn, L)) return L;
        }
    }

    private boolean possible(String[] level, int coinRow, int coinColumn, int L) {
        coinRow--; coinColumn--;
        Queue<Integer> que = new LinkedList<Integer>();
        int h = level.length, w = level[0].length();
        boolean[][] vis = new boolean[h][w];
        que.offer(coinRow); que.offer(coinColumn);
        vis[coinRow][coinColumn] = true;
        while (!que.isEmpty()) {
            int x = que.poll(), y = que.poll();
            for (int dy = -1; dy <= 1; dy += 2) {
                int ny = y + dy;
                if (0 <= ny && ny < w && !vis[x][ny] && level[x].charAt(ny) == 'X') {
                    que.offer(x); que.offer(ny);
                    vis[x][ny] = true;
                }
            }
            for (int dx = -L; dx <= L; dx++) {
                int nx = x + dx;
                if (0 <= nx && nx < h && !vis[nx][y] && level[nx].charAt(y) == 'X') {
                    que.offer(nx);
                    que.offer(y);
                    vis[nx][y] = true;
                }
            }
        }
        return vis[h - 1][0];
    }
}
