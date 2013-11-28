package tmp;

import net.ogiekako.algorithm.dataStructure.dynamic.LinkCutTreeNode;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class DYNACON1 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        LinkCutTreeNode[] nodes = new LinkCutTreeNode[n];
        for (int i = 0; i < n; i++) nodes[i] = new LinkCutTreeNode();
        for (int i = 0; i < m; i++) {
            char c = in.next().charAt(0);
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            if (c == 'a') {
                nodes[x].evert();
                nodes[x].link(nodes[y]);
            } else if (c == 'r') {
                nodes[x].evert();
                nodes[y].cut();
            } else {
                boolean res = nodes[x].root() == nodes[y].root();
                out.println(res ? "YES" : "NO");
            }
        }
    }
}
