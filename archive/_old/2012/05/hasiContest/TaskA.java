package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int m = in.nextInt(), n = in.nextInt();
        int[] as = new int[m];
        int[] bs = new int[m];
        boolean[][] can = new boolean[305][610];
        for (int i = 0; i < m; i++) {
            as[i] = in.nextInt(); bs[i] = in.nextInt();
            int d = bs[i] - as[i] + 305;
            for (int j = as[i]; j < 305; j++) can[j][d] = true;
        }
        int[] min = new int[n + 600];
        int INF = 1 << 28;
        Arrays.fill(min, INF);
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(1);
        min[1] = 1;
        while (!que.isEmpty()) {
            int cur = que.poll();
            int p = Math.min(304, cur);
            for (int i = 0; i < 610; i++)
                if (can[p][i]) {
                    int nxt = cur + i - 305;
                    if (nxt < n + 600 && min[nxt] == INF) {
                        min[nxt] = min[cur] + 1;
                        que.offer(nxt);
                    }
                }
        }
        out.println(min[n] == INF ? -1 : min[n]);
    }
}
