package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
public class Block3Checkers {
    public int blockThem(String[] board) {
        n = board.length;
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) {
            map[i] = board[i].toCharArray();
        }
        for (String s : board) System.out.println(s);
        int p = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'A') {
                    Tx[p] = i;
                    Ty[p++] = j;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs(Tx[i] - Tx[j]) + Math.abs(Ty[i] - Ty[j]) == 1) return 100;
            }
        }

        in = new V[n][n];
        out = new V[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                in[i][j] = new V();
                out[i][j] = new V();
            }
        }
        res = 6;
        solve(map, 0, 0);
        return res;
    }
    int n;
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    int[] Tx = new int[3], Ty = new int[3];
    int counter = 0;
    int res;
    V[][] in = new V[n][n];
    V[][] out = new V[n][n];

    void solve(char[][] map, int q, int used) {
        if (used >= res) return;
        if (q == 2) {
            res = Math.min(res, used);
            return;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                in[i][j].es.clear();
                out[i][j].es.clear();
                int cap = 1;
                if (map[i][j] == 'A' || map[i][j] == 'O') cap = 10;
                if (map[i][j] == 'N') cap = 0;
                make(in[i][j], out[i][j], cap);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int d = 0; d < 4; d++) {
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                        make(out[i][j], in[nx][ny], 10);
                    }
                }
            }
        }
        V t = out[Tx[q]][Ty[q]];
        V s = new V();
        for (int i = q + 1; i < 3; i++) {
            make(s, in[Tx[i]][Ty[i]], 10);
        }
        int flow = dinic(s, t);
        if (used + flow >= res) return;
        if (q == 1) {
            res = Math.min(res, used + flow);
        } else if (flow == 0) {
            solve(map, q + 1, used);
        } else {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (s.visited != in[i][j].visited) {
                        if (s.visited == out[i][j].visited) throw new AssertionError();
                        map[i][j] = 'O';
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == '.' && s.visited == in[i][j].visited && s.visited != out[i][j].visited) {
                        char[][] res1 = new char[n][];
                        for (int i1 = 0; i1 < res1.length; i1++) {
                            res1[i1] = map[i1].clone();
                        }
                        map = res1;
                        map[i][j] = 'N';
                        solve(map, q, used + 1);
                        map[i][j] = 'O';
                        solve(map, q, used);
                        return;
                    }
                }
            }
        }
    }

    private int dinic(V s, V t) {
        int res = 0;
        for (; ; ) {
            bfs(s);
            if (t.visited != counter) return res;
            for (int f; (f = dfs(s, t, 10)) > 0; ) res += f;
        }
    }
    private int dfs(V s, V t, int possible) {
        if (s == t) return possible;
        for (; s.pos >= 0; s.pos--) {
            E e = s.es.get(s.pos);
            if (e.cap != 0 && s.level + 1 == e.to.level) {
                int cur = dfs(e.to, t, Math.min(possible, e.cap));
                if (cur > 0) {
                    e.cap -= cur;
                    e.rev.cap += cur;
                    return cur;
                }
                e.cap -= cur;
                e.rev.cap += cur;
            }
        }
        return 0;
    }
    private void bfs(V s) {
        Queue<V> que = new LinkedList<>();
        que.offer(s);
        s.visited = ++counter;
        s.level = 0;
        while (!que.isEmpty()) {
            V u = que.poll();
            u.pos = u.es.size() - 1;
            for (E e : u.es)
                if (e.cap > 0) {
                    V v = e.to;
                    if (v.visited == counter) continue;
                    v.visited = counter;
                    v.level = u.level + 1;
                    que.offer(v);
                }
        }
    }

    private void make(V u, V v, int cap) {
        E e = new E(v, cap);
        E r = new E(u, 0);
        e.rev = r;
        r.rev = e;
        u.es.add(e);
        v.es.add(r);
    }

    class V {
        List<E> es = new ArrayList<>();
        int visited;
        int level;
        int pos;
    }

    class E {
        private final V to;
        int cap;
        E rev;
        public E(V to, int cap) {
            this.to = to;
            this.cap = cap;
        }
    }
}
