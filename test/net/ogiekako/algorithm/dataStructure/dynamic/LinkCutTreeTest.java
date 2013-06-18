package net.ogiekako.algorithm.dataStructure.dynamic;

import junit.framework.Assert;
import net.ogiekako.algorithm.dataStructure.dynamic.test.DynamicTreeTester;
import net.ogiekako.algorithm.graph.denseGraph.DenseGraphUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LinkCutTreeTest {
    @Test
    public void test() throws Exception {
        DynamicTreeTester.test(new DynamicTreeTester.Generator() {
            public DynamicTree create(int n) {
                final DynamicTreeNode[] nodes = new DynamicTreeNode[n];
                for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode(i);
                return new DynamicTree() {
                    public void cut(int vertex, int _) {
                        nodes[vertex].cut();
                    }
                    public void link(int root, int parent) {
                        nodes[root].link(nodes[parent]);
                    }
                    public int root(int vertex) {
                        return ((LinkCutTreeNode) nodes[vertex].root()).id;
                    }
                    public void evert(int v) {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        });
    }
    @Test
    public void testLCA() {
        Random rnd = new Random(4102984019L);
        for (int i = 0; i < 100; i++) {
            int n = 100;
            int m = 10000;
            long seed = rnd.nextLong();
            int c = testLCA0(seed, n, m);
            Assert.assertEquals(m, c);
        }
    }

    @Test(timeout = 1000)
    public void testLowestCommonAncestorTime() {
        Random rnd = new Random(124019824L);
        int n = 100000;
        int[] order = ArrayUtils.createOrder(n);
        ArrayUtils.shuffle(order);
        int[] parent = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            parent[i] = rnd.nextInt(i + 1);
        }
        LinkCutTreeNode[] nodes = new LinkCutTreeNode[n];
        for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode();
        for (int i = 0; i < n - 1; i++) nodes[order[i + 1]].link(nodes[order[parent[i]]]);
        int[] as = new int[n], bs = new int[n];
        for (int i = 0; i < n; i++) {
            as[i] = rnd.nextInt(n); bs[i] = rnd.nextInt(n);
        }
        for (int i = 0; i < n; i++) {
            nodes[as[i]].lowestCommonAncestor(nodes[bs[i]]);
        }
    }

    @Test(timeout = 1500)
    public void testLowestCommonAncestorTime2() {
        Random rnd = new Random(12401982L);
        int n = 100000;
        int[] order = ArrayUtils.createOrder(n);
        int[] parent = ArrayUtils.createOrder(n + 1);
        LinkCutTreeNode[] nodes = new LinkCutTreeNode[n];
        for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode();
        for (int i = 0; i < n - 1; i++) nodes[order[i + 1]].link(nodes[order[parent[i]]]);
        int[] as = new int[n], bs = new int[n];
        for (int i = 0; i < n; i++) {
            as[i] = rnd.nextInt(n); bs[i] = rnd.nextInt(n);
        }
        for (int i = 0; i < n; i++) {
            nodes[as[i]].lowestCommonAncestor(nodes[bs[i]]);
        }
    }

    private int testLCA0(long seed, int n, int m) {
        LinkCutTreeNode[] nodes = new LinkCutTreeNode[n];
        for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode();
        int[] ps = new int[n];
        Arrays.fill(ps, -1);
        Random rnd = new Random(seed);
        int[] q = new int[m];
        int[] x = new int[m];
        int[] y = new int[m];
        for (int i = 0; i < m; i++) {
            q[i] = rnd.nextInt(3);
            if (q[i] == 1) x[i] = rnd.nextInt(n);
            else if (q[i] == 0) {
                x[i] = rnd.nextInt(n - 1) + 1;
                y[i] = rnd.nextInt(x[i]);
            } else {
                x[i] = rnd.nextInt(n);
                y[i] = rnd.nextInt(n);
            }
        }
        for (int i = 0; i < m; i++) {
//            System.err.println(Arrays.toString(ps)+" "+q[i]+" "+x[i]+" "+y[i]);
            if (q[i] == 0) {
                if (ps[x[i]] == -1) {
                    ps[x[i]] = y[i];
                    nodes[x[i]].link(nodes[y[i]]);
                }
            } else if (q[i] == 1) {
                if (ps[x[i]] != -1) {
                    nodes[x[i]].cut();
                    ps[x[i]] = -1;
                }
            } else {
                ArrayList<Integer> list = new ArrayList<Integer>();
                int p = x[i];
                while (p != -1) {
                    list.add(p);
                    p = ps[p];
                }
                p = y[i];
                while (p != -1) {
                    if (list.contains(p)) break;
                    p = ps[p];
                }
                LinkCutTreeNode res = nodes[x[i]].lowestCommonAncestor(nodes[y[i]]);
                if (p == -1) {
                    Assert.assertNull(res);
                } else {
                    int v;
                    for (v = 0; ; v++) if (nodes[v] == res) break;
//                    System.err.println(v);
                    if (nodes[p] != res) {
                        nodes[x[i]].lowestCommonAncestor(nodes[y[i]]);
                    }
                    Assert.assertEquals(nodes[p], res);
                }
            }

        }
        return m;
    }

    @Test
    public void testEvert_getParent_secondRoot() {
        final int n = 20;
        int m = 100000;
        final boolean[][] graph = new boolean[n][n];
        Random rnd = new Random(124129048L);
        class SolverStupid {
            boolean[] isRoot;

            SolverStupid() {
                isRoot = new boolean[n];
                Arrays.fill(isRoot, true);
            }

            void link(int from, int parent) {
                if (!isRoot[from])
                    throw null;
                if (DenseGraphUtils.isConnected(graph, from, parent))
                    throw null;
                isRoot[from] = false;
                graph[from][parent] = graph[parent][from] = true;
            }

            public void evert(int x) {
                boolean[] visited = new boolean[n];
                clearRoot(x, visited);
                isRoot[x] = true;
            }

            private void clearRoot(int x, boolean[] visited) {
                if (visited[x]) return;
                visited[x] = true;
                isRoot[x] = false;
                for (int i = 0; i < n; i++) if (graph[x][i]) clearRoot(i, visited);
            }

            public int cut(int x) {
                if (isRoot[x]) throw null;
                int r = getRoot(-1, x);
                int parent = cutDfs(-1, r, x);
                if (!graph[parent][x]) throw null;
                graph[parent][x] = graph[x][parent] = false;
                isRoot[x] = true;
                return parent;
            }

            private int cutDfs(int p, int cur, int to) {
                if (graph[cur][to]) return cur;
                for (int i = 0; i < n; i++)
                    if (i != p && graph[cur][i]) {
                        int res = cutDfs(cur, i, to);
                        if (res >= 0) return res;
                    }
                return -1;
            }

            private int getRoot(int p, int x) {
                if (isRoot[x]) return x;
                for (int i = 0; i < n; i++)
                    if (graph[x][i] && p != i) {
                        int r = getRoot(x, i);
                        if (r >= 0) return r;
                    }
                return -1;
            }

            public void check(LinkCutTreeNode[] nodes) {
                boolean[][] graph2 = new boolean[n][n];
                boolean[] isRoot2 = new boolean[n];
                int[] parent = new int[n];
                Arrays.fill(parent, -1);
                for (int i = 0; i < n; i++) if (isRoot[i]) dfsParent(-1, i, parent);
                int[] parent2 = new int[n];
                Arrays.fill(parent2, -1);
                for (int i = 0; i < n; i++) {
                    LinkCutTreeNode t = nodes[i].getParent();
                    if (t == null) isRoot2[i] = true;
                    else {
                        graph2[i][t.id] = graph2[t.id][i] = true;
                        parent2[i] = t.id;
                    }
                }
                if (!ArrayUtils.equals(graph, graph2) || !Arrays.equals(parent, parent2)) {
                    ArrayUtils.show(graph2);

                    throw null;
                }
                if (!Arrays.equals(isRoot, isRoot2)) {
                    throw null;
                }
            }

            private void dfsParent(int p, int r, int[] parent) {
                for (int i = 0; i < n; i++)
                    if (i != p && graph[i][r]) {
                        parent[i] = r;
                        dfsParent(r, i, parent);
                    }
            }

            public int lca(int a, int b) {
                int r = getRoot(-1, a);
                return DenseGraphUtils.lowerCommonAncestor(graph, r, a, b);
            }

            public int secondRoot(int a) {
                int res = getSecondRoot(-1, a);
                if (res == -2) throw new AssertionError();
                return res;
            }
            private int getSecondRoot(int p, int x) {
                if (isRoot[x]) return p;
                for (int i = 0; i < n; i++)
                    if (graph[x][i] && p != i) {
                        int r = getSecondRoot(x, i);
                        if (r >= -1) return r;
                    }
                return -2;
            }
        }
        SolverStupid solver = new SolverStupid();
        LinkCutTreeNode[] nodes = new LinkCutTreeNode[n];
        for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode(i);
        for (int i = 0; i < m; i++) {
            solver.check(nodes);
            int q = rnd.nextInt(5);
            if (q == 0) {// add
                int a = -1, b = -1;
                for (int j = 0; j < 100; j++) {
                    int x = rnd.nextInt(n), y = rnd.nextInt(n);
                    if (x != y && !graph[x][y] && !DenseGraphUtils.isConnected(graph, x, y)) {
                        a = x; b = y; break;
                    }
                }
                if (a == -1) {
                    i--; continue;
                }
                nodes[a].evert();
                solver.evert(a);
                if (nodes[a].getParent() != null) throw null;
                nodes[a].link(nodes[b]);
                solver.link(a, b);
            } else if (q == 1) {// cut
                for (int j = 0; j < 100; j++) {
                    int a = rnd.nextInt(n);
                    LinkCutTreeNode t = nodes[a].getParent();
                    if (t == null) continue;
                    int b = t.id;
                    nodes[a].cut();
                    int c = solver.cut(a);
                    if (b != c)
                        throw null;
                    break;
                }
            } else if (q == 2) {// evert
                int a = rnd.nextInt(n);
                nodes[a].evert();
                solver.evert(a);
            } else if (q == 3) {// LCA
                int a = rnd.nextInt(n), b = rnd.nextInt(n);
                LinkCutTreeNode lca = nodes[a].lowestCommonAncestor(nodes[b]);
                int res = lca == null ? -1 : lca.id;
                int exp = solver.lca(a, b);
                if (res != exp) {
                    throw null;
                }
            } else if (q == 4) {// second root
                int a = rnd.nextInt(n);
                LinkCutTreeNode secondRoot = nodes[a].secondRoot();
                int res = secondRoot == null ? -1 : secondRoot.id;
                int exp = solver.secondRoot(a);
//                ArrayUtils.show(graph);
//                debug("isRoot",solver.isRoot);
//                debug(a);
//                debug(res,exp);
                if (res != exp) {
                    throw null;
                }
            }
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
