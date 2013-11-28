package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.KMP;

import java.util.ArrayList;
import java.util.List;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        Node[] vs = new Node[n];
        for (int i = 0; i < n; i++) vs[i] = new Node();
        for (int i = 0; i < n - 1; i++) {
            int p = in.nextInt() - 1;
            String s = in.next();
            vs[p].es.add(new Edge(vs[i + 1], s));
        }
        t = in.next();
        link = KMP.generateFailureLink(t);
        int res = dfs(vs[0], 0);
        out.println(res);
    }

    int[] link;
    String t;

    private int dfs(Node root, int state) {
        int res = 0;
        for (Edge e : root.es) {
            res += dfs(e, state);
        }
        return res;
    }

    private int dfs(Edge edge, int state) {
        int res = 0;
        for (int i = 0; i < edge.s.length(); i++) {
            while (state == t.length() || state >= 0 && t.charAt(state) != edge.s.charAt(i)) state = link[state];
            state++;
            if (state == t.length()) res++;
        }
        return res + dfs(edge.to, state);
    }

    class Node {
        List<Edge> es = new ArrayList<Edge>();
    }

    class Edge {
        String s;
        Node to;

        public Edge(Node to, String s) {
            this.to = to; this.s = s;
        }
    }
}
