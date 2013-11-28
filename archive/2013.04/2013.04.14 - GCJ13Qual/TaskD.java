package tmp;

import net.ogiekako.algorithm.dataStructure.intCollection.IntArrayList;
import net.ogiekako.algorithm.dataStructure.intCollection.IntIterator;
import net.ogiekako.algorithm.dataStructure.intCollection.IntQueue;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskD {
    int T = 201;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int K = in.nextInt(), N = in.nextInt();
        int[] init = new int[K];
        for (int i = 0; i < K; i++) init[i] = in.nextInt();
        int[] lock = new int[N];
        int[][] key = new int[N][];
        for (int i = 0; i < N; i++) {
            lock[i] = in.nextInt();
            key[i] = new int[in.nextInt()];
            for (int j = 0; j < key[i].length; j++) key[i][j] = in.nextInt();
        }
        int[] last = new int[T];
        for (int i : init) last[i]++;
        for (int i : lock) last[i]--;
        for (int[] i : key) for (int j : i) last[j]++;
        for (int i : last)
            if (i < 0) {
                out.println("IMPOSSIBLE");
                return;
            }
        boolean[] used = new boolean[N];
        int[] has = new int[T];
        for (int i : init) has[i]++;
        if (!possible(used, has, lock, key)) {
            out.println("IMPOSSIBLE");
            return;
        }
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; ; j++) {
                if (j >= N) throw new AssertionError();
                if (used[j]) continue;
                if (has[lock[j]] <= 0) continue;
                has[lock[j]]--;
                for (int k : key[j]) has[k]++;
                used[j] = true;
                if (possible(used, has, lock, key)) {
                    res[i] = j; break;
                }
                has[lock[j]]++;
                for (int k : key[j]) has[k]--;
                used[j] = false;
            }
        }
        for (int i = 0; i < res.length; i++) {
            out.printFormat("%d%c", res[i] + 1, i == res.length - 1 ? '\n' : ' ');
        }
    }

    private boolean possible(boolean[] used, int[] has, int[] lock, int[][] key) {
        int N = used.length;
        IntArrayList[] graph = new IntArrayList[T];
        for (int i = 0; i < N; i++)
            if (!used[i]) {
                if (graph[lock[i]] == null) graph[lock[i]] = new IntArrayList();
                for (int j : key[i]) graph[lock[i]].add(j);
            }
        IntQueue que = new IntQueue();
        boolean[] visited = new boolean[T];
        for (int i = 0; i < T; i++)
            if (has[i] > 0) {
                que.offer(i);
                visited[i] = true;
            }
        while (!que.isEmpty()) {
            int i = que.poll();
            if (graph[i] == null) continue;
            for (IntIterator it = graph[i].iterator(); it.hasNext(); ) {
                int j = it.next();
                if (!visited[j]) {
                    que.offer(j);
                    visited[j] = true;
                }
            }
        }
        for (int i = 0; i < N; i++) if (!used[i] && !visited[lock[i]]) return false;
        return true;
    }
}
