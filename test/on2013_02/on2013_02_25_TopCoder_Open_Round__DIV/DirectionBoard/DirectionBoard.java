package on2013_02.on2013_02_25_TopCoder_Open_Round__DIV.DirectionBoard;


import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
//  TopCoder Open 2013 Round 1A Hard
public class DirectionBoard {
    String DIR = "DRUL";
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public int getMinimum(String[] board) {
        int height = board.length, width = board[0].length();
        int size = height * width;
        int source = size * 2, sink = source + 1;
        Graph graph = new Graph(size * 2 + 2);
        for (int x = 0; x < height; x++)
            for (int y = 0; y < width; y++) {
                int here = x * width + y;
                for (int d = 0; d < 4; d++) {
                    int nx = (x + dx[d] + height) % height;
                    int ny = (y + dy[d] + width) % width;
                    int cost = DIR.charAt(d) == board[x].charAt(y) ? 0 : 1;
                    int there = nx * width + ny;
                    graph.addFlow(here * 2, there * 2 + 1, 1, cost);
                }
                graph.addFlow(source, here * 2, 1, 0);
                graph.addFlow(here * 2 + 1, sink, 1, 0);
            }
        long res = MinimumCostFlow.minimumCostFlow(graph, source, sink, size);
        return res == Long.MAX_VALUE ? -1 : (int) res;
    }
}
