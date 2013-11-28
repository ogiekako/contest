package tmp;

import java.util.LinkedList;
import java.util.Queue;

public class KnightCircuit2 {
    public int maxSize(int w, int h) {
        if (w >= 5 && h >= 5) return w * h;
        boolean[][] vis = new boolean[h][w];
        int res = 0;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                if (!vis[i][j]) {
                    Queue<Integer> que = new LinkedList<Integer>();
                    que.offer(i); que.offer(j);
                    vis[i][j] = true;
                    int cnt = 1;
                    while (!que.isEmpty()) {
                        int x = que.poll(), y = que.poll();
                        for (int dx = -2; dx <= 2; dx++)
                            for (int dy = -2; dy <= 2; dy++)
                                if (dx * dx + dy * dy == 5) {
                                    int nx = x + dx;
                                    int ny = y + dy;
                                    if (0 <= nx && nx < h && 0 <= ny && ny < w && !vis[nx][ny]) {
                                        vis[nx][ny] = true;
                                        cnt++;
                                        que.offer(nx); que.offer(ny);
                                    }
                                }
                    }
                    res = Math.max(res, cnt);
                }
        return res;
    }
}
