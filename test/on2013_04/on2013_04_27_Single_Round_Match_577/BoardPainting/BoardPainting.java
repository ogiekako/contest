package on2013_04.on2013_04_27_Single_Round_Match_577.BoardPainting;


import net.ogiekako.algorithm.graph.BipartiteGraphAlgorithm;

import java.util.*;
// 1000
public class BoardPainting {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public int minimalSteps(String[] target) {
        int h = target.length, w = target[0].length();
        int[][][] id = new int[h][w][4];
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) for (int d = 0; d < 4; d++) id[i][j][d] = -1;
        int V = 0;
        int[] E = new int[2];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++)
                if (target[i].charAt(j) == '#') {
                    V++;
                    for (int d = 0; d < 2; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (0 <= nx && nx < h && 0 <= ny && ny < w && target[nx].charAt(ny) == '#') {
                            id[i][j][d] = id[nx][ny][d ^ 2] = E[d]++;
                        }
                    }
                }
        }
        boolean[][] graph = new boolean[E[0]][E[1]];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                for (int d = 0; d < 4; d++) {
                    if (id[i][j][d] >= 0 && id[i][j][(d + 1) % 4] >= 0) {
                        if (d % 2 == 0) graph[id[i][j][d]][id[i][j][(d + 1) % 4]] = true;
                        else graph[id[i][j][(d + 1) % 4]][id[i][j][d]] = true;
                    }
                }
            }
        int indSet = BipartiteGraphAlgorithm.maximumIndependentSet(E[0], E[1], graph);
        return V - indSet;
    }
}
