package tmp;

import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;

public class TheTilesDivOne {
    int n, m;
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public int find(String[] board) {
        n = board.length; m = board[0].length();
        Graph graph = new Graph(n * m * 2 + 2);
        int source = 0, sink = 1;
        int p = 2;
        int[][] ids = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (board[i].charAt(j) == 'X') {
                    ids[i][j] = -1;
                } else if ((i + j & 1) == 0) {
                    ids[i][j] = p;
                    p += 2;
                } else if ((i & 1) == 0) {
                    ids[i][j] = p++;
                } else {
                    ids[i][j] = p++;
                }
            }
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (ids[i][j] >= 0) {
                    p = ids[i][j];
                    if ((i + j & 1) == 0) {
                        graph.add(new FlowEdge(p, p + 1, 1));
                        for (int d = 0; d < 4; d++) {
                            int nx = i + dx[d];
                            int ny = j + dy[d];
                            if (0 <= nx && nx < n && 0 <= ny && ny < m && ids[nx][ny] >= 0) {
                                int q = ids[nx][ny];
                                if ((nx & 1) == 0) {
                                    graph.add(new FlowEdge(q, p, 1));
                                } else {
                                    graph.add(new FlowEdge(p + 1, q, 1));
                                }
                            }
                        }
                    } else if ((i & 1) == 0) {
                        graph.add(new FlowEdge(source, p, 1));
                    } else {
                        graph.add(new FlowEdge(p, sink, 1));
                    }
                }
        return (int) MinimumCutTree.maxFlow(graph, source, sink);
    }
}
