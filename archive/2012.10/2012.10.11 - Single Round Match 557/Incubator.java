package tmp;

import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;

import java.util.Arrays;

public class Incubator {
    public int maxMagicalGirls(String[] love) {
        int n = love.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = love[i].charAt(j) == 'Y';
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] |= graph[i][k] && graph[k][j];
        int m = 0;
        int[] is = new int[n];
        Arrays.fill(is, -1);
        for (int i = 0; i < n; i++)
            if (!graph[i][i]) {
                is[i] = m++;
            }
        boolean[][] graph2 = new boolean[m][m];
        for (int i = 0; i < n; i++)
            if (!graph[i][i]) for (int j = 0; j < n; j++)
                if (!graph[j][j]) {
                    graph2[is[i]][is[j]] = graph[i][j];
                }
        return m - BipartiteGraphAlgorithm.maximumMatching(graph2);
    }


}
