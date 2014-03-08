package on2013_12.on2013_12_08_Recruite_2013.TaskG;



import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TaskG {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            int W = in.nextInt(), H = in.nextInt();
            char[][] cs = new char[H][W];
            for (int i = 0; i < H; i++) {
                cs[i] = in.nextLine().toCharArray();
            }
            int[][] comp = new int[H][W];
            for (int i = 0; i < H; i++) {
                Arrays.fill(comp[i], -1);
            }
            int p = 0;
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (comp[i][j] == -1 && cs[i][j] != ' ') {
                        dfs(comp, cs, i, j, p++);
                    }
                }
            }
            debug("p", p);
            int n = in.nextInt();
            long[][] cost = new long[26][26];
            for (int i = 0; i < cost.length; i++) {
                Arrays.fill(cost[i], Integer.MAX_VALUE);
            }
            debug(cs);
            for (int i = 0; i < n; i++) {
                int C = in.nextInt();
                int a = in.next().charAt(0) - 'A';
                int b = in.next().charAt(0) - 'A';
                cost[a][b] = cost[b][a] =Math.min(cost[a][b], C);
            }
            long[][] graph = new long[p][p];
            for (int i = 0; i < p; i++) {
                Arrays.fill(graph[i], Integer.MAX_VALUE);
            }
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++)if(cs[i][j]!=' ') {
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (0 <= nx && nx < cs.length && 0 <= ny && ny < cs[0].length && cs[nx][ny] != ' ') {
//                            dfs(comp,cs,nx,ny,p);
                            if (comp[i][j] != comp[nx][ny]) {
                                graph[comp[i][j]][comp[nx][ny]] = cost[cs[i][j] - 'A'][cs[nx][ny] - 'A'];
                            }
                        }
                    }
                }

            }
            PriorityQueue<E> que = new PriorityQueue<>();
            UnionFind uf = new UnionFind(p);
            for (int i = 0; i < p; i++) {
                for (int j = 0; j < p; j++) {
                    E e = new E();
                    e.a = i;
                    e.b = j;
                    e.c = graph[i][j];
                    que.offer(e);
                }
            }
            long res = 0;
            while (!que.isEmpty()) {
                E e = que.poll();
                if (uf.find(e.a, e.b)) continue;
                res += e.c;
                debug(e.c);
                uf.union(e.a, e.b);
            }
            out.println(res >= Integer.MAX_VALUE ? "IMPOSSIBLE" : res);
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    class E implements Comparable<E> {
        int a, b;
        long c;
        @Override
        public int compareTo(E o) {
            return (int) Math.signum(c - o.c);
        }
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private void dfs(int[][] comp, char[][] cs, int x, int y, int p) {
        if (comp[x][y] != -1) return;
        comp[x][y] = p;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < cs.length && 0 <= ny && ny < cs[0].length) {
                if(cs[x][y] == cs[nx][ny])
                dfs(comp, cs, nx, ny, p);
            }
        }
    }


}
