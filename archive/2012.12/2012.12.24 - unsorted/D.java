package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class D {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int h = in.nextInt(), w = in.nextInt(), x0 = in.nextInt() - 1, y0 = in.nextInt() - 1, B = in.nextInt();
        boolean[][] box = new boolean[h][w];
        for (int i = 0; i < B; i++) box[in.nextInt() - 1][in.nextInt() - 1] = true;
        char[][] map = new char[h][w];
        for (int i = 0; i < h; i++) map[i] = in.next().toCharArray();
        boolean ok = true;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) if (box[i][j] && map[i][j] != '!') ok = false;
        if (ok) {
            out.println("YES"); return;
        }
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(x0); que.offer(y0);
        boolean[][] canVisit = new boolean[h][w];
        canVisit[x0][y0] = true;
        while (!que.isEmpty()) {
            int x = que.poll(), y = que.poll();
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (0 <= nx && nx < h && 0 <= ny && ny < w && map[nx][ny] != '#' && !canVisit[nx][ny]) {
                    canVisit[nx][ny] = true;
                    que.offer(nx); que.offer(ny);
                }
            }
        }
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                if (!canVisit[i][j] && box[i][j]) {
                    if (map[i][j] == '.') {
                        out.println("NO"); return;
                    }
                    box[i][j] = false;
                    map[i][j] = '.';
                }
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) if (!canVisit[i][j] && map[i][j] == '!') B--;
        if (B <= 0) {
            out.println("YES"); return;
        }
        int have = 0;
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) if (canVisit[i][j] && map[i][j] == '!') have++;
        for (int x = 0; x < h; x++)
            for (int y = 0; y < w; y++)
                if (canVisit[x][y]) for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];
                    int sub = map[x][y] == '!' ? 1 : 0;
                    if (0 <= nx && nx < h && 0 <= ny && ny < w && canVisit[nx][ny]) {
                        if (map[nx][ny] == '!') sub++;
                    } else continue;
                    if (have - sub >= B) {
                        out.println("YES"); return;
                    }
                }
        out.println("NO");
    }
}
