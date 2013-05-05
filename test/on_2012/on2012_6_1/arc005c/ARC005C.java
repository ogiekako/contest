package on_2012.on2012_6_1.arc005c;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class ARC005C {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int H = in.nextInt(), W = in.nextInt();
        char[][] map = new char[H][W];
        for (int i = 0; i < H; i++) map[i] = in.next().toCharArray();
        int[][] dist = new int[H][W];
        for (int i = 0; i < H; i++) for (int j = 0; j < W; j++) dist[i][j] = Integer.MAX_VALUE;
        Queue<Integer> que = new LinkedList<Integer>();
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                if (map[i][j] == 's') {
                    que.offer(i); que.offer(j);
                    dist[i][j] = 0;
                }
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        while (!que.isEmpty()) {
            int x = que.poll();
            int y = que.poll();
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (0 <= nx && nx < H && 0 <= ny && ny < W) {
                    if (map[nx][ny] == 'g') {
                        out.println("YES");
                        return;
                    }
                    int nDist = dist[x][y] + (map[nx][ny] == '#' ? 1 : 0);
                    if (nDist < dist[nx][ny] && nDist <= 2) {
                        que.offer(nx); que.offer(ny);
                        dist[nx][ny] = nDist;
                    }
                }
            }
        }
        out.println("NO");
    }
}
