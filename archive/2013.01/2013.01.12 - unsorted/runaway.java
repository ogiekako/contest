package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.List;

public class runaway {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) ss[i] = in.next() + (char) ('z' + 1);
        Node root = new Node();
        for (int i = 0; i < n; i++) {
            Node cur = root;
            for (int j = 0; j < ss[i].length(); j++) {
                int p = ss[i].charAt(j) - 'a';
                if (cur.next[p] == null) cur.next[p] = new Node();
                cur = cur.next[p];
            }
        }
        List<String> res = new ArrayList<String>();
        for (String s : ss) {
            Node cur = root;
            boolean[][] graph = new boolean[27][27];// g[i,j] <-> i < j
            for (int i = 0; i < 26; i++) graph[26][i] = true;
            for (int i = 0; i < s.length(); i++) {
                int p = s.charAt(i) - 'a';
                for (int j = 0; j < 27; j++) if (j != p && cur.next[j] != null) graph[p][j] = true;
                cur = cur.next[p];
            }
            for (int k = 0; k < 27; k++)
                for (int i = 0; i < 27; i++) for (int j = 0; j < 27; j++) graph[i][j] |= graph[i][k] && graph[k][j];
            boolean ok = true;
            for (int i = 0; i < 27; i++) if (graph[i][i]) ok = false;
            if (ok) res.add(s);
        }
        out.println(res.size());
        for (String s : res) out.println(s.substring(0, s.length() - 1));
    }

    class Node {
        Node[] next = new Node[27];
    }
}
