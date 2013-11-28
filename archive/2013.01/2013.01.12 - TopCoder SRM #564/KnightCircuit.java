package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.LinkedList;
import java.util.Queue;

public class KnightCircuit {
    private int[] dx, dy;

    public long maxSize(int w, int h, int a, int b) {
        int g = MathUtils.gcd(a, b);
        if (g > 1) {
            w = (w + g - 1) / g;
            h = (h + g - 1) / g;
            return maxSize(w, h, a / g, b / g);
        }
        if (w > 30 && h > 30) {
            if (a % 2 == 1 && b % 2 == 1) {
                return ((long) h * w + 1) / 2;
            } else
                return (long) h * w;
        }
        boolean[][] visited = new boolean[h][w];
        dx = new int[]{-a, -a, -b, -b, a, a, b, b};
        dy = new int[]{-b, b, -a, a, -b, b, -a, a};
        long res = 0;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                if (!visited[i][j]) {
                    Queue<Integer> que = new LinkedList<Integer>();
                    que.offer(i); que.offer(j);
                    visited[i][j] = true;
                    int count = 1;
                    while (!que.isEmpty()) {
                        int x = que.poll(), y = que.poll();
                        for (int d = 0; d < 8; d++) {
                            int nx = x + dx[d];
                            int ny = y + dy[d];
                            if (0 <= nx && nx < h && 0 <= ny && ny < w && !visited[nx][ny]) {
                                que.offer(nx); que.offer(ny);
                                visited[nx][ny] = true;
                                count++;
                            }
                        }
                    }
                    res = Math.max(res, count);
                }
        return res;
    }
}
