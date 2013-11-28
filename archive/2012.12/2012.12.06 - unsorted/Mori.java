package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.BitSet;

public class Mori {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        if ((long) n * (n - 1) / 2 - m >= n) {
            for (int i = 0; i < m; i++) {
                out.println("no");
            }
            return;
        }
        BitSet es = new BitSet();
        for (int i = 0; i < n; i++) for (int j = 0; j < i; j++) es.set(j << 9 | i);
        for (int i = 0; i < m; i++) {
            int s = in.nextInt() - 1, t = in.nextInt() - 1;
            if (s > t) {int tmp = s; s = t; t = tmp;}
            int key = s << 9 | t;
            if (es.get(key)) es.set(key, false);
            else es.set(key);
            out.println(solve(n, es));
        }
    }

    private String solve(int n, BitSet es) {
        if (es.cardinality() >= n) return "no";
        UnionFind uf = new UnionFind(n);
        for (int i = es.nextSetBit(0); i >= 0; i = es.nextSetBit(i + 1)) {
            if (uf.find(i >> 9, i & 511)) return "no";
            uf.union(i >> 9, i & 511);
        }
        return "yes";
    }
}
