package tmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class CirclesGame {
    public String whoCanWin(int[] x, int[] y, int[] r) {
        int n = x.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            int k = -1;
            for (int j = 0; j < n; j++)
                if (i != j) {
                    double dist = Math.hypot(x[i] - x[j], y[i] - y[j]);
                    if (dist + r[i] - 1e-9 < r[j]) {
                        if (k == -1 || r[k] > r[j]) k = j;
                    }
                }
            if (k != -1) graph[i][k] = true;
        }
//        for(boolean[] bs:graph) System.err.println(Arrays.toString(bs));
        int[] g = new int[n];
        Arrays.fill(g, -1);
        for (int i = 0; i < n; i++) {
            g[i] = solve(g, i, n, graph);
        }
//        debug(g);
        int xor = 0;
        for (int i = 0; i < n; i++) {
            int d = 0;
            for (int j = 0; j < n; j++) if (graph[i][j]) d++;
            if (d == 0) xor ^= g[i];
        }
        debug(xor);
        return xor == 0 ? "Bob" : "Alice";
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int solve(int[] g, int p, int n, boolean[][] graph) {
        if (g[p] != -1) return g[p];
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            if (graph[i][p]) {
                list.add(solve(g, i, n, graph));
            }
        if (list.isEmpty()) return g[p] = 1;
        BitSet can = new BitSet();
        int xor = 0;
        for (int i : list) xor ^= i;
        can.set(xor);
        for (int i = 0; i < list.size(); i++) {
            int v = list.get(i);
            for (int j = 0; j < v; j++) {
                xor = j;
                for (int k = 0; k < list.size(); k++) if (k != i) xor ^= list.get(k);
                can.set(xor);
            }
        }
        return g[p] = can.nextClearBit(0);
    }
}
