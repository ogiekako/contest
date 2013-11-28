package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.dataStructure.intCollection.IntArrayList;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        IntArrayList[] graph = new IntArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new IntArrayList();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            uf.union(x, y);
            graph[x].add(y);
            graph[y].add(x);
        }
        if (uf.size(0) < n) {
            out.println("unknown topology");
            return;
        }
        int one = 0, two = 0;
        for (int i = 0; i < n; i++) {
            if (graph[i].size() == 1) one++;
            if (graph[i].size() == 2) two++;
        }
        if (m == n - 1 && one == 2 && two == n - 2) {
            out.println("bus topology");
        } else if (m == n && two == n) {
            out.println("ring topology");
        } else if (m == n - 1 && one == n - 1) {
            out.println("star topology");
        } else {
            out.println("unknown topology");
        }
    }
}
