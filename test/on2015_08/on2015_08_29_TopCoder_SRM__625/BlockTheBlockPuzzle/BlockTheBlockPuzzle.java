package on2015_08.on2015_08_29_TopCoder_SRM__625.BlockTheBlockPuzzle;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.Arrays;

public class BlockTheBlockPuzzle {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minimumHoles(String[] board) {
        int gx = 0, gy = 0;
        int h = board.length, w = board[0].length();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (board[i].charAt(j) == '$') {
                    gx = i;
                    gy = j;
                }
            }
        }
        int[][] from = new int[h][w], to = new int[h][w];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (i % 3 != gx % 3) continue;
                if (j % 3 != gy % 3) continue;
                from[i][j] = n++;
                to[i][j] = n++;
            }
        }
        int s = n++, t = n++;
        Graph G = new Graph(n);
        int max = n * 3 + 100;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (i % 3 != gx % 3) continue;
                if (j % 3 != gy % 3) continue;
                if (board[i].charAt(j) == 'H') continue;
                if (board[i].charAt(j) == 'b') {
                    G.addFlow(s, from[i][j], max);
                }
                if (board[i].charAt(j) == '$') {
                    G.addFlow(to[i][j], t, max);
                }
                int cap = board[i].charAt(j) == '.' ? 1 : max;
                G.addFlow(from[i][j], to[i][j], cap);
                for (int d = 0; d < 4; d++) {
                    int x = i + dx[d] * 3;
                    int y = j + dy[d] * 3;
                    if (0 <= x && x < h && 0 <= y && y < w) {
                        if (board[x].charAt(y) == 'H') continue;
                        cap = 0;
                        for (int k = 1; k <= 2; k++) {
                            int nx = i + dx[d] * k;
                            int ny = j + dy[d] * k;
                            if (board[nx].charAt(ny) == 'b') {
                                cap = max;
                                break;
                            }
                            if (board[nx].charAt(ny) == '.') cap++;
                        }
                        G.addFlow(to[i][j], from[x][y], cap);
                    }
                }
            }
        }
        int flow = (int) new MaxFlow(G).maxFlow(s, t);
        return flow >= max ? -1 : flow;
    }

    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
}
