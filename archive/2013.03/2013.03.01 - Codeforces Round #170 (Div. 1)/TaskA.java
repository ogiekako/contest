package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Set;
import java.util.TreeSet;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        UnionFind tree = new UnionFind(n);
        Set<Integer>[] ls = new Set[n];
        boolean zero = true;
        for (int i = 0; i < n; i++) {
            ls[i] = new TreeSet<Integer>();
            int k = in.nextInt();
            if (k > 0) zero = false;
            for (int j = 0; j < k; j++) {
                int v = in.nextInt();
                ls[i].add(v);
                for (int l = 0; l < i; l++) if (ls[l].contains(v)) tree.union(i, l);
            }
        }
        int res = -1;
        if (zero) res++;
        for (int i = 0; i < n; i++)
            if (tree.root(i) == i) {
                res++;
            }
        out.println(res);
    }
}
