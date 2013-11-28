package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TaskB {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        System.err.println(testNumber);
        int H = in.nextInt();
        int N = in.nextInt(), M = in.nextInt();
        int[][] F = new int[N][M];
        int[][] C = new int[N][M];
        for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) C[i][j] = in.nextInt();
        for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) F[i][j] = in.nextInt();
        HashMap<Entry, Double> map = new HashMap<Entry, Double>();
        PriorityQueue<Entry> que = new PriorityQueue<Entry>();
        Entry init = new Entry(0, 0, H, 0);
        que.offer(init);
        map.put(init, 0.0);
        while (!que.isEmpty()) {
            Entry cur = que.poll();
            int x = cur.x;
            int y = cur.y;
            if (x == N - 1 && y == M - 1) {
                out.printf("Case #%d: %.12f\n", testNumber, cur.time);
                return;
            }
            int height = cur.height;
            int addTime = (cur.height - F[x][y] >= 20) ? 1 : 10;
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (0 <= nx && nx < N && 0 <= ny && ny < M) {
                    int cF = Math.max(F[x][y], cur.height);
                    int cC = C[x][y];
                    int nF = Math.max(F[nx][ny], cur.height);
                    int nC = C[nx][ny];
                    if (nC - cF >= 50 && cC - nF >= 50 && nC - nF >= 50) {
                        Entry nxt = new Entry(nx, ny, height - addTime * 10, cur.time + addTime);
                        Double tmp = map.get(nxt);
                        if (tmp == null || tmp > nxt.time) {
                            map.put(nxt, nxt.time);
                            que.offer(nxt);
                        }
                        if (height == H) {
                            nxt = new Entry(nx, ny, height, cur.time);
                            tmp = map.get(nxt);
                            if (tmp == null || tmp > nxt.time) {
                                map.put(nxt, nxt.time);
                                que.offer(nxt);
                            }
                        }
                    } else if (nC - F[x][y] >= 50) {
                        int nHeight = nC - 50;
                        double waitTime = (height - nHeight) / 10.0;
                        if (nHeight < height) {
                            Entry nxt = new Entry(x, y, nHeight, cur.time + waitTime);
                            Double tmp = map.get(nxt);
                            if (tmp == null || tmp > nxt.time) {
                                map.put(nxt, nxt.time);
                                que.offer(nxt);
                            }
                        }
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    class Entry implements Comparable<Entry> {
        int x, y;
        int height;
        double time;

        Entry(int x, int y, int height, double time) {
            this.height = Math.max(0, height);
            this.time = time;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry entry = (Entry) o;

            if (height != entry.height) return false;
            if (x != entry.x) return false;
            if (y != entry.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + height;
            return result;
        }

        public int compareTo(Entry o) {
            return Double.compare(time, o.time);
        }
    }
}
