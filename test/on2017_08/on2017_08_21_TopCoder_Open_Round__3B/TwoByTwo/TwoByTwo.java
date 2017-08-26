package on2017_08.on2017_08_21_TopCoder_Open_Round__3B.TwoByTwo;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.Arrays;

public class TwoByTwo {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minimal(String[] board) {
        int h = board.length;
        int w = board[0].length();
        int[][] in = new int[h][w];
        int[][] out = new int[h][w];
        int p = 0;
        int s = p++, t = p++;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                in[i][j] = p++;
                out[i][j] = p++;
            }
        }
        Graph graph = new Graph(p);
        int INF = 1000;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (board[i].charAt(j) == '.') continue;
                graph.addFlow(in[i][j], out[i][j], 1);
                if (j < 2 || i >= h - 2) graph.addFlow(s, in[i][j], INF);
                if (i < 2 || j >= w - 2) graph.addFlow(out[i][j], t, INF);
                for (int dx = -2; dx <= 2; dx++)
                    for (int dy = -2; dy <= 2; dy++) {
                        int nx = i + dx;
                        int ny = j + dy;
                        if (0 <= nx && nx < h && 0 <= ny && ny < w && board[nx].charAt(ny) == '#') {
                            graph.addFlow(out[i][j], in[nx][ny], INF);
                        }
                    }
            }
        }
        return (int) new MaxFlow(graph).maxFlow(s, t);
    }
}
