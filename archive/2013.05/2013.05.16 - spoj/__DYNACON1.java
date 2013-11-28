package src;

import net.ogiekako.algorithm.dataStructure.dynamic.dyncon.EulerTourTree;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class __DYNACON1 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        EulerTourTree tree = new EulerTourTree(n);
        for (int i = 0; i < m; i++) {
            char c = in.next().charAt(0);
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            if (c == 'a') {
                tree.link(x,y);
            } else if (c == 'r') {
                tree.cut(x,y);
            } else {
                boolean res = tree.root(x) == tree.root(y);
                out.println(res ? "YES" : "NO");
            }
        }
    }
}
