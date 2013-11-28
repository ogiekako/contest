package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int[] ss = new int[n];
        int[] ts = new int[n];
        int[] cs = new int[n];
        for (int i = 0; i < n; i++) {
            ss[i] = in.nextInt();
            ts[i] = ss[i] + in.nextInt();
            cs[i] = in.nextInt();
        }
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i < n; i++) {
            set.add(ss[i]); set.add(ts[i]);
        }
        int[] numbers = tois(set.toArray(new Integer[0]));
        for (int i = 0; i < n; i++) {
            ss[i] = Arrays.binarySearch(numbers, ss[i]);
            ts[i] = Arrays.binarySearch(numbers, ts[i]);
        }
        PrimalDual_negative pd = new PrimalDual_negative(numbers.length);
        for (int i = 0; i < numbers.length - 1; i++) pd.make(i, i + 1, 60, 0);
        E[] es = new E[n];
        for (int i = 0; i < n; i++) {
//            es[i] = vs[ss[i]].add(vs[ts[i]], 1, -cs[i], 0);
            es[i] = pd.make(ss[i], ts[i], 1, -cs[i]);
        }
        pd.minCostFlow(0, numbers.length - 1, k);
//        System.err.println(res);
        for (int i = 0; i < es.length; i++) {
            if (i > 0) out.print(" ");
            if (es[i].cap == 0) {
                out.print(1);
            } else {
                out.print(0);
            }
        }
        out.println();
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }

    class PrimalDual_negative {
        static final long INF = 1L << 60;
        int n;
        V[] vs;

        PrimalDual_negative(int nn) {
            n = nn;
            vs = new V[n];
            for (int i = 0; i < n; i++) vs[i] = new V(i);
        }

        public E make(int from, int to, int cap, long cost) {
            E e = new E(vs[to], cap, cost);
            E r = new E(vs[from], 0, -cost);
            e.rev = r;
            r.rev = e;
            vs[from].es.add(e);
            vs[to].es.add(r);
            return e;
        }

        long minCostFlow(int from, int to, int flow) {
            V s = vs[from], t = vs[to];
            calcInitialPotential(from);
            long res = 0;
            TreeSet<V> que = new TreeSet<V>();
            while (flow > 0) {
                for (V v : vs) v.cost = INF;
                for (V v : vs) v.visited = false;
                s.cost = 0;
                s.bef = null;
                t.bef = null;
                que.add(s);
                while (!que.isEmpty()) {
                    V v = que.first();
                    que.remove(v);
                    if (v.visited) continue;
                    v.visited = true;
                    for (E e : v.es)
                        if (e.cap > 0 && !e.to.visited) {
                            if (e.to.cost > v.cost + e.cost + v.potential - e.to.potential) {
                                if (que.contains(e.to)) que.remove(e.to);
                                e.to.cost = v.cost + e.cost + v.potential - e.to.potential;
                                e.to.bef = e;
                                que.add(e.to);
                            }
                        }
                }
                if (t.bef == null) return -1;
                for (V v : vs) v.potential += v.cost;
                int min = flow;
                for (E e = t.bef; e != null; e = e.rev.to.bef) {
                    min = Math.min(min, e.cap);
                }
                for (E e = t.bef; e != null; e = e.rev.to.bef) {
                    e.cap -= min;
                    res += e.cost * min;
                    e.rev.cap += min;
                }
                flow -= min;
            }
            return res;
        }

        // 負閉路があると失敗する.
        private void calcInitialPotential(int from) {
            for (V v : vs) v.potential = INF;
            vs[from].potential = 0;
            for (; ; ) {
                boolean updated = false;
                for (V v : vs)
                    for (E e : v.es)
                        if (e.cap > 0) {
                            if (e.to.potential > e.cost + v.potential) {
                                updated = true;
                                e.to.potential = e.cost + v.potential;
                            }
                        }
                if (!updated) break;
            }
        }


    }

    class V implements Comparable<V> {
        int id;
        boolean visited;
        ArrayList<E> es = new ArrayList<E>();
        E bef;
        long cost;
        long potential;

        public int compareTo(V o) {
            if (id == o.id) return 0;
            int res = Long.signum(cost - o.cost);
            return res == 0 ? id - o.id : res;
        }

        V(int id) {
            this.id = id;
        }
    }

    class E {
        E rev;
        V to;
        int cap;
        long cost;

        public E(V to, int cap, long cost) {
            this.to = to;
            this.cap = cap;
            this.cost = cost;
        }
    }
}
