package on2014_03.on2014_03_03_.C;



import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class C {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        BidirectionalGraph g1 = read(in);
        BidirectionalGraph g2 = read(in);
        calc(g1);
        int D1 = D, R1 = R;
        calc(g2);
        int D2 = D, R2 = R;
        int max = D1 + 1 + D2;
        int min = Math.max(R1 + 1 + R2, Math.max(D1, D2));
        out.printFormat("%d %d\n", min, max);
    }
    int D, R;
    private void calc(BidirectionalGraph g) {
        int n = g.size();
        boolean[] visited = new boolean[n];
        R = Integer.MAX_VALUE;
        D = 0;
        for (int i = 0; i < n; i++) {
            Queue<Integer> que = new LinkedList<>();
            Arrays.fill(visited, false);
            visited[i] = true;
            que.offer(i);
            que.offer(0);
            int max = 0;
            while (!que.isEmpty()) {
                int v = que.poll();
                int d = que.poll();
                D = Math.max(D, d);
                max = Math.max(max, d);
                for (Edge e : g.edges(v)) {
                    int u = e.to();
                    if (!visited[u]) {
                        visited[u] = true;
                        que.offer(u);
                        que.offer(d + 1);
                    }
                }
            }
            R = Math.min(R, max);
        }
    }

    private BidirectionalGraph read(MyScanner in) {
        int n = in.nextInt();
        int m = in.nextInt();
        BidirectionalGraph g = new BidirectionalGraph(n);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            g.add(a, b);
        }
        return g;
    }
}
