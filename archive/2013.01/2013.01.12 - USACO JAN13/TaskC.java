package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        UnionFind uf = new UnionFind(N * 2);
        for (int i = 0; i < M; i++) {
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            char c = in.nextChar();
            if (c == 'T') {
                uf.union(x * 2, y * 2);
                uf.union(x * 2 + 1, y * 2 + 1);
                if (uf.find(x * 2, x * 2 + 1) || uf.find(y * 2, y * 2 + 1)) {
                    out.println(i); return;
                }
            } else {
                uf.union(x * 2, y * 2 + 1);
                uf.union(x * 2 + 1, y * 2);
                if (uf.find(x * 2, x * 2 + 1) || uf.find(y * 2, y * 2 + 1)) {
                    out.println(i); return;
                }
            }
        }
        out.println(M);
    }
}
