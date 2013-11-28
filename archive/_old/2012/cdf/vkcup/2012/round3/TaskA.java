package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Integer>[] es = ArrayUtils.createArray(n, new ArrayList<Integer>());
        ArrayList<Integer>[] rs = ArrayUtils.createArray(n, new ArrayList<Integer>());
        for (int i = 0; i < es.length; i++) es[i] = new ArrayList<Integer>();
        for (int i = 0; i < rs.length; i++) rs[i] = new ArrayList<Integer>();
        boolean[] one = new boolean[n];
        boolean[] two = new boolean[n];
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            if (t == 1) one[i] = true;
            if (t == 2) two[i] = true;
        }
        for (int i = 0; i < m; i++) {
            int s = in.nextInt() - 1, t = in.nextInt() - 1;
            es[s].add(t);
            rs[t].add(s);
        }
        Queue<Integer> que = new LinkedList<Integer>();
        for (int i = 0; i < n; i++) if (two[i]) que.offer(i);
        boolean[] can2 = new boolean[n];
        while (!que.isEmpty()) {
            int cur = que.poll();
            if (can2[cur]) continue;
            can2[cur] = true;
            if (!one[cur]) {
                for (int nxt : rs[cur]) {
                    que.offer(nxt);
                }
            }
        }
        for (int i = 0; i < n; i++) if (one[i]) que.offer(i);
        boolean[] can1 = new boolean[n];
        while (!que.isEmpty()) {
            int cur = que.poll();
            if (can1[cur]) continue;
            can1[cur] = true;
            for (int nxt : es[cur]) {
                que.offer(nxt);
            }
        }
        for (int i = 0; i < n; i++) {
            out.println(can1[i] && can2[i] ? 1 : 0);
        }
    }
}
