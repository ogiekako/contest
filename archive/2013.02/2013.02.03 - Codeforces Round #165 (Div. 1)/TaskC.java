package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        V[] vs = new V[n];
        for (int i = 0; i < n; i++) vs[i] = new V();
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1;
            int flow = in.nextInt();
            E e = new E(vs[from], vs[to], flow, i);
            vs[from].es.add(e);
            vs[to].es.add(e);
        }
        for (int i = 0; i < n; i++) {
            for (E e : vs[i].es) vs[i].f += e.flow;
            vs[i].f /= 2;
        }
        Queue<V> que = new LinkedList<V>();
        que.offer(vs[0]);
        int[] res = new int[m];
        while (!que.isEmpty()) {
            V v = que.poll();
            for (E e : v.es)
                if (!e.resolved) {
                    res[e.id] = e.from == v ? 0 : 1;
                    e.resolved = true;
                    V to = e.from == v ? e.to : e.from;
                    to.f -= e.flow;
                    if (to != vs[n - 1] && to.f == 0) que.offer(to);
                }
        }
        for (int r : res) out.println(r);
    }
    class V {
        List<E> es = new ArrayList<E>();
        int f;
    }

    class E {
        V from, to;
        int flow;
        boolean resolved = false;
        private final int id;

        E(V from, V to, int flow, int id) {
            this.flow = flow;
            this.from = from;
            this.to = to;
            this.id = id;
        }
    }
}
