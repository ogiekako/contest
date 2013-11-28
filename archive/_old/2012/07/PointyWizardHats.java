package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;

public class PointyWizardHats {
    public int getNumHats(int[] topHeight, int[] topRadius, int[] bottomHeight, int[] bottomRadius) {
        int n = topHeight.length, m = bottomHeight.length;
        boolean[][] graph = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (bottomHeight[j] * topRadius[i] < topHeight[i] * bottomRadius[j] && topRadius[i] < bottomRadius[j])
                    graph[i][j] = true;
            }
        return BipartiteGraphAlgorithm.maximumMatching(graph);
    }
}

