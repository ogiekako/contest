package on2016_09.on2016_09_18_CodeForces_Russian_Cup_2016_Final.TaskB;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * http://www.webgraphviz.com/
 graph {
 1 -- 2 [ label = 1 ]
 2 -- 3 [ label = 4 ]
 3 -- 1 [ label = 5 ]
 1 -- 4 [ label = 5 ]
 4 -- 5 [ label = 2 ]
 5 -- 1 [ label = 6 ]
 1 -- 6 [ label = 4 ]
 6 -- 7 [ label = 6 ]
 7 -- 1 [ label = 3 ]
 }
 4,5,6 を切れば良い。

 */
public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        V[] vs = new V[n];
        for (int i = 0; i < n; i++) vs[i] = new V();
        E[] es = new E[m * 2];
        for (int i = 0; i < m; i++) {
            int u = in.nextInt()-1, v = in.nextInt()-1, c = in.nextInt() - 1;
            vs[u].add(es[i*2] = new E(vs[v],c));
            vs[v].add(es[i*2 + 1] = new E(vs[u],c));
            es[i*2].rev = es[i*2 + 1];
            es[i*2 + 1].rev = es[i*2];
        }
        vs[0].dfs();

        int p = 0;
        int s = p++, t = p++;
        int[] v = new int[m];
        for (int i = 0; i < m; i++)
            v[i] = p++;
        int[] c = new int[cycles.size()];
        for (int i = 0;i < cycles.size(); i++)
            c[i] = p++;

        Graph G = new Graph(p);
        for (int i = 0; i < m; i++) {
            G.addFlow(s, v[i], 1);
            if (trees.contains(i)) {
                G.addFlow(v[i], t, 1);
            }
        }
        for (int i = 0; i < c.length; i++) {
            for (int j : cycles.get(i)) {
                G.addFlow(v[j], c[i], 1);
            }
            G.addFlow(c[i], t, cycles.get(i).size() - 1);
        }

        double res = new MaxFlow(G).maxFlow(s, t);
        out.println((int)res);
    }

    List<ArrayList<Integer>> cycles = new ArrayList<>();
    HashSet<Integer> trees = new HashSet<>();

    class V extends ArrayList<E> {
        int state = 0; // 0: unvisited, 1: visiting, 2: found
        // Cactus decomposition
        public int dfs() {
            state = 1;
            int found = -1;
            for(E e:this) {
                if (e.visited) continue;
                e.visited = true;
                e.rev.visited = true;
                if (e.to.state == 1) {
                    if (found >= 0) {
                        throw new AssertionError("" + found);
                    }
                    e.to.state = 2;
                    found = cycles.size();
                    cycles.add(new ArrayList<>());
                    cycles.get(found).add(e.c);
                } else {
                    if (e.to.state > 0) {
                        throw new AssertionError("" + e.to.state);
                    }
                    int tmp = e.to.dfs();
                    if (tmp >= 0) {
                        if (state == 2) {
                            state = 1;
                            cycles.get(tmp).add(e.c);
                        } else {
                            found = tmp;
                            cycles.get(tmp).add(e.c);
                        }
                    } else {
                        trees.add(e.c);
                    }
                }
            }
            return found;
        }
    }

    class E {
        V to;
        E rev;
        int c;
        boolean visited;

        public E(V to, int c) {
            this.to = to;
            this.c = c;
        }
    }
}
