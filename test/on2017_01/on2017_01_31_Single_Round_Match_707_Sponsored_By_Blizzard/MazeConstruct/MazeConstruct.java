package on2017_01.on2017_01_31_Single_Round_Match_707_Sponsored_By_Blizzard.MazeConstruct;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MazeConstruct {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int[] ds = {0, 1, 0, -1, 0};

    int solve(char[][] map) {
        int h = map.length, w = map[0].length;
        int[][] dist = new int[h][w];
        Arrays.stream(dist).forEach(d -> Arrays.fill(d, -1));
        Queue<Integer> que = new LinkedList<>();
        que.offer(0);
        que.offer(0);
        que.offer(0);
        while (!que.isEmpty()) {
            int x = que.poll(), y = que.poll(), step = que.poll();
            if (0 <= x && x < h && 0 <= y && y < w && dist[x][y] < 0 && map[x][y] == '.') {
                dist[x][y] = step;
                for (int d = 0; d < 4; d++) {
                    que.offer(x + ds[d]);
                    que.offer(y + ds[d + 1]);
                    que.offer(step + 1);
                }
            }
        }
        return dist[h - 1][w - 1];
    }

    public String[] toS(char[][] S) {
        String[] res = new String[S.length];
        for (int i = 0; i < res.length; i++) res[i] = new String(S[i]);
        return res;
    }

    public char[][] init(int h, int w) {
        char[][] res = new char[h][w];
        for (int i = 0; i < h; i++) Arrays.fill(res[i], '.');
        return res;
    }

    public String[] construct(int k) {
        if (k <= 98) {
            if (k < 50) {
                if (solve(init(1, k + 1)) != k) throw new AssertionError();
                return toS(init(1, k + 1));
            }
            if (solve(init(50, k - 48)) != k) throw new AssertionError();
            return toS(init(50, k - 48));
        }
        int h = 50, w = 50 - k % 2;
        char[][] map = init(h, w);
        if (solve(map) == k) {
            return toS(map);
        }
        for (int i = 0; ; i++) {
            for (int j = 0; j < w - 1; j++) {
                int nj = i % 2 == 0 ? j : w - 1 - j;
                map[i * 2 + 1][nj] = '#';
                if (solve(map) == k) {
                    return toS(map);
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 2; i <= 1000; i++) {
            System.out.println(i);
            new MazeConstruct().construct(i);
        }
    }
}
