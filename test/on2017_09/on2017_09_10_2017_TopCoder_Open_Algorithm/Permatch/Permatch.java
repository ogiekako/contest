package on2017_09.on2017_09_10_2017_TopCoder_Open_Algorithm.Permatch;



import net.ogiekako.algorithm.graph.Graph;

import java.util.Arrays;

public class Permatch {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int complete(String[] board) {
        int h = board.length, w = board[0].length();

        boolean[][] graph = new boolean[h + w][h + w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                graph[i][h + j] = graph[h + j][i] = board[i].charAt(j) == '#';
            }
        }
        int[] comp = new int[h + w];
        Arrays.fill(comp, -1);
        int p = 0;
        // Red: [0,h), Blue: [h, h+w)
        int rsingle = 0, bsingle = 0;
        for (int i = 0; i < h + w; i++) {
            if (comp[i] == -1) {
                boolean cycle = color(graph, comp, i, -1, p++);
                if (cycle) {
                    return -1;
                }
                boolean single = true;
                for (int j = 0; j < h + w; j++) {
                    if (graph[i][j]) single = false;
                }
                if (single) {
                    if (i < h) rsingle++;
                    else bsingle++;
                }
            }
        }
        int big = p - rsingle - bsingle;
        if (big == 0) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int rmask = 0; rmask < 1 << h; rmask++) {
            for (int bmask = 0; bmask < 1 << w; bmask++) {
                int r = Integer.bitCount(rmask);
                int b = Integer.bitCount(bmask);
                if (Math.max(r - bsingle, 0) + Math.max(b - rsingle, 0) <= big - 1) {
                    int[] size = new int[h + w];
                    boolean ok = true;
                    for (int i = 0; i < h + w; i++) {
                        if (size[i] == 0) {
                            if (!check(graph, size, i, -1, rmask | bmask << h)) {
                                ok = false;
                            }
                            if (size[i] % 2 == 0) {
                                ok = false;
                            }
                        }
                    }
                    if (ok) {
                        res = Math.min(res, r + b);
                    }
                }
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private boolean check(boolean[][] graph, int[] size, int i, int p, int mask) {
        int odd = 0;
        size[i] = 1;
        if (mask << 31 - i < 0) {
            size[i]++;
            odd++;
        }
        boolean res = true;
        for (int j = 0; j < graph[i].length; j++) {
            if (j != p && graph[i][j]) {
                boolean ok = check(graph, size, j, i, mask);
                if (!ok) {
                    res = false;
                }
                if (size[j] % 2 == 1) odd++;
                size[i] += size[j];
            }
        }
        return res && odd <= 2;
    }

    private boolean color(boolean[][] graph, int[] comp, int i, int p, int c) {
        if (comp[i] == c) return true;
        comp[i] = c;
        for (int j = 0; j < graph.length; j++) {
            if (p != j && graph[i][j]) {
                boolean cycle = color(graph, comp, j, i, c);
                if (cycle) {
                    return true;
                }
            }
        }
        return false;
    }
}
