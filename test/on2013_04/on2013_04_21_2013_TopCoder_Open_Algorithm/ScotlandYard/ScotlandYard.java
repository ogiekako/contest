package on2013_04.on2013_04_21_2013_TopCoder_Open_Algorithm.ScotlandYard;


import net.ogiekako.algorithm.graph.algorithm.SCC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ScotlandYard {
    public int maxMoves(String[] taxi, String[] bus, String[] metro) {
        int n = taxi.length;
        int[][] id = new int[n][n];
        int m = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                id[i][j] = id[j][i] = m++;
            }
        for (int i = 0; i < n; i++) id[i][i] = -1;
        boolean[] canMove = new boolean[m];
        ArrayList<Integer>[] graph = new ArrayList[m];
        for (int i = 0; i < m; i++) graph[i] = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                boolean[] can = new boolean[m];
                for (String[] way : new String[][]{taxi, bus, metro}) {
                    for (int i2 = 0; i2 < n; i2++)
                        for (int j2 = 0; j2 < n; j2++)
                            if (i2 != j2) {
                                if ((way[i].charAt(i2) == 'Y' || way[j].charAt(i2) == 'Y') && (way[j].charAt(j2) == 'Y' || way[i].charAt(j2) == 'Y')) {
                                    can[id[i2][j2]] = true;
                                }
                                if (way[i].charAt(i2) == 'Y' || way[j].charAt(j2) == 'Y') canMove[id[i][j]] = true;
                            }
                }
                if (can[id[i][j]]) return -1;
                for (int k = 0; k < m; k++) if (can[k]) graph[id[i][j]].add(k);
            }
        int[] comp = SCC.scc(graph);
        boolean acyclic = graph.length == 0;
        for (int c : comp) if (c == graph.length - 1) acyclic = true;
        for (int i = 0; i < graph.length; i++) for (int j : graph[i]) if (i == j) acyclic = false;
        if (!acyclic) return -1;
        int[] vertex = new int[graph.length];
        for (int i = 0; i < graph.length; i++) vertex[comp[i]] = i; // vertex[0] < vertex[1] < ...
        int[] dp = new int[graph.length];
        int res = 0;
        for (int i = 0; i < graph.length; i++) {
            res = Math.max(res, dp[vertex[i]] + (canMove[vertex[i]] ? 1 : 0));
            for (int u : graph[vertex[i]]) dp[u] = Math.max(dp[u], dp[vertex[i]] + 1);
        }
        return res;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        Random rnd = new Random(1240912408L);
        for (; ; ) {
            int n = 50;
            String[][] A = new String[3][50];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 50; j++) {
                    A[i][j] = "";
                    for (int k = 0; k < 50; k++) {
                        A[i][j] += (j == k || rnd.nextInt(20) != 0) ? 'N' : 'Y';
                    }
                }
            }
            int res = new ScotlandYard().maxMoves(A[0], A[1], A[2]);
            debug(A);
            System.out.println(res);
        }
    }
}
