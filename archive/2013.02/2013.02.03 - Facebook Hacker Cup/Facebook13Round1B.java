package tmp;


import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class Facebook13Round1B {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int m = in.nextInt();
        String k1 = in.nextLine();
        String k2 = in.nextLine();
        int n = k1.length();
        int l = n / m;
        char[] res = k1.toCharArray();
        for (int i = 0; i < n; i++)
            if (res[i] == '?') {
                boolean ok = false;
                for (char c = 'a'; c <= 'f'; c++) {
                    res[i] = c;
                    if (isPossible(res, k1, k2, m, l)) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    out.println("IMPOSSIBLE");
                    return;
                }
            }
        if (!isPossible(res, k1, k2, m, l)) {
            out.println("IMPOSSIBLE");
            return;
        }
        out.println(String.valueOf(res));
    }

    private boolean isPossible(char[] res, String k1, String k2, int m, int l) {
        return isPossible1(res, k1) && isPossible2(res, k2, m, l);
    }

    private boolean isPossible2(char[] res, String k2, int m, int l) {
        boolean[][] graph = new boolean[m][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < m; j++) {
                String c = String.valueOf(res).substring(i * l, (i + 1) * l);
                String d = k2.substring(j * l, (j + 1) * l);
                graph[i][j] = isPossible1(c.toCharArray(), d);
            }
        return BipartiteGraphAlgorithm.maximumMatching(graph) == m;
    }

    private boolean isPossible1(char[] res, String k1) {
        for (int i = 0; i < res.length; i++) {
            char c = res[i], d = k1.charAt(i);
            if (c != '?' && d != '?' && c != d) return false;
        }
        return true;
    }
}
