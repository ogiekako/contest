package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.LuckyGrid;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.ip.MonotoneIp2Solver;

import java.util.Arrays;
import java.util.TreeSet;

public class LuckyGrid {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    /*
    [0, 1, 1, 7]
    [1, 0, 1, 4]
    [3, 5, 8, 47]
    [4, 4, 8, 44]
    [0, 11, 11, 77]
    [1, 10, 11, 74]
    [10, 1, 11, 47]
    [11, 0, 11, 44]
    [7, 7, 14, 77]
    [8, 6, 14, 74]
    [14, 3, 17, 77]
    [15, 2, 17, 74]
    [5, 61, 66, 447]
    [6, 60, 66, 444]
     */
    public int findMinimumSum(String[] grid) {
        int n = grid.length;
        TreeSet<Integer> fours = new TreeSet<>();
        for (int four = 0; four < n + 1; four++) {
            int seven = n - four;
            int v = four * 4 + seven * 7;
            boolean ok = true;
            while (v > 0) {
                if (v % 10 != 4 && v % 10 != 7) ok = false;
                v /= 10;
            }
            if (ok) {
                fours.add(four);
            }
        }
        TreeSet<Integer> lower = new TreeSet<>();
        for (int four : fours) {
            if (fours.contains(four + 1)) {
                lower.add(four);
            }
        }
        debug("lower", lower);

        int[] C = new int[n];
        int[] R = new int[n];
        int res = Integer.MAX_VALUE;
        if (n == 11) {
            // 0, 10
            for (final int x : lower) {
                Arrays.fill(C, x);
                Arrays.fill(R, x);
                res = Math.min(res, solve(R, C, grid));
                for (int i = 0; i < n; i++) {
                    R[i] = 10 - x;
                    res = Math.min(res, solve(R, C, grid));
                    R[i] = x;
                }
                for (int i = 0; i < n; i++) {
                    C[i] = 10 - x;
                    res = Math.min(res, solve(R, C, grid));
                    for (int j = 0; j < n; j++) {
                        R[j] = 10 - x;
                        res = Math.min(res, solve(R, C, grid));
                        R[j] = x;
                    }
                    C[i] = x;
                }
            }
        } else {
            if (lower.isEmpty()) return -1;
            int x = lower.first();
            Arrays.fill(R, x);
            Arrays.fill(C, x);
            res = Math.min(res, solve(R, C, grid));
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private int solve(int[] R, int[] C, String[] grid) {
        R = R.clone();
        C = C.clone();

        int n = R.length;
        int four = 0;
        for (int i = 0; i < n; i++) {
            four += C[i];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '4') {
                    R[i]--;
                    C[j]--;
                    if (R[i] < -1) return Integer.MAX_VALUE;
                    if (C[j] < -1) return Integer.MAX_VALUE;
                }
            }
        }

        int p = 0;
        int s = p++, t = p++;
        int[] col = new int[n];
        int[] row = new int[n];
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            col[i] = p++;
        }
        for (int i = 0; i < n; i++) {
            row[i] = p++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = p++;
            }
        }

        Graph graph = new Graph(p);

        int BIG = 5000;
        long need = 0;

        for (int i = 0; i < n; i++) {
            if (C[i] == -1) {
                four++;
            } else {
                graph.addFlow(s, col[i], C[i], -BIG);
                need += C[i] * BIG;
                graph.addFlow(s, col[i], 1, -1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (R[i] == -1) {
                // Do nothing
            } else {
                graph.addFlow(row[i], t, R[i], -BIG);
                need += R[i] * BIG;
                graph.addFlow(row[i], t, 1, 0);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '.') {
                    graph.addFlow(col[j], g[i][j], 1, 0);
                    graph.addFlow(g[i][j], row[i], 1, 0);
                }
            }
        }
        graph.addFlow(t, s, 10000, 0);
        long cost = (long) -new MinimumCostFlow(graph).minimumCostCirculation();
        if (cost < need) return Integer.MAX_VALUE;
        cost -= need;

        four += cost;

        return four * 4 + (n * n - four) * 7;
    }
}
