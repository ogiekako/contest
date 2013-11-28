package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        Edge[] es = new Edge[m];
        for (int i = 0; i < m; i++) {
            es[i] = new Edge(in.nextInt() - 1, in.nextInt() - 1);
        }
        int[][] tree = new int[m + 1][];
        int[][] revTree = new int[m + 1][];
        UnionFind uf = new UnionFind(n);
        tree[0] = uf.tree.clone();
        for (int i = 0; i < m; i++) {
            uf.union(es[i].x, es[i].y);
            tree[i + 1] = uf.tree.clone();
        }
        uf = new UnionFind(n);
        revTree[m] = uf.tree.clone();
        for (int i = m - 1; i >= 0; i--) {
            uf.union(es[i].x, es[i].y);
            revTree[i] = uf.tree.clone();
        }
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            int s = in.nextInt() - 1, t = in.nextInt();
            uf = new UnionFind(tree[s].clone());
            UnionFind uf2 = new UnionFind(revTree[t]);
            for (int j = 0; j < n; j++) uf.union(j, uf2.root(j));
            int res = 0;
            for (int j = 0; j < n; j++) if (uf.root(j) == j) res++;
            out.println(res);
        }
    }
    class Edge {
        int x, y;
        public Edge(int x, int y) {
            this.x = x; this.y = y;
        }
    }
}
