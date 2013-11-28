package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.IntegerUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

public class CoalScam {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m1 = in.nextInt(), m2 = in.nextInt();
        UnionFind uf = new UnionFind(n);
        Edge[] e2s = new Edge[m1];
        for (int i = 0; i < e2s.length; i++) e2s[i] = new Edge(in.nextInt(), in.nextInt(), in.nextLong());
        Edge[] e1s = new Edge[m2];
        for (int i = 0; i < e1s.length; i++) e1s[i] = new Edge(in.nextInt(), in.nextInt(), in.nextLong());
        Arrays.sort(e1s, Collections.reverseOrder());
        Arrays.sort(e2s);
        int count = 0;
        long res1 = 0;
        for (Edge e : e1s) {
            if (uf.find(e.s, e.t)) continue;
            uf.union(e.s, e.t);
            res1 += e.cost;
            count++;
        }
        long res2 = res1;
        for (Edge e : e2s) {
            if (uf.find(e.s, e.t)) continue;
            uf.union(e.s, e.t);
            res2 += e.cost;
            count++;
        }
        if (count < n - 1) {
            out.println("Impossible");
        } else {
            out.printf("%d %d\n", res1, res2);
        }
    }
    class Edge implements Comparable<Edge> {
        int s, t;
        long cost;

        Edge(int s, int t, long cost) {
            this.cost = cost;
            this.s = s;
            this.t = t;
        }

        public int compareTo(Edge o) {
            return IntegerUtils.compare(cost, o.cost);
        }
    }
}
